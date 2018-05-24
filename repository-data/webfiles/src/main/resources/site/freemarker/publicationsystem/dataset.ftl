<#ftl output_format="HTML">
<#include "../include/imports.ftl">
<#include "./macro/structured-text.ftl">
<@hst.setBundle basename="publicationsystem.headers,publicationsystem.labels"/>

<#if dataset??>
<div itemscope itemtype="http://schema.org/Dataset">
<section class="document-header" aria-label="Document Header">
    <div class="document-header__inner">
        <meta itemprop="keywords" content="${dataset.fullTaxonomyList?join(",")}"/>

        <h3 class="flush--bottom push-half--top"><@fmt.message key="labels.dataset"/></h3>
        <h1 class="layout-5-6" data-uipath="document.title" itemprop="name">${dataset.title}</h1>
        <p class="flush--top" itemprop="isPartOf" itemscope itemtype="http://schema.org/PublicationIssue">
            This data set is part of
            <a class="label label--parent-document"
               href="<@hst.link hippobean=dataset.parentPublication.selfLinkBean/>"
               title="${dataset.parentPublication.title}"
               itemprop="url"
            ><span itemprop="name">${dataset.parentPublication.title}</span></a>
        </p>

        <div class="flex push--top push--bottom">
            <div class="flex__item">
                <div class="media">
                    <div class="media__icon media__icon--date-range"></div>
                    <dl class="media__body">
                        <dt><@fmt.message key="headers.date-range"/></dt>
                        <dd data-uipath="ps.dataset.date-range">
                            <#if dataset.coverageStart?? && dataset.coverageEnd??>
                                <@formatCoverageDates start=dataset.coverageStart.time end=dataset.coverageEnd.time/>
                                <meta itemprop="temporalCoverage" content="<@formatCoverageDates start=dataset.coverageStart.time end=dataset.coverageEnd.time schemaFormat=true/>" />
                            <#else>
                                (Not specified)
                            </#if>
                        </dd>
                    </dl>
                </div>
            </div><!--

            --><#if dataset.geographicCoverage?has_content><div class="flex__item">
                <div class="media">
                    <div class="media__icon media__icon--granularity"></div>
                    <dl class="media__body">
                        <dt id="geographic-coverage"><@fmt.message key="headers.geographical-coverage"/></dt>
                        <dd itemprop="spatialCoverage" data-uipath="ps.dataset.geographic-coverage">
                            <#list dataset.geographicCoverage as geographicCoverageItem>${geographicCoverageItem}<#sep>, </#list>
                        </dd>
                    </dl>
                </div>
            </div></#if><!--

            --><#if dataset.granularity?has_content ><div class="flex__item">
                <div class="media">
                    <div class="media__icon media__icon--geographic-coverage"></div>
                    <dl class="media__body">
                        <dt><@fmt.message key="headers.geographical-granularity"/></dt>
                        <dd data-uipath="ps.dataset.granularity">
                            <#list dataset.granularity as granularityItem>${granularityItem}<#sep>, </#list>
                        </dd>
                    </dl>
                </div>
            </div></#if><!--

            --><#if dataset.nominalDate?has_content ><div class="flex__item">
                <div class="media">
                    <div class="media__icon media__icon--calendar"></div>
                    <dl class="media__body">
                        <dt><@fmt.message key="headers.publication-date"/></dt>
                        <dd data-uipath="ps.dataset.nominal-date" itemprop="datePublished">
                            <@formatRestrictableDate value=dataset.nominalDate/>
                        </dd>
                    </dl>
                </div>
            </div></#if><!--

            --><#if dataset.nextPublicationDate?has_content ><div class="flex__item">
                <div class="media">
                    <div class="media__icon media__icon--calendar"></div>
                    <dl class="media__body">
                        <dt><@fmt.message key="headers.next-publication-date"/></dt>
                        <dd data-uipath="ps.dataset.next-publication-date">
                            <@formatDate date=dataset.nextPublicationDate.time/>
                        </dd>
                    </dl>
                </div>
            </div></#if>

        </div>
    </div>
</section>

<section class="document-content" aria-label="Document Content">
    <div>
        <h2><@fmt.message key="headers.summary"/></h2>
        <div itemprop="description"><@structuredText item=dataset.summary uipath="ps.dataset.summary" /></div>

        <h2><@fmt.message key="headers.resources"/></h2>
        <ul data-uipath="ps.dataset.resources">
            <#list dataset.files as attachment>
                <li class="attachment" itemprop="hasPart" itemscope itemtype="http://schema.org/MediaObject">
                    <@externalstorageLink attachment.resource; url>
                    <a itemprop="contentUrl" title="${attachment.text}" href="${url}" onClick="logGoogleAnalyticsEvent('Download attachment','Data set','${attachment.resource.filename}');" onKeyUp="return vjsu.onKeyUp(event)"><span itemprop="name">${attachment.text}</span></a>;
                    </@externalstorageLink>
                    <span class="fileSize">[size: <span itemprop="contentSize"><@formatFileSize bytesCount=attachment.resource.length/></span>]</span>
                </li>
            </#list>
            <#list dataset.resourceLinks as link>
                <li>
                    <a href="${link.linkUrl}" onClick="logGoogleAnalyticsEvent('Link click','Data set','${link.linkUrl}');" onKeyUp="return vjsu.onKeyUp(event)">${link.linkText}</a>
                </li>
            </#list>
        </ul>
    </div>
</section>
</div>

<#else>
    <span>${error}</span>
</#if>
