package uk.nhs.digital.ps.components;

import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.onehippo.cms7.essentials.components.EssentialsContentComponent;
import uk.nhs.digital.ps.beans.PublicationPage;

public class PublicationPageComponent extends EssentialsContentComponent {

    private final PageSectionGrouper pageSectionGrouper = new PageSectionGrouper();

    @Override
    public void doBeforeRender(final HstRequest request, final HstResponse response) throws HstComponentException {
        super.doBeforeRender(request, response);
        PublicationPage page = (PublicationPage) request.getRequestContext().getContentBean();

        request.setAttribute("page", page);
        request.setAttribute("pageSections", pageSectionGrouper.groupSections(page.getSections()));
    }

}
