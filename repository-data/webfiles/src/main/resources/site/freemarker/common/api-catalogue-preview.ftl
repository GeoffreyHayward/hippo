<#ftl output_format="HTML">
<#include "../include/imports.ftl">
<#include "macro/metaTags.ftl">
<#include "macro/contentPixel.ftl">
<#include "macro/scrollableFilterNav-newDesign.ftl">
<#include "macro/apiCatalogueEntries-newDesign.ftl">
<#include "macro/svgIcons.ftl">
<#include "../nhsd-common/macros/header-banner.ftl">

<#-- @ftlvariable name="document" type="uk.nhs.digital.website.beans.ComponentList" -->
<#-- @ftlvariable name="filtersModel" type="uk.nhs.digital.common.components.apicatalogue.filters.Filters" -->
<#-- @ftlvariable name="section" type="uk.nhs.digital.common.components.apicatalogue.filters.Section" -->
<#-- @ftlvariable name="filter" type="uk.nhs.digital.common.components.apicatalogue.filters.Subsection" -->

<#-- Add meta tags -->
<@metaTags></@metaTags>
<#-- Content Page Pixel -->
<@contentPixel document.getCanonicalUUID() document.title></@contentPixel>

<#assign alphabetical_hash = group_blocks(flat_blocks(apiCatalogueLinks true))/>

<@headerBanner document />

<div class="nhsd-t-grid nhsd-!t-margin-top-6">

    <div class="nhsd-t-row">
        <div class="nhsd-t-col-12">
            <#if document.body?has_content??>
                <@hst.html hippohtml=document.body contentRewriter=brContentRewriter/>
            </#if>
        </div>
    </div>


    <#if alphabetical_hash??>
    <div class="nhsd-t-row">
        <div class="nhsd-t-col-3 nhsd-!t-display-hide nhsd-!t-display-l-show">
            <div class="nhsd-a-box nhsd-a-box--border-grey nhsd-!t-margin-right-3 nhsd-!t-display-sticky nhsd-!t-display-sticky--offset-2">
                <@scrollableFilterNav alphabetical_hash filtersModel></@scrollableFilterNav>
            </div>
        </div>

        <div class="nhsd-t-col-l-9 nhsd-t-col-m-12">
            <@apiCatalogueEntries alphabetical_hash filtersModel></@apiCatalogueEntries>
        </div>
    </div>

    </#if>
</div>
