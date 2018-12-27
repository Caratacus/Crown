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

import java.util.List;
import java.util.Set;

import org.crown.enums.StatusEnum;
import org.crown.framework.service.BaseService;
import org.crown.model.dto.MenuDTO;
import org.crown.model.dto.MenuTreeDTO;
import org.crown.model.entity.Menu;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author Caratacus
 */
public interface IMenuService extends BaseService<Menu> {

    /**
     * 保存菜单
     *
     * @param menu
     * @param resourceIds
     */
    void saveMenu(Menu menu, List<String> resourceIds);

    /**
     * 修改菜单
     *
     * @param menu
     * @param resourceIds
     */
    void updateMenu(Menu menu, List<String> resourceIds);

    /**
     * 递归删除菜单
     *
     * @param menuId
     */
    void removeMenu(Integer menuId);

    /**
     * 修改菜单状态
     *
     * @param menuId
     * @param status
     */
    void updateStatus(Integer menuId, StatusEnum status);

    /**
     * 获取菜单详情
     *
     * @param menuId
     * @return
     */
    MenuDTO getMenuDTODetails(Integer menuId);

    /**
     * 获取用户权限菜单
     *
     * @param uid
     * @return
     */
    List<MenuTreeDTO> getUserPermMenus(Integer uid);

    /**
     * 获取用户按钮权限
     *
     * @param uid
     * @return
     */
    Set<String> getUserPermButtonAliases(Integer uid);

}
