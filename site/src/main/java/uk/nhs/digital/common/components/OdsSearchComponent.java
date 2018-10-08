package uk.nhs.digital.common.components;

import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;

public class OdsSearchComponent extends SearchComponent {

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) {
        request.setAttribute("query", getQueryParameter(request));
    }
}
