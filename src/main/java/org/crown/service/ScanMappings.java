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
package org.crown.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.crown.common.annotations.Resources;
import org.crown.model.entity.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;

import io.swagger.annotations.ApiOperation;
import liquibase.util.MD5Util;

/**
 * <p>
 * 服务mapping扫描
 * </p>
 *
 * @author Caratacus
 */
@Service
public class ScanMappings {

    @Autowired
    private IResourceService resourceService;

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    private String[] emptyArray = new String[]{""};

    /**
     * 扫描资源插入数据库
     */
    @PostConstruct
    public void doScan() {
        resourceService.saveOrUpdateBatch(
                handlerMapping.getHandlerMethods()
                        .values()
                        .stream()
                        .map(this::getResources)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList())
        );
    }

    /**
     * 获取Resource
     *
     * @param handlerMethod
     * @return
     */
    public List<Resource> getResources(HandlerMethod handlerMethod) {
        Resources res = handlerMethod.getMethodAnnotation(Resources.class);
        if (Objects.isNull(res)) {
            return Collections.emptyList();
        }
        RequestMapping requestMappingAnnotation = handlerMethod.getBeanType().getAnnotation(RequestMapping.class);
        RequestMapping methodMappingAnnotation = handlerMethod.getMethodAnnotation(RequestMapping.class);
        if (Objects.isNull(requestMappingAnnotation) && Objects.isNull(methodMappingAnnotation)) {
            return Collections.emptyList();
        }
        ApiOperation apiOperation = handlerMethod.getMethodAnnotation(ApiOperation.class);
        String[] requestMappings = Objects.nonNull(requestMappingAnnotation) ? requestMappingAnnotation.value() : emptyArray;
        String[] methodMappings = Objects.nonNull(methodMappingAnnotation) ? methodMappingAnnotation.path() : emptyArray;
        RequestMethod[] method = Objects.nonNull(methodMappingAnnotation) ? methodMappingAnnotation.method() : new RequestMethod[0];
        requestMappings = ArrayUtils.isEmpty(requestMappings) ? emptyArray : requestMappings;
        methodMappings = ArrayUtils.isEmpty(methodMappings) ? emptyArray : methodMappings;
        Set<String> mappings = new HashSet<>(1);
        for (String reqMapping : requestMappings) {
            for (String methodMapping : methodMappings) {
                mappings.add(reqMapping + methodMapping);
            }
        }
        List<Resource> resources = new ArrayList<>(1);
        for (RequestMethod requestMethod : method) {
            for (String mapping : mappings) {
                //接口描述
                Resource resource = new Resource();
                resource.setResourceName(Objects.nonNull(apiOperation) ? apiOperation.value() : "未命名资源路径");
                resource.setMapping(mapping);
                resource.setMethod(requestMethod.name());
                resource.setAuthType(res.auth());
                resource.setPerm(resourceService.getResourcePermTag(requestMethod.name(), mapping));
                resource.setId(MD5Util.computeMD5(resource.getPerm()));
                resources.add(resource);
            }
        }
        return resources;
    }

}
