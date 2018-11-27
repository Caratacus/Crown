/*
 * Copyright (c) 2018-2022 Caratacus, (caratacus@qq.com).
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

import java.util.Objects;

import org.crown.common.annotations.Resources;
import org.crown.common.api.model.responses.ApiResponses;
import org.crown.common.framework.controller.SuperController;
import org.crown.common.spring.ScanMappings;
import org.crown.model.entity.Resource;
import org.crown.service.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 资源表 前端控制器
 * </p>
 *
 * @author Caratacus
 * @since 2018-10-25
 */
@Api(tags = {"Resource"}, description = "资源操作相关接口")
@RestController
@RequestMapping(value = "/resource", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class ResourceRestController extends SuperController {

    @Autowired
    private IResourceService resourceService;

    @Autowired
    private ScanMappings scanMappings;

    @Resources(verify = false)
    @ApiOperation(value = "查询所有资源(分页)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "resourceName", value = "需要查询的资源名", paramType = "query"),
            @ApiImplicitParam(name = "method", value = "需要查询的请求方式", paramType = "query"),
            @ApiImplicitParam(name = "verify", value = "是否需要验证", paramType = "query"),
    })
    @GetMapping
    public ApiResponses<IPage<Resource>> page(@RequestParam(value = "resourceName", required = false) String resourceName,
                                              @RequestParam(value = "method", required = false) String method,
                                              @RequestParam(value = "verify", required = false) Boolean verify
    ) {
        IPage<Resource> page = resourceService.page(this.<Resource>getPage(), Wrappers.<Resource>lambdaQuery().like(StringUtils.isNotEmpty(resourceName), Resource::getResourceName, resourceName).eq(StringUtils.isNotEmpty(method), Resource::getMethod, method).eq(Objects.nonNull(verify), Resource::getVerify, verify));
        return success(page);
    }

    @Resources(verify = false)
    @ApiOperation(value = "刷新资源")
    @PutMapping("/refresh")
    public ApiResponses<Void> refresh() {
        scanMappings.doScan();
        return empty();
    }


}

