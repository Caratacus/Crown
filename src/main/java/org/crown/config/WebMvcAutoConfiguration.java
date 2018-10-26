package org.crown.config;

import java.util.List;

import javax.validation.Validator;

import org.crown.common.http.log.aspect.LogRecordAspect;
import org.crown.common.kit.JacksonUtils;
import org.crown.common.spring.validator.SpringHibernateValidatorAdapter;
import org.crown.common.undertow.UndertowServerFactoryCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import io.undertow.Undertow;


/**
 * <p>
 * Service 配置
 * </p>
 *
 * @author Caratacus
 * @since 2018-04-06
 */
@Configuration
public class WebMvcAutoConfiguration implements WebMvcConfigurer {

    @Bean
    public LogRecordAspect logRecordAspect() {
        return new LogRecordAspect();
    }

    @Bean
    public Validator springHibernateValidatorAdapter() {
        return new SpringHibernateValidatorAdapter();
    }

    @Bean
    @ConditionalOnClass(Undertow.class)
    public UndertowServerFactoryCustomizer undertowServerFactoryCustomizer() {
        return new UndertowServerFactoryCustomizer();
    }

    /**
     * <p>
     * 请求监听
     * </p>
     */
    @Bean
    @ConditionalOnMissingBean(RequestContextListener.class)
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

    /**
     * <p>
     * 消息转换
     * </p>
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.removeIf(e -> e instanceof MappingJackson2HttpMessageConverter);
        MappingJackson2HttpMessageConverter httpMessageConverter = new MappingJackson2HttpMessageConverter();
        httpMessageConverter.setObjectMapper(JacksonUtils.getObjectMapper());
        converters.add(httpMessageConverter);
    }

    /**
     * {@inheritDoc}
     * <p>This implementation is empty.
     */
    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.removeIf(resolver -> resolver instanceof DefaultHandlerExceptionResolver);
        //TODO
        // exceptionResolvers.add(new ServerHandlerExceptionResolver());
    }

    /**
     * 配置拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /**
         * 放行Swagger
         * @see springfox.documentation.swagger.web.ApiResourceController
         * @see springfox.documentation.swagger2.web.Swagger2Controller
         */
        //TODO
        //   registry.addInterceptor(new ApiInterceptor()).addPathPatterns("/**").excludePathPatterns("/error", "/swagger-resources", "/swagger-resources/configuration/security", "/swagger-resources/configuration/ui", "/v2/api-docs");
    }

}
