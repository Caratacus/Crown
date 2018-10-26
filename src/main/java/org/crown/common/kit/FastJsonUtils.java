package org.crown.common.kit;

import java.util.Objects;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * FastJsonUtils
 *
 * @author Caratacus
 * @date 2017/07/13
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class FastJsonUtils {

    /**
     * 转换Json
     *
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        return isCharSequence(object) ? (String) object : JSON.toJSONString(object);
    }

    /**
     * <p>
     * 是否为CharSequence类型
     * </p>
     *
     * @param object
     * @return
     */
    public static Boolean isCharSequence(Object object) {
        return !Objects.isNull(object) && StringUtils.isCharSequence(object.getClass());
    }

    /**
     * Json转换为对象 转换失败返回null
     *
     * @param json
     * @return
     */
    public static Object parse(String json) {
        Object object = null;
        try {
            object = JSON.parse(json);
        } catch (Exception ignored) {
        }
        return object;
    }

}
