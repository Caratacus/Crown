package org.crown.common.api.emuns;

import javax.servlet.http.HttpServletResponse;

import org.crown.common.exception.UnknownEnumException;


/**
 * 业务异常枚举
 *
 * @author Caratacus
 */
public enum ErrorCode {

    /**
     * 未授权的服务
     */
    FOR_EXAMPLE(HttpServletResponse.SC_FORBIDDEN, true, "FOR_EXAMPLE");

    private final int httpCode;
    private final boolean show;
    private final String msg;

    ErrorCode(int httpCode, boolean show, String msg) {
        this.httpCode = httpCode;
        this.msg = msg;
        this.show = show;
    }

    public static ErrorCode getErrorCode(String errorCode) {
        ErrorCode[] enums = ErrorCode.values();
        for (ErrorCode errorCodeEnum : enums) {
            if (errorCodeEnum.name().equalsIgnoreCase(errorCode)) {
                return errorCodeEnum;
            }
        }
        throw new UnknownEnumException("Error: Unknown errorCode, or do not support changing errorCode!\n");
    }

    public int httpCode() {
        return this.httpCode;
    }

    public String msg() {
        return this.msg;
    }

    public boolean isShow() {
        return this.show;
    }
}
