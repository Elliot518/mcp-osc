package com.mcp.osc.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


/**
 * @author: KG
 * @description:
 * @date: Created in 10:03 下午 2022/9/21
 * @modified by:
 */
@Configuration
public abstract class AbstractSwaggerConfig {

    /**
     * Swagger Api Info
     * @return
     */
    protected abstract SwaggerApiInfo getApiInfo();

    @Bean
    public Docket createRestApi() {
        SwaggerApiInfo swaggerApiInfo = getApiInfo();

        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo(swaggerApiInfo))
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerApiInfo.getBasePackage()))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(SwaggerApiInfo swaggerApiInfo) {
        return new ApiInfoBuilder()
                .title(swaggerApiInfo.getTitle())
                .description(swaggerApiInfo.getDescription())
                .contact(new Contact(swaggerApiInfo.getAuthor(), swaggerApiInfo.getServiceUrl(), swaggerApiInfo.getEmail()))
                .version(swaggerApiInfo.getVersion())
                .build();
    }
}


