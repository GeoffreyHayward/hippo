package uk.nhs.digital.externalstorage.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AbortMultipartUploadRequest;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadResult;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PartETag;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.UploadPartRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Implement S3Connector to allow uploading files to S3 and managing ACL.
 */
public class S3ConnectorImpl implements S3Connector {

    private String bucketName;

    private AmazonS3 s3;

    private S3ObjectKeyGenerator s3ObjectKeyGenerator;

    // minimum multi part upload is 5MB
    private static final int BUFFER_SIZE = 5 * 1024 * 1024;

    static final Logger log = LoggerFactory.getLogger(S3ConnectorImpl.class);

    public S3ConnectorImpl(AmazonS3 s3, String bucketName, S3ObjectKeyGenerator s3ObjectKeyGenerator) {
        this.bucketName = bucketName;
        this.s3 = s3;
        this.s3ObjectKeyGenerator = s3ObjectKeyGenerator;
    }

    public String getBucketName() {
        return bucketName;
    }

    public boolean publishResource(String objectPath) {
        AccessControlList acl = s3.getObjectAcl(bucketName, objectPath);
        acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
        s3.setObjectAcl(bucketName, objectPath, acl);

        return true;
    }

    public boolean unpublishResource(String objectPath) {
        AccessControlList acl = s3.getObjectAcl(bucketName, objectPath);
        acl.revokeAllPermissions(GroupGrantee.AllUsers);
        s3.setObjectAcl(bucketName, objectPath, acl);

        return true;
    }

    public S3ObjectMetadata uploadFile(InputStream fileStream, String fileName, String contentType) {
        String objectKey = s3ObjectKeyGenerator.generateObjectKey(fileName);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);

        // initialise multipart upload
        InitiateMultipartUploadResult initResult = s3.initiateMultipartUpload(new InitiateMultipartUploadRequest(bucketName, objectKey, metadata));

        // loop parts
        List<PartETag> partETags;
        try {
            partETags = uploadParts(fileStream, bucketName, objectKey, initResult.getUploadId());

        } catch (Exception ex) {
            final String errorMessage = "Failed to upload file " + objectKey;
            log.error(errorMessage, ex);
            s3.abortMultipartUpload(new AbortMultipartUploadRequest(bucketName, objectKey, initResult.getUploadId()));
            throw new RuntimeException(errorMessage, ex);
        }

        // finalise multipart upload
        s3.completeMultipartUpload(new CompleteMultipartUploadRequest(bucketName, objectKey, initResult.getUploadId(), partETags));

        // The above put request returns metadata object but it's empty,
        // hence the need for a separate call to fetch actual metadata.
        ObjectMetadata resultMetadata = s3.getObjectMetadata(bucketName, objectKey);

        return new S3ObjectMetadataImpl(resultMetadata, bucketName, objectKey);
    }

    private List<PartETag> uploadParts(final InputStream fileStream,
                                       final String bucketName,
                                       final String objectKey,
                                       final String uploadId
    ) throws IOException {
        final List<PartETag> partETags = new ArrayList<>();
        int lastBytesReadCount = 0;
        int processedBytesCount = 0;
        int currentPartNumber = 0;

        while (lastBytesReadCount >= 0) {
            final byte[] buffer = new byte[BUFFER_SIZE];
            lastBytesReadCount = fileStream.read(buffer, 0, BUFFER_SIZE);

            if (lastBytesReadCount >= 0) {
                processedBytesCount += lastBytesReadCount;
                currentPartNumber++;

                try (ByteArrayInputStream partStream = new ByteArrayInputStream(buffer, 0, lastBytesReadCount)) {
                    UploadPartRequest req = new UploadPartRequest()
                        .withBucketName(bucketName)
                        .withKey(objectKey)
                        .withUploadId(uploadId)
                        .withInputStream(partStream)
                        .withPartNumber(currentPartNumber)
                        .withPartSize(lastBytesReadCount);

                    partETags.add(s3.uploadPart(req).getPartETag());

                } catch (Exception ex) {
                    log.error("Error processing upload part {} for object {} with upload id {}", currentPartNumber, objectKey, uploadId);
                    throw ex;
                }
            }
        }

        log.info("Uploaded {} bytes in {} parts for {}", processedBytesCount, currentPartNumber, objectKey);

        return partETags;
    }

    public S3File getFile(String objectPath) {
        return new S3FileProxy(s3.getObject(bucketName, objectPath));
    }
}
