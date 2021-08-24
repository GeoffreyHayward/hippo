<#ftl output_format="HTML">

<#-- @ftlvariable name="document" type="uk.nhs.digital.website.beans.Policy" -->

<#include "../include/imports.ftl">
<#include "../nhsd-common/macro/sections/sections.ftl">
<#include "../nhsd-common/macro/component/lastModified.ftl">
<#include "../nhsd-common/macro/component/pagination.ftl">
<#include "../nhsd-common/macro/component/chapter-pagination.ftl">
<#include "macro/stickyNavSections.ftl">
<#include "macro/metaTags.ftl">
<#include "../common/macro/fileMetaAppendix.ftl">
<#include "../common/macro/fileIconByMimeType.ftl">
<#include "../nhsd-common/macro/published-work-banners/text-banner.ftl">
<#include "../nhsd-common/macro/published-work-banners/hero-module.ftl">
<#include "../nhsd-common/macro/published-work-banners/slim-picture.ftl">
<#include "../nhsd-common/macro/component/downloadBlockAsset.ftl">
<#include "macro/contentPixel.ftl">
<#include "macro/documentIcon.ftl">

<#-- Add meta tags -->
<@metaTags></@metaTags>

<@hst.setBundle basename="rb.doctype.published-work,publicationsystem.headers"/>

<#--  Define variables  -->
<#assign bannerImage = "" />
<#assign bannerImageAltText = "" />
<#assign button = "" />
<#assign buttonText = "" />

<#-- Fall back to Blue Banner if no publication style is defined -->
<#if document.publicationStyle.publicationStyle??>
    <#assign publicationStyle = document.publicationStyle.publicationStyle />
<#else>
    <#assign publicationStyle = "bluebanner" />
</#if>
<#-- Don't use the summary in the content when the hero module is active -->
<#if publicationStyle = 'heromodule'>
    <#assign hasSummaryContent = false />
<#else>
    <#assign hasSummaryContent = document.summary?? && document.summary.content?has_content />
</#if>

<#assign hasSectionContent = document.sections?has_content />
<#assign childPages = document.links />
<#assign hasChildPages = childPages?? && childPages?size gt 0 />
<#assign hasHighlightsContent = document.highlightSection.hightlightSection?? && document.highlightSection?has_content />
<#assign hasResources = document.resources?has_content />

<#assign sectionTitlesFound = countSectionTitles(document.sections) />
<@fmt.message key="headers.about-this-publication" var="aboutThisPublicationHeader" />
<#assign renderNav = (hasSummaryContent && sectionTitlesFound gte 1) || (sectionTitlesFound gt 1) />

<#-- Content Page Pixel -->
<@contentPixel document.getCanonicalUUID() document.title></@contentPixel>

