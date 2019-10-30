package org.crown.framework.spring.validator;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.alibaba.fastjson.JSONArray;

/**
 * <p>
 * FastJson JSONArray校验
 * </p>
 *
 * @author Caratacus
 */
public class FastJSONArrayConstraintValidator implements ConstraintValidator<FastJSONArray, JSONArray> {

    @Override
    public void initialize(FastJSONArray json) {
    }

    /**
     * @Description: 自定义校验逻辑
     */
    @Override
    public boolean isValid(JSONArray jsonArray, ConstraintValidatorContext constraintValidatorContext) {
        return Objects.nonNull(jsonArray);
    }
}