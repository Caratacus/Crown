package org.crown.project.system.menu.service;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.crown.common.utils.StringUtils;
import org.crown.common.utils.TreeUtils;
import org.crown.common.utils.security.ShiroUtils;
import org.crown.framework.service.impl.BaseServiceImpl;
import org.crown.framework.web.domain.Ztree;
import org.crown.project.system.menu.domain.Menu;
import org.crown.project.system.menu.mapper.MenuMapper;
import org.crown.project.system.role.domain.Role;
import org.crown.project.system.user.domain.User;
import org.springframework.stereotype.Service;

/**
 * 菜单 业务层处理
 *
 * @author Crown
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<MenuMapper, Menu> implements IMenuService {

    public static final String PREMISSION_STRING = "perms[\"{0}\"]";

    @Override
    public List<Menu> selectMenusByUser(User user) {
        List<Menu> menus;
        // 管理员显示所有菜单信息
        if (user.isAdmin()) {
            menus = baseMapper.selectMenuNormalAll();
        } else {
            menus = baseMapper.selectMenusByUserId(user.getUserId());
        }
        return TreeUtils.getChildPerms(menus, 0);
    }

    @Override
    public List<Menu> selectMenuList(Menu menu) {

        List<Menu> menuList;
        User user = ShiroUtils.getSysUser();
        if (user.isAdmin()) {
            menuList = query().like(StringUtils.isNotEmpty(menu.getMenuName()), Menu::getMenuName, menu.getMenuName())
                    .eq(StringUtils.isNotEmpty(menu.getVisible()), Menu::getVisible, menu.getVisible())
                    .orderByAsc(Menu::getParentId)
                    .orderByAsc(Menu::getOrderNum)
                    .list();
        } else {
            menu.getParams().put("userId", user.getUserId());
            menuList = baseMapper.selectMenuListByUserId(menu);
        }
        return menuList;

    }

    @Override
    public Set<String> selectPermsByUserId(Long userId) {
        List<String> perms = baseMapper.selectPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotEmpty(perm)) {
                permsSet.addAll(StringUtils.split2List(perm.trim()));
            }
        }
        return permsSet;
    }

    @Override
    public List<Ztree> roleMenuTreeData(Role role) {
        Long roleId = role.getRoleId();
        List<Ztree> ztrees;
        List<Menu> menuList = list();
        if (StringUtils.isNotNull(roleId)) {
            List<String> roleMenuList = baseMapper.selectMenuTree(roleId);
            ztrees = initZtree(menuList, roleMenuList, true);
        } else {
            ztrees = initZtree(menuList, null, true);
        }
        return ztrees;
    }

    @Override
    public List<Ztree> menuTreeData() {
        List<Menu> menuList = list();
        return initZtree(menuList);
    }

    @Override
    public List<Menu> list() {
        List<Menu> menuList;
        User user = ShiroUtils.getSysUser();
        if (user.isAdmin()) {
            menuList = query().orderByAsc(Menu::getParentId).orderByAsc(Menu::getOrderNum).list();
        } else {
            menuList = baseMapper.selectMenuAllByUserId(user.getUserId());
        }
        return menuList;

    }

    @Override
    public LinkedHashMap<String, String> selectPermsAll() {
        return list().stream()
                .collect(
                        Collectors.toMap(Menu::getUrl, v -> MessageFormat.format(PREMISSION_STRING, v.getPerms()),
                                (k, v) -> {
                                    throw new IllegalStateException(String.format("Duplicate key %s", k));
                                }, LinkedHashMap::new)
                );
    }

    /**
     * 对象转菜单树
     *
     * @param menuList 菜单列表
     * @return 树结构列表
     */
    public List<Ztree> initZtree(List<Menu> menuList) {
        return initZtree(menuList, null, false);
    }

    /**
     * 对象转菜单树
     *
     * @param menuList     菜单列表
     * @param roleMenuList 角色已存在菜单列表
     * @param permsFlag    是否需要显示权限标识
     * @return 树结构列表
     */
    public List<Ztree> initZtree(List<Menu> menuList, List<String> roleMenuList, boolean permsFlag) {
        boolean isCheck = StringUtils.isNotNull(roleMenuList);
        return menuList.stream().map(menu -> {
            Ztree ztree = new Ztree();
            ztree.setId(menu.getMenuId());
            ztree.setpId(menu.getParentId());
            ztree.setName(transMenuName(menu, permsFlag));
            ztree.setTitle(menu.getMenuName());
            if (isCheck) {
                ztree.setChecked(roleMenuList.contains(menu.getMenuId() + menu.getPerms()));
            }
            return ztree;
        }).collect(Collectors.toList());
    }

    private String transMenuName(Menu menu, boolean permsFlag) {
        StringBuilder sb = new StringBuilder(menu.getMenuName());
        if (permsFlag) {
            sb.append("<font color=\"#888\">&nbsp;&nbsp;&nbsp;").append(menu.getPerms()).append("</font>");
        }
        return sb.toString();
    }

    @Override
    public boolean deleteMenuById(Long menuId) {
        ShiroUtils.clearCachedAuthorizationInfo();
        return delete().and(e -> e.eq(Menu::getMenuId, menuId).or().eq(Menu::getParentId, menuId)).execute();
    }

    @Override
    public Menu selectMenuById(Long menuId) {
        return baseMapper.selectMenuById(menuId);
    }

    @Override
    public boolean insertMenu(Menu menu) {
        ShiroUtils.clearCachedAuthorizationInfo();
        return save(menu);
    }

    @Override
    public boolean updateMenu(Menu menu) {
        ShiroUtils.clearCachedAuthorizationInfo();
        return updateById(menu);
    }

    @Override
    public boolean checkMenuNameUnique(Menu menu) {
        Long menuId = menu.getMenuId();
        Menu info = query().eq(Menu::getMenuName, menu.getMenuName()).eq(Menu::getParentId, menu.getParentId()).getOne();
        return Objects.isNull(info) || info.getMenuId().equals(menuId);
    }

}
