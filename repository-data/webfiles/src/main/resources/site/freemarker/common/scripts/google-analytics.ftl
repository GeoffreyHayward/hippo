<#ftl output_format="HTML">

<!-- Global site tag (gtag.js) - Google Analytics -->
<script async src="https://www.googletagmanager.com/gtag/js?id=UA-76954916-2"></script>
<script>
    window.dataLayer = window.dataLayer || [];
    function gtag(){dataLayer.push(arguments);}
    gtag('js', new Date());
    gtag('config', 'UA-76954916-2', { 'anonymize_ip': true });

    function logGoogleAnalyticsEvent(action,category,label) {
        gtag('event', action, {
                'event_category': category,
                'event_label': label
            });
    }
</script>