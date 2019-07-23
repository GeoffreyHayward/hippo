package uk.nhs.digital.website.beans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import uk.nhs.digital.indices.StickySection;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE)
@HippoEssentialsGenerated(internalName = "website:section")
@Node(jcrType = "website:section")
public class Section extends BaseCompound implements StickySection {

    @JsonProperty
    @HippoEssentialsGenerated(internalName = "website:title")
    public String getTitle() {
        return getProperty("website:title");
    }

    @Override
    @JsonIgnore
    public String getHeading() {
        return this.getTitle();
    }

    @Override
    @JsonProperty
    @HippoEssentialsGenerated(internalName = "website:headinglevel")
    public String getHeadingLevel() {
        return getProperty("website:headinglevel");
    }

    @JsonProperty
    @HippoEssentialsGenerated(internalName = "website:type")
    public String getType() {
        return getProperty("website:type");
    }

    // Overridden autogenerated version to avoid issues course by getProperty return null
    @JsonProperty
    @HippoEssentialsGenerated(internalName = "website:numberedList", allowModifications = false)
    public boolean getIsNumberedList() {
        Boolean result = getProperty("website:numberedList");
        return result != null && result;
    }

    @JsonProperty("html")
    public String getHtmlJson() {
        HippoHtml html = getHtml();
        if (html != null) {
            return html.getContent();
        }
        return null;
    }

    @HippoEssentialsGenerated(internalName = "website:html")
    public HippoHtml getHtml() {
        return getHippoHtml("website:html");
    }

    @JsonProperty
    public String getSectionType() {
        return "website-section";
    }

    @JsonIgnore
    public boolean isMainHeading() {
        return this.getHeadingLevel() == null || mainHeading();
    }

    @JsonIgnore
    private boolean mainHeading() {
        return this.getHeadingLevel().equalsIgnoreCase("Main heading")
            && this.getHeading() != null
            && !this.getHeading().isEmpty();
    }
}
