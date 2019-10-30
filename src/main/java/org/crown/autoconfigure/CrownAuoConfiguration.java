package org.crown.autoconfigure;

import javax.servlet.DispatcherType;

import org.crown.common.filter.GlobalFilter;
import org.crown.framework.springboot.properties.CrownProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>
 * Crown项目SpringBoot相关配置
 * </p>
 *
 * @author Caratacus
 * @since 2017-09-21
 */
@Configuration
@EnableConfigurationProperties({CrownProperties.class})
public class CrownAuoConfiguration implements WebMvcConfigurer {

    private final CrownProperties properties;

    public CrownAuoConfiguration(CrownProperties properties) {
        this.properties = properties;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /** 文件上传路径 */
        registry.addResourceHandler(properties.getPath().getResourceHandler()).addResourceLocations("file:" + properties.getPath().getFilePath());

        /** swagger配置 */
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public FilterRegistrationBean globalFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new GlobalFilter());
        registration.addUrlPatterns("/*");
        registration.setName("globalFilter");
        registration.setOrder(Integer.MAX_VALUE);
        return registration;
    }
}