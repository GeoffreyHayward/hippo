<#ftl output_format="HTML">
<#include "../include/imports.ftl">
<#include "macro/articleSections.ftl">
<#include "macro/sectionNav.ftl">
<#include "macro/furtherInformationSection.ftl">
<#include "macro/metaTags.ftl">

<#-- Add meta tags -->
<@metaTags></@metaTags>

<#assign hasSectionContent = document.sections?has_content />
<#assign hasTopTasks = document.toptasks?has_content />
<#assign hasChildPages = childPages?has_content />
<#assign hasIntroductionContent = document.introduction?? />
<#assign hasContactDetailsContent = document.contactdetails?? && document.contactdetails.content?has_content?? />

<#assign sectionTitlesFound = countSectionTitles(document.sections) />
<#assign renderNav = (hasSectionContent && (sectionTitlesFound gte 2 || (sectionTitlesFound gte 1 && hasChildPages))) />

<article class="article article--service">
    <div class="grid-wrapper grid-wrapper--article">
        <div class="local-header article-header">
            <h1 class="local-header__title">${document.title}</h1>
        </div>

        <div class="grid-row">
            <#if renderNav>
            <div class="column column--one-third page-block page-block--sidebar sticky sticky--top">
                <@sectionNav getSectionNavLinks({ "document": document, "childPages": childPages })></@sectionNav>
            </div>
            </#if>

            <div class="column column--two-thirds page-block page-block--main">
                <#if hasTopTasks>
                    <#assign summarySectionClassName = "article-section article-section--summary no-border">
                <#else>
                    <#assign summarySectionClassName = "article-section article-section--summary">
                </#if>

                <div id="${slugify('Summary')}" class="${summarySectionClassName}">
                    <h2>Summary</h2>
                    <p>${document.summary}</p>
                </div>

                <#if hasTopTasks>
                <div class="article-section article-section--highlighted">
                    <div class="callout callout--attention">
                        <h2>Top tasks</h2>
                        <div class="rich-text-content">
                            <#list document.toptasks as toptask>
                            <@hst.html hippohtml=toptask contentRewriter=gaContentRewriter/>
                            </#list>
                        </div>
                    </div>
                </div>
                </#if>

                <#if hasIntroductionContent>
                <div class="article-section article-section--introduction">
                    <div class="rich-text-content">
                        <@hst.html hippohtml=document.introduction contentRewriter=gaContentRewriter/>
                    </div>
                </div>
                </#if>

                <@articleSections document.sections></@articleSections>

                <#if hasContactDetailsContent>
                <div class="article-section article-section--contact" id="${slugify('Contact details')}">
                    <h2>Contact details</h2>
                    <div class="rich-text-content">
                        <@hst.html hippohtml=document.contactdetails contentRewriter=gaContentRewriter/>
                    </div>
                </div>
                </#if>

                <@furtherInformationSection childPages></@furtherInformationSection>
            </div>
        </div>
    </div>
</article>
