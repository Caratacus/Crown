package org.crown.service.impl;

import org.crown.model.entity.MenuResource;
import org.crown.mapper.MenuResourceMapper;
import org.crown.service.IMenuResourceService;
import org.crown.common.framework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜单资源关系表 服务实现类
 * </p>
 *
 * @author Caratacus
 * @since 2018-10-25
 */
@Service
public class MenuResourceServiceImpl extends BaseServiceImpl<MenuResourceMapper, MenuResource> implements IMenuResourceService {

}
