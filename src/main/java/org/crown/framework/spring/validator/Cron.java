package org.crown.framework.spring.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * <p>
 * Cron校验注解
 * </p>
 *
 * @author Caratacus
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CronConstraintValidator.class)
public @interface Cron {

    /**
     * @Description: 错误提示
     */
    String message() default "请输入正确Cron表达式";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}