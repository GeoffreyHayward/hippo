package uk.nhs.digital.externalstorage.resource;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.ContentDisposition;
import org.apache.wicket.request.resource.ResourceStreamResource;
import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.util.resource.AbstractResourceStream;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;
import org.hippoecm.frontend.model.IModelReference;
import org.hippoecm.frontend.plugin.IPluginContext;
import org.hippoecm.frontend.plugin.config.IPluginConfig;
import org.hippoecm.frontend.plugins.standards.util.ByteSizeFormatter;
import org.hippoecm.frontend.service.IEditor;
import org.hippoecm.frontend.service.render.RenderPlugin;
import org.onehippo.cms7.services.HippoServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.nhs.digital.externalstorage.ExternalStorageConstants;
import uk.nhs.digital.externalstorage.s3.S3Connector;
import uk.nhs.digital.externalstorage.s3.S3File;

import java.io.IOException;
import java.io.InputStream;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

public class ImageDisplayPlugin extends RenderPlugin<Node> {

    private static final Logger log = LoggerFactory.getLogger(ImageDisplayPlugin.class);

    public static final String MIME_TYPE_HIPPO_BLANK = "application/vnd.hippo.blank";

    ByteSizeFormatter formatter = new ByteSizeFormatter();
    final S3Connector s3ConnectorService;

    public ImageDisplayPlugin(IPluginContext context, IPluginConfig config) {
        super(context, config);

        s3ConnectorService = HippoServiceRegistry.getService(S3Connector.class);

        IEditor.Mode mode = IEditor.Mode.fromString(config.getString("mode"), IEditor.Mode.VIEW);
        if (mode == IEditor.Mode.COMPARE && config.containsKey("model.compareTo")) {
            IModelReference<Node> baseModelRef = context.getService(config.getString("model.compareTo"),
                IModelReference.class);
            boolean doCompare = false;
            if (baseModelRef != null) {
                IModel<Node> baseModel = baseModelRef.getModel();
                Node baseNode = baseModel.getObject();
                Node currentNode = getModel().getObject();
                if (baseNode != null && currentNode != null) {
                    try {
                        String baseNodeReference = baseNode.getProperty(ExternalStorageConstants.PROPERTY_EXTERNAL_STORAGE_REFERENCE).getString();
                        String currentNodeReference = currentNode.getProperty(ExternalStorageConstants.PROPERTY_EXTERNAL_STORAGE_REFERENCE).getString();
                        doCompare = baseNodeReference != currentNodeReference;
                    } catch (RepositoryException ex) {
                        log.error("Could not compare references", ex);
                    }
                }
            }
            if (doCompare) {
                Fragment fragment = new Fragment("fragment", "compare", this);
                Fragment baseFragment = createResourceFragment("base", baseModelRef.getModel());
                baseFragment.add(new AttributeAppender("class", new Model<String>("hippo-diff-removed"), " "));
                fragment.add(baseFragment);

                Fragment currentFragment = createResourceFragment("current", getModel());
                currentFragment.add(new AttributeAppender("class", new Model<String>("hippo-diff-added"), " "));
                fragment.add(currentFragment);
                add(fragment);
            } else {
                add(createResourceFragment("fragment", getModel()));
            }
        } else {
            add(createResourceFragment("fragment", getModel()));
        }
    }

    private Fragment createResourceFragment(String id, IModel<Node> model) {
        S3NodeMetadata metadata = new S3NodeMetadata(model.getObject());
        Fragment fragment = new Fragment(id, "unknown", this);

        try {
            if (metadata.getSize() < 0) {
                return fragment;
            }

            fragment = new Fragment(id, "embed", this);
            fragment.add(new Label("filesize", Model.of(formatter.format(metadata.getSize()))));
            fragment.add(new Label("mimetype", Model.of(metadata.getMimeType())));

            if (!metadata.getFileName().isEmpty()) {
                fragment.add(this.createFileLink(metadata));
            }

            if (metadata.getMimeType().equals(MIME_TYPE_HIPPO_BLANK)) {
                fragment.setVisible(false);
            }
        } catch (RepositoryException ex) {
            log.error("Error while creating embedded resource view", ex);
        }

        return fragment;
    }

    private ResourceLink createFileLink(S3NodeMetadata metadata) throws RepositoryException {
        S3FileResourceStream s3FileResourceStream = new S3FileResourceStream(metadata, s3ConnectorService);
        ResourceStreamResource resourceStreamResource = new ResourceStreamResource(s3FileResourceStream);
        resourceStreamResource.setFileName(metadata.getFileName());
        resourceStreamResource.setContentDisposition(ContentDisposition.ATTACHMENT);

        ResourceLink filelink = new ResourceLink("link", resourceStreamResource);
        filelink.add(new Label("filename", new Model<>(metadata.getFileName())));
        return filelink;
    }

    @Override
    protected void onModelChanged() {
        replace(createResourceFragment("fragment", getModel()));
        super.onModelChanged();
        redraw();
    }

    private static class S3FileResourceStream extends AbstractResourceStream {

        private final S3NodeMetadata metadata;
        private final S3Connector s3ConnectorService;

        public S3FileResourceStream(S3NodeMetadata metadata, S3Connector s3ConnectorService) {
            this.metadata = metadata;
            this.s3ConnectorService = s3ConnectorService;
        }

        @Override
        public String getContentType() {
            return metadata.getMimeType();
        }

        @Override
        public Bytes length() {
            return Bytes.bytes(metadata.getSize());
        }

        @Override
        public InputStream getInputStream() throws ResourceStreamNotFoundException {
            //TODO Error handling (throw a ResourceStreamNotFoundException)
            final S3File s3File = s3ConnectorService.getFile(metadata.getReference());
            return s3File.getContent();
        }

        @Override
        public void close() throws IOException {
            //TODO Is this needed?
        }
    }

}
