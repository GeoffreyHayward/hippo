<#ftl output_format="HTML">
<#include "../include/imports.ftl">
<@hst.setBundle basename="nationalindicatorlibrary.headers"/>

<section id="title" class="document-content">
    <#if document??>
        <h1 class="NIL-title" data-uipath="ps.document.title">${document.title}</h1>
        <p style="width:70%;">${document.summary}</p>      
    </#if>
</section>

<section id="popular-topics" class="document-content">
    <h2><@fmt.message key="headers.popularTopics"/></h2>
    <div class="nihubSection">
        <div class="document-content columns" style="text-align:center">
            <#list document.popularTopicLinks as link>
                <div class="layout__item">
                    <h3 class="popular-topic-link"><a href="${link.linkUrl}" target="_blank" >${link.linkText}</a></h3>
                </div>
            </#list>
        </div>
    </div>
</section>

<section id="getting-assured" class="document-content">
    <h2><@fmt.message key="headers.indicatorsAndAssurance"/></h2>
    <div class="nihubSection"><br>
        <div class="nihubIgbSection">
            <h1 class="here-to-help"><@fmt.message key="headers.hereToHelp"/></h1><br>
            <div class="columns">
                <div class="advice igb-link">
                    <a href="${document.advice.pageLink}" style="text-decoration:none;"><h3 class="advice-title">${document.advice.title}</h3>
                    <p class="advice-content">${document.advice.text}</p></a>
                </div>
                <div class="add-your-indicator igb-link">
                    <a href="${document.addYourIndicator.pageLink}" style="text-decoration:none;"><h3 class="add-your-indicator-title">${document.addYourIndicator.title}</h3>
                    <p class="add-your-indicator-content">${document.addYourIndicator.text}</p></a>
                </div>
                <div class="apply-for-assurance igb-link">
                    <a href="${document.applyForAssurance.pageLink}" style="text-decoration:none;"><h3 class="apply-for-assurance-title">${document.applyForAssurance.title}</h3>
                    <p class="apply-for-assurance-content">${document.applyForAssurance.text}</p></a>
                </div>
            </div>
            <@fmt.message key="headers.findOutMore" var="findOut"/>
            <h2 class="find-out-more"><a href="<@hst.link siteMapItemRefId='nilHelp' />" title="${findOut}">${findOut}</a></h2>
        </div>
    </div>
</section>
