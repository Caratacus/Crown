package org.crown.common.api.model.responses;


import java.io.Serializable;
import java.time.LocalDateTime;

import org.crown.common.api.ApiUtils;
import org.crown.common.api.model.ErrorCode;
import org.springframework.http.HttpStatus;


/**
 * 接口返回
 *
 * @author Caratacus
 */
public class ApiResponses<T> implements Serializable {

    /**
     * 不需要返回结果
     */
    public static ApiResponses<Void> empty() {
        return SuccessResponses.<Void>builder().status(HttpStatus.OK.value()).build();

    }

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
        return FailureResponses.builder()
                .error(errorCode.getError())
                .msg(errorCode.getMsg())
                .exception(ApiUtils.exceptionMsg(exception))
                .show(errorCode.isShow())
                .time(LocalDateTime.now())
                .status(errorCode.getHttpCode())
                .build();
    }

}
