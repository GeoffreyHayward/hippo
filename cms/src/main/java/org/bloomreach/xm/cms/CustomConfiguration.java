package org.bloomreach.xm.cms;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({"classpath*:META-INF/hst-assembly/addon/crisp/overrides/custom-resource-resolvers.xml"})
public class CustomConfiguration {
}
