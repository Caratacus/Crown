package org.crown.framework.spring.validator;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.alibaba.fastjson.JSONObject;

/**
 * <p>
 * FastJson JSONObject校验
 * </p>
 *
 * @author Caratacus
 */
public class FastJSONObjectConstraintValidator implements ConstraintValidator<FastJSONObject, JSONObject> {

    @Override
    public void initialize(FastJSONObject jsonObject) {
    }

    /**
     * @Description: 自定义校验逻辑
     */
    @Override
    public boolean isValid(JSONObject jsonObject, ConstraintValidatorContext constraintValidatorContext) {
        return Objects.nonNull(jsonObject);
    }
}
