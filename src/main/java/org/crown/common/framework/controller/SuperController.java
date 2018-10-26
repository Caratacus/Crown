package org.crown.common.framework.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.crown.common.api.model.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;


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
     * 获取一个新的RestResult
     *
     * @return
     */
    public static <T> ApiResponses<T> success(T object) {
        return ApiResponses.<T>success(object);
    }

    /**
     * 获取一个新的RestResult
     *
     * @return
     */
    public static ApiResponses<Void> empty() {
        return ApiResponses.empty();
    }

}
