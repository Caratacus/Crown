package org.crown.project.system.role.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.crown.common.annotation.Log;
import org.crown.common.enums.BusinessType;
import org.crown.common.utils.poi.ExcelUtils;
import org.crown.framework.enums.ErrorCodeEnum;
import org.crown.framework.model.ExcelDTO;
import org.crown.framework.utils.ApiAssert;
import org.crown.framework.web.controller.WebController;
import org.crown.framework.web.page.TableData;
import org.crown.project.system.role.domain.Role;
import org.crown.project.system.role.service.IRoleService;
import org.crown.project.system.user.domain.User;
import org.crown.project.system.user.domain.UserRole;
import org.crown.project.system.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 角色信息
 *
 * @author Crown
 */
@Controller
@RequestMapping("/system/role")
public class RoleController extends WebController {

    private final String prefix = "system/role";

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IUserService userService;

    @RequiresPermissions("system:role:view")
    @GetMapping
    public String role() {
        return prefix + "/role";
    }

    @RequiresPermissions("system:role:list")
    @PostMapping("/list")
    @ResponseBody
    public TableData<Role> list(Role role) {
        startPage();
        List<Role> list = roleService.selectRoleList(role);
        return getTableData(list);
    }

    @Log(title = "角色管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:role:export")
    @PostMapping("/export")
    @ResponseBody
    public ExcelDTO export(Role role) {
        List<Role> list = roleService.selectRoleList(role);
        ExcelUtils<Role> util = new ExcelUtils<>(Role.class);
        return new ExcelDTO(util.exportExcel(list, "角色数据"));

    }

    /**
     * 新增角色
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存角色
     */
    @RequiresPermissions("system:role:add")
    @Log(title = "角色管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public void addSave(@Validated Role role) {
        ApiAssert.isTrue(ErrorCodeEnum.ROLE_NAME_EXIST.overrideMsg("角色名称[" + role.getRoleName() + "]已存在"), roleService.checkRoleNameUnique(role));
        ApiAssert.isTrue(ErrorCodeEnum.ROLE_KEY_EXIST.overrideMsg("角色权限[" + role.getRoleKey() + "]已存在"), roleService.checkRoleKeyUnique(role));
        roleService.insertRole(role);
    }

    /**
     * 修改角色
     */
    @GetMapping("/edit/{roleId}")
    public String edit(@PathVariable("roleId") Long roleId, ModelMap mmap) {
        mmap.put("role", roleService.getById(roleId));
        return prefix + "/edit";
    }

    /**
     * 修改保存角色
     */
    @RequiresPermissions("system:role:edit")
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public void editSave(@Validated Role role) {
        ApiAssert.isTrue(ErrorCodeEnum.ROLE_NAME_EXIST.overrideMsg("角色名称[" + role.getRoleName() + "]已存在"), roleService.checkRoleNameUnique(role));
        ApiAssert.isTrue(ErrorCodeEnum.ROLE_KEY_EXIST.overrideMsg("角色权限[" + role.getRoleKey() + "]已存在"), roleService.checkRoleKeyUnique(role));
        roleService.updateRole(role);
    }

    /**
     * 角色分配数据权限
     */
    @GetMapping("/authDataScope/{roleId}")
    public String authDataScope(@PathVariable("roleId") Long roleId, ModelMap mmap) {
        mmap.put("role", roleService.getById(roleId));
        return prefix + "/dataScope";
    }

    /**
     * 保存角色分配数据权限
     */
    @RequiresPermissions("system:role:edit")
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PostMapping("/authDataScope")
    @ResponseBody
    public void authDataScopeSave(Role role) {
        roleService.authDataScope(role);
        setSysUser(userService.selectUserById(getSysUser().getUserId()));
    }

    @RequiresPermissions("system:role:remove")
    @Log(title = "角色管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public void remove(String ids) {
        roleService.deleteRoleByIds(ids);
    }

    /**
     * 校验角色名称
     */
    @PostMapping("/checkRoleNameUnique")
    @ResponseBody
    public boolean checkRoleNameUnique(Role role) {
        return roleService.checkRoleNameUnique(role);
    }

    /**
     * 校验角色权限
     */
    @PostMapping("/checkRoleKeyUnique")
    @ResponseBody
    public boolean checkRoleKeyUnique(Role role) {
        return roleService.checkRoleKeyUnique(role);
    }

    /**
     * 选择菜单树
     */
    @GetMapping("/selectMenuTree")
    public String selectMenuTree() {
        return prefix + "/tree";
    }

    /**
     * 角色状态修改
     */
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:role:edit")
    @PostMapping("/changeStatus")
    @ResponseBody
    public void changeStatus(Role role) {
        roleService.changeStatus(role);
    }

    /**
     * 分配用户
     */
    @RequiresPermissions("system:role:edit")
    @GetMapping("/authUser/{roleId}")
    public String authUser(@PathVariable("roleId") Long roleId, ModelMap mmap) {
        mmap.put("role", roleService.getById(roleId));
        return prefix + "/authUser";
    }

    /**
     * 查询已分配用户角色列表
     */
    @RequiresPermissions("system:role:list")
    @PostMapping("/authUser/allocatedList")
    @ResponseBody
    public TableData<User> allocatedList(User user) {
        startPage();
        List<User> list = userService.selectAllocatedList(user);
        return getTableData(list);
    }

    /**
     * 取消授权
     */
    @Log(title = "角色管理", businessType = BusinessType.GRANT)
    @PostMapping("/authUser/cancel")
    @ResponseBody
    public void cancelAuthUser(UserRole userRole) {
        roleService.deleteAuthUser(userRole);
    }

    /**
     * 批量取消授权
     */
    @Log(title = "角色管理", businessType = BusinessType.GRANT)
    @PostMapping("/authUser/cancelAll")
    @ResponseBody
    public void cancelAuthUserAll(Long roleId, String userIds) {
        roleService.deleteAuthUsers(roleId, userIds);
    }

    /**
     * 选择用户
     */
    @GetMapping("/authUser/selectUser/{roleId}")
    public String selectUser(@PathVariable("roleId") Long roleId, ModelMap mmap) {
        mmap.put("role", roleService.getById(roleId));
        return prefix + "/selectUser";
    }

    /**
     * 查询未分配用户角色列表
     */
    @RequiresPermissions("system:role:list")
    @PostMapping("/authUser/unallocatedList")
    @ResponseBody
    public TableData<User> unallocatedList(User user) {
        startPage();
        List<User> list = userService.selectUnallocatedList(user);
        return getTableData(list);
    }

    /**
     * 批量选择用户授权
     */
    @Log(title = "角色管理", businessType = BusinessType.GRANT)
    @PostMapping("/authUser/selectAll")
    @ResponseBody
    public void selectAuthUserAll(Long roleId, String userIds) {
        roleService.insertAuthUsers(roleId, userIds);
    }
}