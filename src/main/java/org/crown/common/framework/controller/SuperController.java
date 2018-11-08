package org.crown.common.framework.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.crown.common.api.ApiAssert;
import org.crown.common.api.model.responses.ApiResponses;
import org.crown.common.emuns.ErrorCodeEnum;
import org.crown.common.kit.JWTTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;

import io.jsonwebtoken.Claims;


/**
 * SuperController
 *
 * @author Caratacus
 */
public class SuperController {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;


    /**
     * 成功的ApiResponses
     *
     * @return
     */
    public static <T> ApiResponses<T> success(T object) {
        return ApiResponses.<T>success(object);
    }

    /**
     * 空的ApiResponses
     *
     * @return
     */
    public static ApiResponses<Void> empty() {
        return ApiResponses.empty();
    }

    /**
     * 获取当前用户id
     */
    public Integer currentUid() {
        String token = request.getHeader("Authorization");
        ApiAssert.notNull(ErrorCodeEnum.UNAUTHORIZED, token);
        token = token.replaceFirst("Basic ", "");
        Claims claims = JWTTokenUtils.getClaim(token);
        ApiAssert.notNull(ErrorCodeEnum.UNAUTHORIZED, claims);
        return claims.get(JWTTokenUtils._ID, Integer.class);
    }
}
