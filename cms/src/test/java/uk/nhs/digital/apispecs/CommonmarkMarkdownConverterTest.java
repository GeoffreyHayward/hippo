package uk.nhs.digital.apispecs;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.MockitoAnnotations.initMocks;
import static uk.nhs.digital.test.util.FileUtils.contentOfFileFromClasspath;

import org.junit.Before;
import org.junit.Test;
import uk.nhs.digital.apispecs.commonmark.CommonmarkMarkdownConverter;

public class CommonmarkMarkdownConverterTest {

    private CommonmarkMarkdownConverter commonmarkMarkdownConverter;

    @Before
    public void setUp() {

        initMocks(this);

        commonmarkMarkdownConverter = new CommonmarkMarkdownConverter();
    }

    @Test
    public void convertsMarkdownWithBackticksToHtmlWithCodeTags() {

        // given
        final String markdownWithBackticks = from("inline-code.md");
        final String expectedHtml = from("inline-code.html");

        // when
        final String actualHtml = commonmarkMarkdownConverter.toHtml(markdownWithBackticks);

        // then
        assertThat(
            "Markdown with backticks is converted to HTML with <code> tags.",
            actualHtml,
            is(expectedHtml)
        );
    }

    @Test
    public void convertsMarkdownWithGfmTablesToHtmlWithTables() {
        // GFM = GitHub Flavoured Markdown

        // given
        final String markdown = from("table.md");
        final String expectedHtml = from("html-with-table-tags.html");

        // when
        final String actualHtml = commonmarkMarkdownConverter.toHtml(markdown);

        // then
        assertThat(
            "Markdown with GFM tables converted to HTML with <table> tags.",
            actualHtml,
            is(expectedHtml)
        );
    }

    @Test
    public void rendersHeadingIds_withCustomHeadingIds_autoGeneratedFromHeadingsTexts() {

        // given
        final String markdown = from("headings.md");
        final String expectedHtml = from("headings-with-ids.html");

        // when
        final String actualHtml = commonmarkMarkdownConverter.toHtml(markdown);

        // then
        assertThat(
            "Headings have id values autogenerated.",
            actualHtml,
            is(expectedHtml)
        );
    }

    @Test
    public void rendersHeadingIds_withCustomHeadingIdPrefixes_whenProvided() {

        // given
        final String markdown = from("headings.md");
        final String expectedHtml = from("headings-with-prefixed-ids.html");

        // when
        final String actualHtml = commonmarkMarkdownConverter.toHtml(markdown, "customPrefix__");

        // then
        assertThat(
            "Heading id values are prefixed with provided prefix.",
            actualHtml,
            is(expectedHtml)
        );
    }

    private String from(final String testDataFileName) {
        return contentOfFileFromClasspath(
            "/test-data/api-specifications/CommonmarkMarkdownConverterTest/" + testDataFileName
        );
    }
}
