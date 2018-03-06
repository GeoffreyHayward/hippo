package uk.nhs.digital.ps.test.acceptance.util;

import static org.openqa.selenium.net.Urls.urlEncode;

import uk.nhs.digital.ps.test.acceptance.pages.site.AbstractSitePage;

import java.util.HashMap;
import java.util.Map;

public class TestContentUrls {

    private final Map<String, String> urlLookup = new HashMap();

    public TestContentUrls() {
        setup();
    }

    public String lookupUrl(String pageName) {
        String url = urlLookup.get(pageName.toLowerCase());
        if (url == null) {
            throw new RuntimeException("Unknown pageName: " + pageName);
        }

        return AbstractSitePage.URL + url;
    }

    private void setup() {
        add("home", "/");

        add("search", "/search");

        add("publications overview",
            "/publications");

        add("shmi", "/shmi");

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
        add("valid publication series direct",
            "/publications/valid-publication-series/time-series-index");
        add("series without latest",
            "/publications/acceptance-tests/series-without-latest");

        // archive page
        add("valid publication archive",
            "/publications/acceptance-tests/valid-publication-archive");
        add("valid publication archive direct",
            "/publications/acceptance-tests/valid-publication-archive/time-archive-lorem-ipsum-dolor");

        add("publication with rich content",
            "/publications/acceptance-tests/publication-rich");

        // unpublished dataset
        add("upcoming publication dataset",
            "/publications/acceptance-tests/upcoming-publication/upcoming-dataset");

        // dataset with no parent publication
        add("dataset without publication",
            "/publications/acceptance-tests/dataset-without-publication/dataset-without-publication");

        // attachment tests
        add("attachment test publication",
            "/publications/acceptance-tests/attachment-test");
        add("attachment test dataset",
            "/publications/acceptance-tests/attachment-test/dataset");

        // coverage date test
        add("coverage date publication",
            "/publications/acceptance-tests/coveragedates-test/coverage-test");

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

        // CI Hub/Landing
        add("SHMI landing", "/publications/ci-hub/summary-hospital-level-mortality-indicator-shmi");
        add("SHMI timetable attachment",
            "/binaries/content/documents/corporate-website/publication-system/ci-hub/summary-hospital-level-mortality-indicator-shmi/summary-hospital-level-mortality-indicator-shmi/publicationsystem%3Acilandingasset/publicationsystem%3AAttachments/publicationsystem%3AattachmentResource");
        add("ci hub root", "/publications/ci-hub");

        // attachments
        add("attachment-text.pdf",
            getAttachmentUrl("attachment-test/content/content", "Attachments-v3"));
        add("attachment.pdf",
            getAttachmentUrl("attachment-test/content/content", "Attachments-v3[2]"));

        add("dataset-attachment-text.pdf",
            getAttachmentUrl("attachment-test/dataset/dataset", "Files-v3"));
        add("dataset-attachment.pdf",
            getAttachmentUrl("attachment-test/dataset/dataset", "Files-v3[2]"));

        // Ordered publication
        add("ordered publication",
            "/publications/acceptance-tests/ordered-publication");

        // Sectioned publication
        add("sectioned publication",
            "/publications/acceptance-tests/sectioned-publication");
        add("sectioned publication robots",
            getAttachmentUrl("sectioned-publication/content/content", "bodySections[2]", "image"));
        add("sectioned publication snowman",
            getAttachmentUrl("sectioned-publication/content/content", "bodySections[5]", "image"));

        // Publication with National Statistic logo
        add("national statistic publication",
            "/publications/lorem-ipsum-content/morbi-tempor-euismod-vehicula");

        // National Indicator Library
        add("nihub","/indicators/nihub");

        add("service with rich content - parent",
            "/services/service-document-1/");

        add("homeland",
            "/homeland");
    }

    private String getAttachmentUrl(String siteUrl, String attachmentTag) {
        return getAttachmentUrl(siteUrl, attachmentTag, "attachmentResource");
    }

    private String getAttachmentUrl(String siteUrl, String attachmentTag, String resourceTag) {
        return "/binaries/content/documents/corporate-website/publication-system/acceptance-tests/"
            + siteUrl + "/"
            + urlEncode("publicationsystem:" + attachmentTag) + "/"
            + urlEncode("publicationsystem:" + resourceTag);
    }

    private void add(String pageName, String url) {
        urlLookup.put(pageName.toLowerCase(), url);
    }
}
