<#ftl output_format="HTML">

<#macro stickyNavTags links affix title filter selectedFilter slugifyValue=true useKeyForQueryValue=false>
<div class="article-section-nav-wrapper">
    <div class="article-section-nav">
        <h2 class="article-section-nav__title">${title}</h2>
        <#if links??>
            <nav>
                <ol class="article-section-nav__list article-section-nav__list--tag-links">
                    <#list links as link>
                    <li>
                        <#assign hasCount = (link.count?? && link.count?has_content)?then("(" + link.count + ")", "") />
                        <#assign dataValue = useKeyForQueryValue?then(link.key, link.title)>
                        <#assign slugifiedValue = slugifyValue?then(slugify(dataValue), dataValue) />

                        <#if selectedFilter?seq_contains(link.key)>
                        <#assign linkout = "&" + filter + "=" + selectedFilter?join("&" + filter + "=") + affix />
                          <a href="${getDocumentUrl()}${linkout?replace("&${filter}="+link.key, "")?replace("&", "?", "f")}" title="Show '${link.title}' ${filter} only" class="tag-link selected" data-slugified-value="${slugifiedValue}">${link.title} ${hasCount}</a>
                        <#else>
                          <a href="?${filter}=${selectedFilter?join("&" + filter + "=", "", "&" + filter + "=")}${link.key + affix}" title="Show '${link.title}' ${filter} only" class="tag-link" data-slugified-value="${slugifiedValue}">${link.title} ${hasCount}</a>
                        </#if>
                    </li>
                </#list>
                </ol>
            </nav>
        </#if>
    </div>
</div>
</#macro>
