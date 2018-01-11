package uk.nhs.digital.ps.test.acceptance.util;

import java.util.HashMap;
import java.util.Map;

import static org.openqa.selenium.net.Urls.urlEncode;

public class TestContentUrls {

    private final Map<String, String> urlLookup = new HashMap();

    public TestContentUrls() {
        setup();
    }

    public String lookupUrl(String pageName) {
        return urlLookup.get(pageName.toLowerCase());
    }

    private void setup() {
        add("home", "/");

        add ("publications overview",
            "/publications");

        // data sets pages
        add("publication with datasets",
            "/publications/acceptance-tests/publication-with-datasets");
        add("publication with datasets dataset",
            "/publications/acceptance-tests/publication-with-datasets/datasets-subfolder/publication-with-datasets-dataset");
        add("series with publication with datasets",
            "/publications/acceptance-tests/series-with-publication-with-datasets");

        // folder
        add("acceptence tests folder", "/publications/acceptance-tests");

        // series page
        add("valid publication series",
            "/publications/valid-publication-series");

        add("publication with rich content",
            "/publications/acceptance-tests/publication-rich");

        // unpublished dataset
        add("upcoming publication dataset",
            "/publications/acceptance-tests/upcoming-publication/upcoming-dataset");

        // attachment tests
        add("attachment test publication",
            "/publications/acceptance-tests/attachment-test");
        add("attachment test dataset",
            "/publications/acceptance-tests/attachment-test/dataset");

        // bare minimum documents
        add("bare minimum publication",
            "/publications/acceptance-tests/bare-minimum-publication");
        add("bare minimum dataset",
            "/publications/acceptance-tests/bare-minimum-publication/bare-minimum-dataset");

        // invalid urls
        add("invalid root", "/invalid");
        add("invalid document", "/publications/invalid");
        add("invalid sub document", "/publications/acceptance-tests/invalid");

        // about pages
        add("terms and conditions", "/about/terms-and-conditions");
        add("accessibility help", "/about/accessibility-help");

        // attachments
        add("attachment-text.pdf",
            getAttachmentUrl("attachment-test/content/content", "Attachments-v3"));
        add("attachment.pdf",
            getAttachmentUrl("attachment-test/content/content", "Attachments-v3[2]"));

        add("dataset-attachment-text.pdf",
            getAttachmentUrl("attachment-test/dataset/dataset", "Files-v3"));
        add("dataset-attachment.pdf",
            getAttachmentUrl("attachment-test/dataset/dataset", "Files-v3[2]"));
    }

    private String getAttachmentUrl(String siteUrl, String attachmentTag) {
        return "/binaries/content/documents/corporate-website/publication-system/acceptance-tests/"
            + siteUrl + "/"
            + urlEncode("publicationsystem:" + attachmentTag) + "/"
            + urlEncode("publicationsystem:attachmentResource");
    }

    private void add(String pageName, String url) {
        urlLookup.put(pageName.toLowerCase(), url);
    }
}
