package org.crown.service.impl;

import org.crown.model.entity.Resource;
import org.crown.mapper.ResourceMapper;
import org.crown.service.IResourceService;
import org.crown.common.framework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

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

}
