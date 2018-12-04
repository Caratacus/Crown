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
package org.crown.service.impl;

import java.util.List;

import org.crown.common.utils.TypeUtils;
import org.crown.emuns.AuthTypeEnum;
import org.crown.framework.service.impl.BaseServiceImpl;
import org.crown.mapper.ResourceMapper;
import org.crown.model.dto.ResourcePermDTO;
import org.crown.model.entity.Resource;
import org.crown.service.IResourceService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 * @author Caratacus
 * @since 2018-10-25
 */
@Service
public class ResourceServiceImpl extends BaseServiceImpl<ResourceMapper, Resource> implements IResourceService {


    @Override
    public List<String> getUserPerms(Integer uid) {
        //TODO 目前是查询所有权限 后期需要更改
        return listObjs(Wrappers.<Resource>lambdaQuery().select(Resource::getPerm), TypeUtils::castToString);
    }

    @Override
    public List<ResourcePermDTO> getUserResourcePerms(Integer uid) {
        //List<ResourcePermDTO> perms = getPerms(AuthTypeEnum.LOGIN, AuthTypeEnum.LOGIN);
        List<ResourcePermDTO> userPerms = entitys(Wrappers.<Resource>lambdaQuery().select(Resource::getMethod, Resource::getMapping), e -> e.convert(ResourcePermDTO.class));
       // perms.addAll(userPerms);
        return userPerms;
    }

    @Override
    public List<ResourcePermDTO> getOpenPerms() {
        return getPerms(AuthTypeEnum.OPEN);
    }

    @Override
    public List<ResourcePermDTO> getLoginPerms() {
        return getPerms(AuthTypeEnum.LOGIN);
    }

    @Override
    public List<ResourcePermDTO> getPerms(AuthTypeEnum... authTypes) {
        return entitys(Wrappers.<Resource>lambdaQuery().select(Resource::getMethod, Resource::getMapping).in(Resource::getAuthType, authTypes), e -> e.convert(ResourcePermDTO.class));
    }
}
