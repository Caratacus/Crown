package org.crown.common.exception;


import org.apache.commons.lang3.StringUtils;
import org.crown.common.api.emuns.ErrorCode;

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

    public ApiException(ErrorCode errorCode, String msg) {

        super(StringUtils.isNotBlank(msg) ? msg : errorCode.msg());
        this.errorCode = errorCode;
    }

    public ApiException(ErrorCode errorCode) {
        this(errorCode, null);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

}
