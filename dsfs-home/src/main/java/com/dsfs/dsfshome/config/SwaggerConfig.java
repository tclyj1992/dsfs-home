package com.dsfs.dsfshome.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author lyj
 * @date 2019/10/12
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket buildDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInf())    //.apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.dsfs.dsfshome.controller"))//要注释的接口名
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo buildApiInf(){
        return new ApiInfoBuilder()
                .title("鼎盛防水接口文档")
                .description("详情")
                .termsOfServiceUrl("")
                .contact(new Contact("lyj", "", ""))
                .version("1.0")
                .build();

    }
}
