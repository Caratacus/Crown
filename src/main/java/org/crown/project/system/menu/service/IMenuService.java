package org.crown.project.system.menu.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.crown.framework.service.BaseService;
import org.crown.framework.web.domain.Ztree;
import org.crown.project.system.menu.domain.Menu;
import org.crown.project.system.role.domain.Role;
import org.crown.project.system.user.domain.User;

/**
 * 菜单 业务层
 *
 * @author Crown
 */
public interface IMenuService extends BaseService<Menu> {

    /**
     * 根据用户ID查询菜单
     *
     * @param user 用户信息
     * @return 菜单列表
     */
    List<Menu> selectMenusByUser(User user);

    /**
     * 查询系统菜单列表
     *
     * @param menu 菜单信息
     * @return 菜单列表
     */
    List<Menu> selectMenuList(Menu menu);

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    Set<String> selectPermsByUserId(Long userId);

    /**
     * 根据角色ID查询菜单
     *
     * @param role 角色对象
     * @return 菜单列表
     */
    List<Ztree> roleMenuTreeData(Role role);

    /**
     * 查询所有菜单信息
     *
     * @return 菜单列表
     */
    List<Ztree> menuTreeData();

    /**
     * 查询系统所有权限
     *
     * @return 权限列表
     */
    Map<String, String> selectPermsAll();

    /**
     * 删除菜单管理信息
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    boolean deleteMenuById(Long menuId);

    /**
     * 根据菜单ID查询信息
     *
     * @param menuId 菜单ID
     * @return 菜单信息
     */
    Menu selectMenuById(Long menuId);

    /**
     * 新增保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    boolean insertMenu(Menu menu);

    /**
     * 修改保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    boolean updateMenu(Menu menu);

    /**
     * 校验菜单名称是否唯一
     *
     * @param menu 菜单信息
     * @return 结果
     */
    boolean checkMenuNameUnique(Menu menu);
}
