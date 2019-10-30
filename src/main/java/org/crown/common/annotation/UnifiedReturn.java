package org.crown.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.http.HttpStatus;

/**
 * 包装API返回注解
 *
 * @author Crown
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UnifiedReturn {

    /**
     * 是否包装返回
     */
    boolean wrapper() default false;

    /**
     * 正常返回httpcode码
     */
    HttpStatus status() default HttpStatus.OK;
}