package org.crown.common.framework.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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


}
