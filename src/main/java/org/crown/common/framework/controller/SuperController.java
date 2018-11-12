package org.crown.common.framework.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.crown.common.api.ApiAssert;
import org.crown.common.api.model.responses.ApiResponses;
import org.crown.common.emuns.ErrorCodeEnum;
import org.crown.common.kit.JWTTokenUtils;
import org.crown.common.kit.TypeUtils;
import org.crown.cons.PageCons;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

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
        token = token.replaceFirst("Bearer ", "");
        Claims claims = JWTTokenUtils.getClaim(token);
        ApiAssert.notNull(ErrorCodeEnum.UNAUTHORIZED, claims);
        return claims.get(JWTTokenUtils._ID, Integer.class);
    }

    /**
     * 获取分页对象
     *
     * @return
     */
    protected <T> Page<T> getPage() {
        return getPage(PageCons.DEFAULT_LIMIT);
    }

    /**
     * 获取分页对象
     *
     * @param size
     * @return
     */
    protected <T> Page<T> getPage(int size) {
        int index = 1;
        // 页数
        Integer cursor = TypeUtils.castToInt(request.getParameter(PageCons.PAGE_PAGE), index);
        // 分页大小
        Integer limit = TypeUtils.castToInt(request.getParameter(PageCons.PAGE_ROWS), size);
        // 是否查询分页
        Boolean searchCount = TypeUtils.castToBoolean(request.getParameter(PageCons.SEARCH_COUNT), false);
        limit = limit > PageCons.MAX_LIMIT ? PageCons.MAX_LIMIT : limit;
        Page<T> page = new Page<>(cursor, limit);
        page.setAsc(request.getParameterValues(PageCons.PAGE_ASCS));
        page.setDesc(request.getParameterValues(PageCons.PAGE_DESCS));
        return page;
    }
}
