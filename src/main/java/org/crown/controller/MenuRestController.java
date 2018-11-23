/*
 * Copyright (c) 2018-2019 Caratacus, (caratacus@qq.com).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.crown.controller;

import java.util.List;

import org.crown.common.annotations.Resources;
import org.crown.common.api.model.responses.ApiResponses;
import org.crown.common.framework.controller.SuperController;
import org.crown.model.entity.Menu;
import org.crown.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author Caratacus
 * @since 2018-10-25
 */
@Api(tags = {"Menu"}, description = "菜单相关接口")
@RestController
@RequestMapping(value = "/menu", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class MenuRestController extends SuperController {

    @Autowired
    private IMenuService menuService;

    @Resources(verify = false)
    @ApiOperation(value = "查询所有菜单")
    @GetMapping
    public ApiResponses<List<Menu>> list() {
        return success(menuService.list());
    }

}