<div class="nhsd-t-grid nhsd-t-grid--full-width nhsd-!t-display-chapters"
     aria-label="Document Header">

    <@hst.headContributions categoryIncludes="testscripts"/>

    <#--  Commented out until blue banner ticket is unblocked. It will use hero until then  -->

    <#--  <#if publicationStyle == 'bluebanner'>
        <@textBanner document />
    </#if>  -->

    <#if publicationStyle == 'heromodule' || publicationStyle == 'bluebanner'>
        <#if document.publicationStyle.bannerImage.pageHeaderHeroModule??>
            <@hst.link hippobean=document.publicationStyle.bannerImage.pageHeaderHeroModule fullyQualified=true var="selectedBannerImage" />
            <#assign bannerImage = selectedBannerImage />
        </#if>
        <#assign heroConfig = {
        "document": document,
        "bannerImage": bannerImage,
        "bannerImageAltText": document.publicationStyle.imageAltText,
        "button": document.publicationStyle.button,
        "buttonText": "Jump to overview",
        "showTime": true
        }
        />
        <@heroModule heroConfig true/>
    </#if>

    <#if publicationStyle == 'slimpicture'>
        <#if document.publicationStyle.bannerImage??>
            <@hst.link hippobean=document.publicationStyle.bannerImage.pageHeaderSlimBannerSmall2x fullyQualified=true var="selectedBannerImage" />
            <#assign bannerImage = selectedBannerImage />
        </#if>
        <#assign slimPictureConfig = {
        "document": document,
        "bannerImage": bannerImage,
        "bannerImageAltText": document.publicationStyle.imageAltText
        }
        />
        <@slimPicture slimPictureConfig />
    </#if>

    <#if hasChildPages>

        <#assign documents = [] />

    <#-- Cache the parent document's details -->
        <@hst.link hippobean=document var="link" />
        <#assign documents = [{ "index": 0, "id": document.identifier, "title": document.title, "link": link }] />
    <#-- Cache the chapter details -->
        <#list childPages as chapter>
            <@hst.link hippobean=chapter var="link" />
            <#assign documents += [{ "index": chapter?counter, "id": chapter.identifier, "title": chapter.title, "link": link }] />
        </#list>
        <@chapterNav document />
    </#if>
    <div class="nhsd-t-grid">
        <div class="nhsd-t-row" id="document-content">
            <#if renderNav>
                <div class="nhsd-t-col-xs-12 nhsd-t-col-s-4">
                    <!-- start sticky-nav -->
                    <@fmt.message key="headers.page-contents" var="pageContentsHeader" />
                    <#assign links = [] />
                    <@stickyNavSections getStickySectionNavLinks({ "document": document, "sections": links, "includeSummary": hasSummaryContent}), pageContentsHeader></@stickyNavSections>

                    <#-- Restore the bundle -->
                    <@hst.setBundle basename="rb.doctype.published-work,publicationsystem.headers"/>
                    <!-- end sticky-nav -->
                </div>
            </#if>

            <div class="nhsd-t-col-xs-12 nhsd-t-col-s-8">
                <#if hasSummaryContent>
                    <div id="${slugify('Summary')}">
                        <p class="nhsd-t-heading-xl"><@fmt.message key="headers.summary"/></p>
                        <div data-uipath="website.publishedwork.summary"><@hst.html hippohtml=document.summary contentRewriter=brContentRewriter/></div>
                    </div>
                </#if>

                <#if hasHighlightsContent>
                    <#if hasSummaryContent>
                        <hr class="nhsd-a-horizontal-rule"/>
                    </#if>

                    <p class="nhsd-t-heading-xl">${document.highlightSection.hightlightsTitle}</p>
                    <@hst.html hippohtml=document.highlightsContent contentRewriter=brContentRewriter/>
                </#if>

                <#if hasSectionContent>
                    <#if hasHighlightsContent || (hasSummaryContent && !hasHighlightsContent)>
                        <hr class="nhsd-a-horizontal-rule"/>
                    </#if>

                    <@sections document.sections></@sections>
                </#if>

                <div class="nhsd-!t-margin-bottom-6">
                    <@lastModified document.lastModified false></@lastModified>
                </div>

                <div class="nhsd-!t-margin-bottom-8">
                    <@pagination document />
                </div>

                <#if hasChildPages>
                    <#assign splitChapters = splitHash(documents) />
                    <div class="nhsd-m-publication-chapter-navigation nhsd-m-publication-chapter-navigation--split nhsd-!t-margin-1"
                         id="chapter-index"
                    >
                        <ol class="nhsd-t-list nhsd-t-list--number nhsd-t-list--loose">
                            <#list splitChapters.left as chapter>
                                <#if chapter.id == document.identifier>
                                    <li class="nhsd-m-publication-chapter-navigation--active">
                                <#else>
                                    <li class="">
                                </#if>
                                <a class="nhsd-a-link"
                                   href="${chapter.link}"
                                   onClick="${getOnClickMethodCall(document.class.name, chapter.link)}"
                                   title="${chapter.title}"
                                >
                                    ${chapter.title}
                                    <span class="nhsd-t-sr-only"></span>
                                </a>
                                </li>
                            </#list>

                            <#if splitChapters.right?size gte 1>
                                <#list splitChapters.right as chapter>
                                    <#if chapter.id == document.identifier>
                                        <li class="nhsd-m-publication-chapter-navigation--active">
                                    <#else>
                                        <li class="">
                                    </#if>
                                    <a class="nhsd-a-link"
                                       href="${chapter.link}"
                                       onClick="${getOnClickMethodCall(document.class.name, chapter.link)}"
                                       title="${chapter.title}"
                                    >
                                        ${chapter.title}
                                        <span class="nhsd-t-sr-only"></span>
                                    </a>
                                    </li>
                                </#list>
                            </#if>
                        </ol>
                    </div>
                </#if>
            </div>
        </div>
    </div>
</div>
<@hst.setBundle basename="rb.doctype.published-work,publicationsystem.headers"/>
