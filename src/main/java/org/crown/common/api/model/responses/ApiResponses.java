package org.crown.common.api.model.responses;


import java.io.Serializable;
import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.crown.common.api.emuns.ErrorCode;
import org.springframework.http.HttpStatus;


/**
 * 接口返回
 *
 * @author Caratacus
 */
public class ApiResponses<T> implements Serializable {

    /**
     * 成功返回
     *
     * @param object
     */
    public static <T> ApiResponses<T> success(T object) {
        return SuccessResponses.<T>builder().status(HttpStatus.OK.value()).result(object).build();

    }

    /**
     * 失败返回
     *
     * @param errorCode
     * @param exception
     */
    public static <T> ApiResponses<T> failure(ErrorCode errorCode, Exception exception) {
        return failure(errorCode, exception, null);
    }

    /**
     * 失败返回
     *
     * @param errorCode
     * @param exception
     * @param msg
     */
    public static <T> ApiResponses<T> failure(ErrorCode errorCode, Exception exception, String msg) {
        return FailureResponses.builder()
                .error(errorCode.toString())
                .msg(StringUtils.isNotBlank(msg) ? msg : errorCode.msg())
                .exception(exception.toString())
                .show(errorCode.isShow())
                .time(LocalDateTime.now())
                .status(errorCode.httpCode())
                .build();
    }

}
