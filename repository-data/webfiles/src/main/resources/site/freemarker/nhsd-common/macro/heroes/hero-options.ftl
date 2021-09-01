<#ftl output_format="HTML">

<#function getHeroOptions document>
    <#assign options = {
        "title": document.title
    }>

    <#assign content="">
    <#if document.summary?has_content>
        <#assign content = document.summary/>
    <#elseif document.shortsummary?has_content>
        <#assign content = document.shortsummary/>
    <#elseif document.content?has_content>
        <#assign content = document.content/>
    </#if>
    <#assign options += { "summary": content }/>

    <#if document.image?has_content || document.leadImage?has_content || document.bannerImage?has_content && document.bannerImage.pageHeaderHeroModule?has_content>
        <#assign alt = ""/>
        <#if document.leadImageAltText?has_content>
            <#assign alt = document.leadImageAltText/>
        <#elseif document.altText?has_content>
            <#assign alt = document.altText/>
        <#elseif document.image?has_content && document.image.description?has_content>
            <#assign alt = document.image.description/>
        <#elseif document.bannerImageAltText?has_content>
            <#assign alt = document.bannerImageAltText/>
        </#if>

        <#if document.image?has_content>
            <@hst.link hippobean=document.image.original fullyQualified=true var="imageSrc" />
            <#assign image = {
                "src": imageSrc,
                "alt": alt
            }/>
        <#elseif document.leadImage?has_content>
            <@hst.link hippobean=document.leadImage.original fullyQualified=true var="imageSrc" />
            <#assign image = {
                "src": imageSrc,
                "alt": alt
            }/>
        <#elseif document.bannerImage?has_content && document.bannerImage.pageHeaderHeroModule?has_content>
            <@hst.link hippobean=document.bannerImage.pageHeaderHeroModule fullyQualified=true var="imageSrc" />
            <#assign image = {
                "src": imageSrc,
                "alt": alt
            }/>
        </#if>

        <#assign options += { "image": image }/>
    </#if>

    <#return options/>
</#function>

<#function getHeroOptionsWithMetaData document>
    <#assign options = getHeroOptions(document) />

    <#assign metadata = [] />

    <#assign datePublishedLabel = "Date Published" />
    <#if document.publishedDate?has_content>
        <@fmt.formatDate value=document.publishedDate.time type="Date" pattern="d MMMM yyyy" timeZone="${getTimeZone()}" var="date" />
        <#assign metadata += [{
            "title": datePublishedLabel,
            "value": date
        }]/>
    <#elseif document.publicationDate?has_content>
        <@fmt.formatDate value=document.publicationDate.time type="Date" pattern="d MMMM yyyy" timeZone="${getTimeZone()}" var="date" />
        <#assign metadata += [{
            "title": datePublishedLabel,
            "value": date
        }]/>
    </#if>

    <#assign options += { "metaData": metadata }/>
    <#return options/>
</#function>