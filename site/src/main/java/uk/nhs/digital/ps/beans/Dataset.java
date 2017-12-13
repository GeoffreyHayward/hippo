package uk.nhs.digital.ps.beans;

import org.hippoecm.hst.content.beans.standard.HippoFolder;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import java.util.Calendar;
import java.util.List;
import java.util.Collection;

import org.hippoecm.hst.content.beans.standard.HippoResourceBean;
import uk.nhs.digital.ps.beans.structuredText.StructuredText;
import uk.nhs.digital.ps.site.exceptions.DataRestrictionViolationException;

import static java.util.Arrays.asList;

@HippoEssentialsGenerated(internalName = "publicationsystem:dataset")
@Node(jcrType = "publicationsystem:dataset")
public class Dataset extends BaseDocument {

    private static final Collection<String> propertiesPermittedWhenUpcoming = asList(
        PropertyKeys.TITLE,
        PropertyKeys.SUMMARY,
        PropertyKeys.NOMINAL_DATE
    );

    @HippoEssentialsGenerated(internalName = PropertyKeys.TITLE)
    public String getTitle() {
        return getPropertyIfPermitted(PropertyKeys.TITLE);
    }

    @HippoEssentialsGenerated(internalName = PropertyKeys.SUMMARY)
    public StructuredText getSummary() {
        assertPropertyPermitted(PropertyKeys.SUMMARY);
        return new StructuredText(getProperty(PropertyKeys.SUMMARY, ""));
    }

    @HippoEssentialsGenerated(internalName = PropertyKeys.NOMINAL_DATE)
    public Calendar getNominalDate() {
        return getPropertyIfPermitted(PropertyKeys.NOMINAL_DATE);
    }

    @HippoEssentialsGenerated(internalName = PropertyKeys.NEXT_PUBLICATION_DATE)
    public Calendar getNextPublicationDate() {
        return getPropertyIfPermitted(PropertyKeys.NEXT_PUBLICATION_DATE);
    }

    @HippoEssentialsGenerated(internalName = PropertyKeys.GEOGRAPHIC_COVERAGE)
    public String getGeographicCoverage() {
        return getPropertyIfPermitted(PropertyKeys.GEOGRAPHIC_COVERAGE);
    }

    @HippoEssentialsGenerated(internalName = PropertyKeys.TAXONOMY)
    public String[] getKeys() {
        return getPropertyIfPermitted(PropertyKeys.TAXONOMY);
    }

    /*
     * Migration: 20171208
     * New field: publicationsystem:Files-v2
     * Old field: publicationsystem:Files
     *
     * Temporary make this getter forward and backward compatible, meaning we can deploy this code without running
     * content migration.
     */
    @HippoEssentialsGenerated(internalName = PropertyKeys.FILES_V2)
    public List<HippoResourceBean> getFiles() {
        assertPropertyPermitted(PropertyKeys.FILES_V2);
        List<HippoResourceBean> attachments = getChildBeansByName(PropertyKeys.FILES_V2, HippoResourceBean.class);

        if (attachments.isEmpty()) {
            attachments = getChildBeansByName(PropertyKeys.FILES, HippoResourceBean.class);
        }

        return attachments;
    }

    public List<Relatedlink> getResourceLinks() {
        assertPropertyPermitted(PropertyKeys.RESOURCE_LINKS);
        return getChildBeansByName(PropertyKeys.RESOURCE_LINKS, Relatedlink.class);
    }

    @HippoEssentialsGenerated(internalName = PropertyKeys.GRANULARITY)
    public String[] getGranularity() {
        return getPropertyIfPermitted(PropertyKeys.GRANULARITY);
    }

    @HippoEssentialsGenerated(internalName = PropertyKeys.COVERAGE_START)
    public Calendar getCoverageStart() {
        return getPropertyIfPermitted(PropertyKeys.COVERAGE_START);
    }

    @HippoEssentialsGenerated(internalName = PropertyKeys.COVERAGE_END)
    public Calendar getCoverageEnd() {
        return getPropertyIfPermitted(PropertyKeys.COVERAGE_END);
    }

    public Publication getParentPublication() {
        Publication publicationBean = null;

        HippoFolder folder = (HippoFolder) getParentBean();
        while (!HippoBeanHelper.isRootFolder(folder)) {
            publicationBean = Publication.getPublicationInFolder(folder);

            if (publicationBean != null) {
                break;
            } else {
                folder = (HippoFolder) folder.getParentBean();
            }
        }

        return publicationBean;
    }

    private <T> T getPropertyIfPermitted(final String propertyKey) {
        assertPropertyPermitted(propertyKey);

        return getProperty(propertyKey);
    }

    private void assertPropertyPermitted(final String propertyKey) {

        final boolean isPropertyPermitted =
            propertiesPermittedWhenUpcoming.contains(propertyKey) || isPubliclyAccessible();

        if (!isPropertyPermitted) {
            throw new DataRestrictionViolationException(
                "Property is not available when parent publication is flagged as 'not publicly accessible': " + propertyKey
            );
        }
    }

    public boolean isPubliclyAccessible() {
        return getParentPublication().isPubliclyAccessible();
    }

    interface PropertyKeys {
        String TITLE = "publicationsystem:Title";
        String SUMMARY = "publicationsystem:Summary";
        String TAXONOMY = "hippotaxonomy:keys";
        String NOMINAL_DATE = "publicationsystem:NominalDate";
        String NEXT_PUBLICATION_DATE = "publicationsystem:NextPublicationDate";
        String COVERAGE_START = "publicationsystem:CoverageStart";
        String COVERAGE_END = "publicationsystem:CoverageEnd";
        String GEOGRAPHIC_COVERAGE = "publicationsystem:GeographicCoverage";
        String GRANULARITY = "publicationsystem:Granularity";
        String RESOURCE_LINKS = "publicationsystem:ResourceLinks";
        String FILES = "publicationsystem:Files";
        String FILES_V2 = "publicationsystem:Files-v2";
    }
}
