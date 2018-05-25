<#ftl output_format="HTML">
<#include "./macro/sections/sections.ftl">
<#include "./macro/publicationHeader.ftl">
<#include "./publication.ftl">

<#include "../include/imports.ftl">
<#include "../common/macro/sectionNav.ftl">

<#function getSectionNavLinks index>
    <#assign links = [] />
    
    <#if index?has_content>
        <#list index as i>
            <#assign links += [{ "url": "#" + slugify(i), "title": i }] />
        </#list>
    </#if>    
    
    <#return links />
</#function>

<article class="article article--chaptered-publication">
    <@publicationHeader publication=page.publication/>

    <div class="grid-wrapper grid-wrapper--article article-section" aria-label="Document Content">
        <div class="grid-row">
            <#-- <#if index?has_content && index?size gt 1> -->
            <#if index?has_content>
            <div class="column column--one-third page-block page-block--sidebar sticky sticky--top">
                <@sectionNav getSectionNavLinks(index)></@sectionNav>
            </div>
            </#if>

            <div class="column column--two-thirds page-block page-block--main">
                <div class="article-section">
                    <h1 data-uipath="ps.publication.page-title" title="${page.title}">${page.title}</h1>
                </div>
                
                <div class="article-section no-border">
                    <#if page.bodySections?has_content>
                        <@sections sections=page.bodySections />
                    </#if>
                </div>
            </div>
        </div>
    </div>
</article>
