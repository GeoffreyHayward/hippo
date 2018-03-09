package uk.nhs.digital.externalstorage.s3;

import org.onehippo.cms7.services.SingletonService;

import java.io.InputStream;

@SingletonService
public interface S3Connector {

    String getBucketName();

    boolean publishResource(String objectPath);

    boolean unpublishResource(String objectPath);

    S3ObjectMetadata uploadFile(InputStream fileStream, String objectPath, String contentType);

    /**
     * <p>
     * Retrieves a file identified by the parameter from S3.
     * </p>
     * <p>
     * Aim to call this method once and make sure to close the stream returned by
     * {@linkplain S3File#getContent()} as soon as the {@linkplain S3File} instance is no longer needed. See
     * description of {@linkplain S3File#getContent()} and of
     * <a href="https://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/com/amazonaws/services/s3/AmazonS3Client.html#getObject-java.lang.String-java.lang.String-">
     * AmazonS3#getObject</a> method for more details.
     * </p>
     *
     * @param objectPath Path, uniquely idendifying the file in S3.
     * @return Proxy to S3 file.
     */
    S3File getFile(String objectPath);
}
