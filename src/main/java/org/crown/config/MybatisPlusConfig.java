package org.crown.config;

import org.crown.common.mybatisplus.CommonMetaObjectHandler;
import org.crown.common.mybatisplus.MybatisPlusSqlInjector;
import org.crown.common.mybatisplus.SqlPerformanceInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;


/**
 * mybatis plus 配置
 *
 * @author Caratacus
 */
@Configuration
public class MybatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean
    public SqlPerformanceInterceptor sqlPerformanceInterceptor() {
        return new SqlPerformanceInterceptor();
    }

    @Bean
    public CommonMetaObjectHandler commonMetaObjectHandler() {
        return new CommonMetaObjectHandler();
    }

    @Bean
    public MybatisPlusSqlInjector mybatisPlusSqlInjector() {
        return new MybatisPlusSqlInjector();
    }
}
