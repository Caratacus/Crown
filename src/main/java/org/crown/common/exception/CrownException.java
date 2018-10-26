package org.crown.common.exception;


/**
 * <p>
 * Crown异常类
 * </p>
 *
 * @author Caratacus
 */
public class CrownException extends RuntimeException {

    private static final long serialVersionUID = 1L;


    public CrownException(String message) {
        super(message);
    }

    public CrownException(Throwable throwable) {
        super(throwable);
    }

    public CrownException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
