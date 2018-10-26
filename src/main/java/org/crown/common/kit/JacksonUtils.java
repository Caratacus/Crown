package org.crown.common.kit;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import org.crown.common.exception.CrownException;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Jackson 工具类
 *
 * @author Caratacus
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class JacksonUtils {

    private final static ObjectMapper objectMapper;

    static {
        objectMapper = initWrapperObjectMapper(new ObjectMapper());
    }

    /**
     * 转换Json
     *
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        if (isCharSequence(object)) {
            return (String) object;
        }
        try {
            return getObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new CrownException(e);
        }
    }

    /**
     * 获取ObjectMapper
     *
     * @return
     */
    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    /**
     * 初始化 Wrapper ObjectMapper
     *
     * @param objectMapper
     * @return
     */
    public static ObjectMapper initWrapperObjectMapper(ObjectMapper objectMapper) {
        if (Objects.isNull(objectMapper)) {
            objectMapper = new ObjectMapper();
        }
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        //不显示为null的字段
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 忽略不能转移的字符
        objectMapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
        // 过滤对象的null属性.
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 过滤map中的null值
        objectMapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        simpleModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        simpleModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        return objectMapper;
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
