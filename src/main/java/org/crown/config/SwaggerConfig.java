package org.crown.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <p>
 * RESTful 服务 API 管理框架 Swagger 配置初始化
 * </p>
 *
 * @author Caratacus
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any()).build().useDefaultResponseMessages(false);
    }

    /**
     * 获取swagger ApiInfo
     *
     * @return
     */
    private static ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Crown API")
                .description("Crown Swagger API 文档")
                .termsOfServiceUrl("https://github.com/Caratacus/Crown")
                .version("1.0")
                .contact(new Contact("Crown", "https://github.com/Caratacus", "caratacus@qq.cn"))
                .build();
    }


}
