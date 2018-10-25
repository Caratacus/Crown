package org.crown.service.impl;

import org.crown.model.entity.Menu;
import org.crown.mapper.MenuMapper;
import org.crown.service.IMenuService;
import org.crown.common.framework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author Caratacus
 * @since 2018-10-25
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<MenuMapper, Menu> implements IMenuService {

}
