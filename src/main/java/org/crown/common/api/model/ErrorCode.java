package org.crown.common.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * 业务异常类
 *
 * @author Caratacus
 */
@Getter
@ToString
@Builder
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class ErrorCode {

    private String error;
    private int httpCode;
    private boolean show;
    private String msg;

}
