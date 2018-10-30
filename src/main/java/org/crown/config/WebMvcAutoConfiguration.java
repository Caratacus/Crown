package org.crown.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.crown.common.http.log.aspect.LogRecordAspect;
import org.crown.common.spring.CrownHandlerExceptionResolver;
import org.crown.common.spring.interceptor.GlobalInterceptor;
import org.crown.common.spring.validator.ValidatorCollectionImpl;
import org.crown.common.undertow.UndertowServerFactoryCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

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

    @Override
    public Validator getValidator() {
        return new SpringValidatorAdapter(new ValidatorCollectionImpl());
    }


    @Bean
    @ConditionalOnClass(Undertow.class)
    public UndertowServerFactoryCustomizer undertowServerFactoryCustomizer() {
        return new UndertowServerFactoryCustomizer();
    }

    @Bean
    @ConditionalOnMissingBean(RequestContextListener.class)
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.forEach(e -> {
            if (e instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter converter = (MappingJackson2HttpMessageConverter) e;
                SimpleModule simpleModule = new SimpleModule();
                simpleModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                simpleModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                simpleModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
                simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
                converter.getObjectMapper().registerModule(simpleModule);
            }
        });
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(new CrownHandlerExceptionResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /**
         * 放行Swagger
         * @see springfox.documentation.swagger.web.ApiResourceController
         * @see springfox.documentation.swagger2.web.Swagger2Controller
         */
        registry.addInterceptor(new GlobalInterceptor()).addPathPatterns("/**").excludePathPatterns("/error", "/swagger-resources", "/swagger-resources/configuration/security", "/swagger-resources/configuration/ui", "/v2/api-docs");
    }

}
