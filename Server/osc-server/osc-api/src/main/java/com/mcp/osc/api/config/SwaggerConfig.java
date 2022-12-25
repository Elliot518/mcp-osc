package com.mcp.osc.api.config;

import com.mcp.osc.infrastructure.config.AbstractSwaggerConfig;
import com.mcp.osc.infrastructure.config.SwaggerApiInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author: KG
 * @description:
 * @date: Created in 5:16 下午 2022/9/21
 * @modified by:
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends AbstractSwaggerConfig {
    @Override
    protected SwaggerApiInfo getApiInfo() {
        SwaggerApiInfo swaggerApiInfo = new SwaggerApiInfo();
        swaggerApiInfo.setBasePackage("com.mcp.osc.api.controller");
        swaggerApiInfo.setTitle("osc-api API");
        swaggerApiInfo.setDescription("swagger-ui API for osc-api");
        swaggerApiInfo.setServiceUrl("http://www.microcomponent.net");
        swaggerApiInfo.setVersion("1.0");
        //swaggerApiInfo.setTokenHead(SwaggerConstant.TOKEN_HEAD);
        return swaggerApiInfo;
    }

    @Bean
    @Override
    public Docket createRestApi() {
        return super.createRestApi();
    }
}


