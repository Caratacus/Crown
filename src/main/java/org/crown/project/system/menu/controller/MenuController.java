package org.crown.project.system.menu.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.crown.common.annotation.Log;
import org.crown.common.enums.BusinessType;
import org.crown.framework.enums.ErrorCodeEnum;
import org.crown.framework.utils.ApiAssert;
import org.crown.framework.web.controller.WebController;
import org.crown.framework.web.domain.Ztree;
import org.crown.project.system.menu.domain.Menu;
import org.crown.project.system.menu.service.IMenuService;
import org.crown.project.system.role.domain.Role;
import org.crown.project.system.role.domain.RoleMenu;
import org.crown.project.system.role.service.IRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;

/**
 * 菜单信息
 *
 * @author Crown
 */
@Controller
@RequestMapping("/system/menu")
public class MenuController extends WebController<Menu> {

    private final String prefix = "system/menu";

    @Autowired
    private IMenuService menuService;
    @Autowired
    private IRoleMenuService roleMenuService;

    @RequiresPermissions("system:menu:view")
    @GetMapping
    public String menu() {
        return prefix + "/menu";
    }

    @RequiresPermissions("system:menu:list")
    @PostMapping("/list")
    @ResponseBody
    public List<Menu> list(Menu menu) {
        return menuService.selectMenuList(menu);
    }

    /**
     * 删除菜单
     */
    @Log(title = "菜单管理", businessType = BusinessType.DELETE)
    @RequiresPermissions("system:menu:remove")
    @GetMapping("/remove/{menuId}")
    @ResponseBody
    public void remove(@PathVariable("menuId") Long menuId) {
        ApiAssert.isFalse(ErrorCodeEnum.MENU_EXISTING_LOWER_LEVEL_MENU, menuService.exist(Wrappers.<Menu>lambdaQuery().eq(Menu::getMenuId, menuId)));
        ApiAssert.isFalse(ErrorCodeEnum.MENU_EXISTING_USING, roleMenuService.exist(Wrappers.<RoleMenu>lambdaQuery().eq(RoleMenu::getMenuId, menuId)));
        menuService.deleteMenuById(menuId);
    }

    /**
     * 新增
     */
    @GetMapping("/add/{parentId}")
    public String add(@PathVariable("parentId") Long parentId, ModelMap mmap) {
        Menu menu;
        if (0L != parentId) {
            menu = menuService.selectMenuById(parentId);
        } else {
            menu = new Menu();
            menu.setMenuId(0L);
            menu.setMenuName("主目录");
        }
        mmap.put("menu", menu);
        return prefix + "/add";
    }

    /**
     * 新增保存菜单
     */
    @Log(title = "菜单管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("system:menu:add")
    @PostMapping("/add")
    @ResponseBody
    public void addSave(@Validated Menu menu) {
        ApiAssert.isTrue(ErrorCodeEnum.MENU_NAME_EXIST.overrideMsg("菜单名称[" + menu.getMenuName() + "]已存在"), menuService.checkMenuNameUnique(menu));
        menuService.insertMenu(menu);
    }

    /**
     * 修改菜单
     */
    @GetMapping("/edit/{menuId}")
    public String edit(@PathVariable("menuId") Long menuId, ModelMap mmap) {
        mmap.put("menu", menuService.selectMenuById(menuId));
        return prefix + "/edit";
    }

    /**
     * 修改保存菜单
     */
    @Log(title = "菜单管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:menu:edit")
    @PostMapping("/edit")
    @ResponseBody
    public void editSave(@Validated Menu menu) {
        ApiAssert.isTrue(ErrorCodeEnum.MENU_NAME_EXIST.overrideMsg("菜单名称[" + menu.getMenuName() + "]已存在"), menuService.checkMenuNameUnique(menu));
        menuService.updateMenu(menu);
    }

    /**
     * 选择菜单图标
     */
    @GetMapping("/icon")
    public String icon() {
        return prefix + "/icon";
    }

    /**
     * 校验菜单名称
     */
    @PostMapping("/checkMenuNameUnique")
    @ResponseBody
    public boolean checkMenuNameUnique(Menu menu) {
        return menuService.checkMenuNameUnique(menu);
    }

    /**
     * 加载角色菜单列表树
     */
    @GetMapping("/roleMenuTreeData")
    @ResponseBody
    public List<Ztree> roleMenuTreeData(Role role) {
        return menuService.roleMenuTreeData(role);
    }

    /**
     * 加载所有菜单列表树
     */
    @GetMapping("/menuTreeData")
    @ResponseBody
    public List<Ztree> menuTreeData() {
        return menuService.menuTreeData();
    }

    /**
     * 选择菜单树
     */
    @GetMapping("/selectMenuTree/{menuId}")
    public String selectMenuTree(@PathVariable("menuId") Long menuId, ModelMap mmap) {
        mmap.put("menu", menuService.selectMenuById(menuId));
        return prefix + "/tree";
    }
}