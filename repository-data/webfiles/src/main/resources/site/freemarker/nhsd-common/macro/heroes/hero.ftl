<#ftl output_format="HTML">

<#include "./base-hero.ftl">
<#include "full-hero-content.ftl">
<#include "./simple-hero-content.ftl">

<#macro hero options heroType = "default">
    <#assign validatedType = heroType/>
    <#if !['accentedImage', 'accentedImageMirrored', 'image', 'backgroundImage']?seq_contains(validatedType)>
        <#assign validatedType = "default"/>
    </#if>

    <@baseHero options validatedType>
        <#if heroType == "backgroundImage">
            <@simpleHeroContent options>
                <#nested />
            </@simpleHeroContent>
        <#else>
            <@fullHeroContent options>
                <#nested />
            </@fullHeroContent>
        </#if>
    </@baseHero>
</#macro>
