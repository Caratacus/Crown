package org.crown.common.exception;


import org.crown.common.api.emuns.ErrorCodeEnum;
import org.crown.common.api.model.ErrorCode;

/**
 * <p>
 * API 业务异常类
 * </p>
 *
 * @author Caratacus
 */
public class ApiException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private ErrorCode errorCode;

    public ApiException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.msg());
        this.errorCode = errorCodeEnum.convert();
    }

    public ApiException(ErrorCode errorCode) {
        super(errorCode.getError());
        this.errorCode = errorCode;

    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
