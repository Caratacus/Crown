package org.crown.common.spring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.crown.common.annotations.Resources;
import org.crown.model.entity.Resource;
import org.crown.service.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import io.swagger.annotations.ApiOperation;
import liquibase.util.MD5Util;

/**
 * <p>
 * 服务mapping扫描
 * </p>
 *
 * @author Caratacus
 * @since 2017-10-19
 */
@Service
public class ScanMappings {

    @Autowired
    private IResourceService resourceService;

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    public void doScan() {
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();
        List<Resource> resources = handlerMethods.values().stream().map(this::getResources).flatMap(Collection::stream).collect(Collectors.toList());
        resourceService.saveOrUpdateBatch(resources);
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
        String[] requestMappings = Objects.nonNull(requestMappingAnnotation) ? requestMappingAnnotation.value() : new String[]{""};
        String[] methodMappings = Objects.nonNull(methodMappingAnnotation) ? methodMappingAnnotation.path() : new String[]{""};
        RequestMethod[] method = Objects.nonNull(methodMappingAnnotation) ? methodMappingAnnotation.method() : new RequestMethod[0];
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
                resource.setId(MD5Util.computeMD5(requestMethod.name() + mapping));
                resource.setResourceName(Objects.nonNull(apiOperation) ? apiOperation.value() : "未命名资源路径");
                resource.setMapping(mapping);
                resource.setMethod(requestMethod.name());
                resource.setVerify(res.verify());
                resources.add(resource);
            }
        }
        return resources;
    }

}