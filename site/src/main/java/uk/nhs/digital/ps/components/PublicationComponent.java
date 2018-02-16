package uk.nhs.digital.ps.components;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoFolder;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.onehippo.cms7.essentials.components.EssentialsContentComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.nhs.digital.ps.beans.Publication;

public class PublicationComponent extends EssentialsContentComponent {

    private static final Logger log = LoggerFactory.getLogger(PublicationComponent.class);

    @Override
    public void doBeforeRender(final HstRequest request, final HstResponse response) throws HstComponentException {
        super.doBeforeRender(request, response);
        final HstRequestContext ctx = request.getRequestContext();
        Publication publication = getPublication(ctx);

        request.setAttribute("publication", publication);
    }

    private Publication getPublication(HstRequestContext ctx) throws HstComponentException {
        HippoBean content = ctx.getContentBean();

        if (content.getClass().equals(Publication.class)) {
            return (Publication) content;
        }
        if (content.getClass().equals(HippoFolder.class)) {
            return Publication.getPublicationInFolder((HippoFolder)content);
        }

        log.warn("Cannot find Publication document for: {}", content.getPath());
        throw new HstComponentException("Cannot find Publication document based on request content");
    }
}
