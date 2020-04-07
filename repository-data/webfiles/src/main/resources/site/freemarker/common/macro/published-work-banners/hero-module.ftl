<#ftl output_format="HTML">
<#include "../../../include/imports.ftl">

<#--
config is a hash with the following properties:

document - document object
bannerImage - string: image path
bannerImageAltText - string: alt text
button - string: button state
showTime - boolean: show time from passed document
topText - string: show this top text if no time is supplied
topTextLink - string: make top text into a link if this is supplied

-->
<#macro heroModule config>
    <#assign document = config.document />
    <#assign bannerImage = config.bannerImage />
    <#assign bannerImageAltText = config.bannerImageAltText />
    <#assign button = config.button />
    <#assign showTime = config.showTime />
    <#assign topText = config.topText />
    <#assign topTextLink = config.topTextLink />

    <#local hasSummaryContent = document.summary?? && document.summary.content?has_content />

    <div class="grid-wrapper grid-wrapper--full-width grid-wrapper--wide">
        <div class="hero-module article-header article-header--hero-module"
             style="background-image: url(${bannerImage})">
            <figure class="hero-module__mobile-image">
                <img src="${bannerImage}" alt="${bannerImageAltText}">
            </figure>
            <div class="grid-row">
                <div class="hero-module__inner">
                    <div class="hero-module__content">
                        <#if showTime == true>
                            <time class="hero-module__time"
                                  datetime="<@fmt.formatDate value=document.publicationDate.time?date type="date" pattern="yyyy-MM-d" timeZone="${getTimeZone()}" />"><@fmt.formatDate value=document.publicationDate.time?date type="date" pattern="d MMM yyyy" timeZone="${getTimeZone()}" /></time>
                        <#elseif
                            topText?is_string && topText?length gt 0 &&
                            topTextLink?is_string && topTextLink?length gt 0>
                            <p class="hero-module__toptext"><a href=${topTextLink}>${topText}</a></p>
                        <#elseif topText?is_string && topText?length gt 0>
                            <p class="hero-module__toptext">${topText}</p>
                        </#if>

                        <h1 class="hero-module__title"
                            data-uipath="document.title">${document.title}</h1>
                        <#if hasSummaryContent>
                            <div class="hero-module__summary"
                                 data-uipath="website.publishedwork.summary"><@hst.html hippohtml=document.summary contentRewriter=gaContentRewriter/></div>
                        </#if>
                        <#if button != "nobutton">
                            <a class="hero-module__button" href="#document-content">
                                Jump to overview
                                <img class="hero-module__button-arrow"
                                     aria-hidden="true" alt="Down Arrow"
                                     src="<@hst.webfile path="/images/hero-module/arrow--down.svg"/>"/>
                            </a>
                        </#if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</#macro>
