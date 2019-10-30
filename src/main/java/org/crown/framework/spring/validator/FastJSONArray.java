package org.crown.framework.spring.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * <p>
 * FastJson JSONArray校验注解
 * </p>
 *
 * @author Caratacus
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FastJSONArrayConstraintValidator.class)
public @interface FastJSONArray {

    /**
     * @Description: 错误提示
     */
    String message() default "请输入正确的JSONArray格式";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
