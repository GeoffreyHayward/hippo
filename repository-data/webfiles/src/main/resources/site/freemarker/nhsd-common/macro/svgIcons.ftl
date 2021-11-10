<#ftl output_format="HTML">

<#-- Return an inline SVG icon by ID --> <-- W3C recommend non empty id strings remove and test #TODO LK -->
<#macro svgIcon>
    <#assign viewBox="0 0 20 20" />
    <#if id="actionArrow">
        <#assign viewBox="0 0 24 24" />
    <#elseif id="comments" || id="weather">
        <#assign viewBox="0 0 34 30" />
    </#if>

    <svg class="${(className??)?then(className, '')}" xmlns="http://www.w3.org/2000/svg" viewBox="${viewBox}" aria-hidden="true" preserveAspectRatio="xMidYMid meet" focusable="false">
        <#if id="actionArrow">
        <path d="M0 0h24v24H0z" fill="none"></path>
        <path d="M12 2a10 10 0 0 0-9.95 9h11.64L9.74 7.05a1 1 0 0 1 1.41-1.41l5.66 5.65a1 1 0 0 1 0 1.42l-5.66 5.65a1 1 0 0 1-1.41 0 1 1 0 0 1 0-1.41L13.69 13H2.05A10 10 0 1 0 12 2z"></path>

        <#elseif id="comments">
        <path d="M5.688,4.81333333 C8.81333333,2.272 12.584,1 17,1 C21.416,1 25.1866667,2.272 28.312,4.81333333 C31.4373333,7.35466667 33,10.4186667 33,14 C33,17.584 31.4373333,20.6453333 28.312,23.1893333 C25.1866667,25.7306667 21.416,27 17,27 C14.6666667,27 12.4373333,26.6053333 10.312,25.8133333 C7.60533333,27.9386667 4.66666667,29 1.50133333,29 C1.416,29 1.33333333,28.9706667 1.25066667,28.9066667 C1.168,28.8453333 1.104,28.7706667 1.064,28.688 C0.978666667,28.48 1.01066667,28.304 1.15733333,28.1573333 C1.30133333,28.0106667 1.552,27.72 1.90666667,27.2826667 C2.26133333,26.8453333 2.73066667,26.1146667 3.312,25.0933333 C3.896,24.0746667 4.312,23.104 4.56266667,22.1893333 C2.18933333,19.8133333 1,17.0853333 1,14 C1,10.4186667 2.56266667,7.35466667 5.688,4.81333333"></path>

        <#elseif id="weather">
        <path d="M31.875,21.25 C31.875,24.7658125 29.0158125,27.625 25.5,27.625 L8.5,27.625 C4.9841875,27.625 2.125,24.7658125 2.125,21.25 C2.125,17.7341875 4.9841875,14.875 8.5,14.875 C8.769875,14.875 9.061,14.898375 9.415875,14.949375 C9.726125,14.9950625 10.027875,14.860125 10.2605625,14.65825 C10.49325,14.456375 10.625,14.120625 10.625,13.8125 C10.625,10.2966875 13.4841875,7.4375 17,7.4375 C20.5158125,7.4375 23.375,10.2966875 23.3760625,13.776375 C23.3739375,13.801875 23.37075,13.8709375 23.37075,13.8975 C23.37075,14.205625 23.504625,14.4978125 23.7373125,14.6996875 C23.97,14.9015625 24.2770625,14.9929375 24.584125,14.9483125 C24.939,14.898375 25.230125,14.875 25.5,14.875 C29.0158125,14.875 31.875,17.7341875 31.875,21.25 L31.875,21.25 Z M24.4375,5.3125 C26.781375,5.3125 28.6875,7.218625 28.6875,9.5625 C28.6875,10.88 28.0893125,12.087 27.0544375,12.89875 C26.54975,12.80525 26.03125,12.75 25.5,12.75 L25.434125,12.75 C25.106875,10.100125 23.59175,7.864625 21.447625,6.555625 C22.2306875,5.7789375 23.2931875,5.3125 24.4375,5.3125 L24.4375,5.3125 Z M34,10.625 L34,8.5 L30.716875,8.5 C30.55325,7.5341875 30.175,6.6416875 29.6278125,5.8745625 L32.6262539,2.8761875 L31.1238125,1.3738125 L28.1254375,4.3721875 C27.3583125,3.825 26.4658125,3.44675 25.5,3.283125 L25.5,0 L23.375,0 L23.375,3.2841875 C21.7971875,3.550875 20.389375,4.4040625 19.414,5.658875 C18.6479375,5.4346875 17.8383125,5.3125 17,5.3125 C12.6958125,5.3125 9.1279375,8.5286875 8.5775625,12.75 C3.835625,12.6639375 0,16.53675 0,21.25 C0,25.9366875 3.8133125,29.75 8.5,29.75 L25.5,29.75 C30.1866875,29.75 34,25.9366875 34,21.25 C34,17.937125 32.0906875,15.0673125 29.3175625,13.665875 C30.05175,12.7978125 30.52775,11.7523125 30.71475,10.625 L34,10.625 Z" fill="#FFFFFF"></path>
        <#else>
            [Error - no svg Icon found]
        </#if>
    </svg>
</#macro>
