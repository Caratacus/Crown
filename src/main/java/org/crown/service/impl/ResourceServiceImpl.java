package org.crown.service.impl;

import java.util.List;

import org.crown.common.framework.service.impl.BaseServiceImpl;
import org.crown.common.kit.BeanConverter;
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
    public List<ResourcePermDTO> getUserPerms(Integer uid) {
        //TODO 目前是查询所有权限 后期需要更改
        List<Resource> resources = list(Wrappers.<Resource>query().select(Resource.METHOD, Resource.MAPPING));
        return BeanConverter.convert(ResourcePermDTO.class, resources);
    }
}
