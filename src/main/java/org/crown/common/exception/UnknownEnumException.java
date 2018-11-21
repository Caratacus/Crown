package org.crown.common.exception;

/**
 * <p>
 * 未知枚举 异常
 * </p>
 *
 * @author Caratacus
 */
public class UnknownEnumException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UnknownEnumException(String message) {
        super(message);
    }

    public UnknownEnumException(Throwable throwable) {
        super(throwable);
    }

    public UnknownEnumException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
