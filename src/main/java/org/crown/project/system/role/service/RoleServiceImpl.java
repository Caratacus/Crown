package org.crown.project.system.role.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.crown.common.annotation.DataScope;
import org.crown.common.utils.StringUtils;
import org.crown.common.utils.TypeUtils;
import org.crown.common.utils.security.ShiroUtils;
import org.crown.framework.exception.Crown2Exception;
import org.crown.framework.service.impl.BaseServiceImpl;
import org.crown.project.system.role.domain.Role;
import org.crown.project.system.role.domain.RoleDept;
import org.crown.project.system.role.domain.RoleMenu;
import org.crown.project.system.role.mapper.RoleMapper;
import org.crown.project.system.user.domain.UserRole;
import org.crown.project.system.user.service.IUserRoleService;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;

/**
 * 角色 业务层处理
 *
 * @author Crown
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private IRoleMenuService roleMenuService;
    @Autowired
    private IRoleDeptService roleDeptService;
    @Autowired
    private IUserRoleService userRoleService;

    @Override
    @DataScope
    public List<Role> selectRoleList(Role role) {
        return baseMapper.selectRoleList(role);
    }

    @Override
    public Set<String> selectRoleKeys(Long userId) {
        List<Role> perms = baseMapper.selectRolesByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (Role perm : perms) {
            if (StringUtils.isNotNull(perm)) {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }

    @Override
    public List<Role> selectRolesByUserId(Long userId) {
        List<Role> userRoles = baseMapper.selectRolesByUserId(userId);
        List<Role> roles = selectRoleAll();
        for (Role role : roles) {
            for (Role userRole : userRoles) {
                if (role.getRoleId().longValue() == userRole.getRoleId().longValue()) {
                    role.setFlag(true);
                    break;
                }
            }
        }
        return roles;
    }

    @Override
    public List<Role> selectRoleAll() {
        return ((IRoleService) AopContext.currentProxy()).selectRoleList(new Role());
    }

    @Override
    public boolean deleteRoleById(Long roleId) {
        return delete().eq(Role::getRoleId, roleId).execute();
    }

    @Override
    public boolean deleteRoleByIds(String ids) {
        List<Long> roleIds = StringUtils.split2List(ids, TypeUtils::castToLong);
        for (Long roleId : roleIds) {
            Role role = getById(roleId);
            if (userRoleService.query().eq(UserRole::getRoleId, roleId).exist()) {
                throw new Crown2Exception(HttpServletResponse.SC_BAD_REQUEST, role.getRoleName() + "已分配，不能删除");
            }
        }
        return delete().in(Role::getRoleId, roleIds).execute();
    }

    @Override
    @Transactional
    public boolean insertRole(Role role) {
        // 新增角色信息
        save(role);
        ShiroUtils.clearCachedAuthorizationInfo();
        return insertRoleMenu(role);
    }

    @Override
    @Transactional
    public boolean updateRole(Role role) {
        // 修改角色信息
        updateById(role);
        ShiroUtils.clearCachedAuthorizationInfo();
        // 删除角色与菜单关联
        roleMenuService.delete().eq(RoleMenu::getRoleId, role.getRoleId()).execute();
        return insertRoleMenu(role);
    }

    @Override
    @Transactional
    public boolean authDataScope(Role role) {
        // 修改角色信息
        updateById(role);
        // 删除角色与部门关联
        roleDeptService.delete().eq(RoleDept::getRoleId, role.getRoleId()).execute();
        // 新增角色和部门信息（数据权限）
        return insertRoleDept(role);
    }

    /**
     * 新增角色菜单信息
     *
     * @param role 角色对象
     */
    public boolean insertRoleMenu(Role role) {
        roleMenuService.saveBatch(
                Arrays.stream(role.getMenuIds()).map(menuId -> {
                    RoleMenu rm = new RoleMenu();
                    rm.setRoleId(role.getRoleId());
                    rm.setMenuId(menuId);
                    return rm;
                }).collect(Collectors.toList())
        );
        return true;
    }

    /**
     * 新增角色部门信息(数据权限)
     *
     * @param role 角色对象
     */
    private boolean insertRoleDept(Role role) {
        roleDeptService.saveBatch(
                Arrays.stream(role.getDeptIds()).map(deptId -> {
                    RoleDept rd = new RoleDept();
                    rd.setRoleId(role.getRoleId());
                    rd.setDeptId(deptId);
                    return rd;
                }).collect(Collectors.toList())
        );
        return true;
    }

    @Override
    public boolean checkRoleNameUnique(Role role) {
        Long roleId = role.getRoleId();
        Role info = query().eq(Role::getRoleName, role.getRoleName()).getOne();
        return Objects.isNull(info) || info.getRoleId().equals(roleId);
    }

    @Override
    public boolean checkRoleKeyUnique(Role role) {
        Long roleId = role.getRoleId();
        Role info = query().eq(Role::getRoleKey, role.getRoleKey()).getOne();
        return Objects.isNull(info) || info.getRoleId().equals(roleId);
    }

    @Override
    public boolean changeStatus(Role role) {
        Role updateRole = new Role();
        updateRole.setStatus(role.getStatus());
        return update(updateRole, Wrappers.<Role>lambdaQuery().eq(Role::getRoleId, role.getRoleId()));

    }

    @Override
    public boolean deleteAuthUser(UserRole userRole) {
        return userRoleService.delete().eq(UserRole::getRoleId, userRole.getRoleId()).eq(UserRole::getUserId, userRole.getUserId()).execute();
    }

    /**
     * 批量取消授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要删除的用户数据ID
     * @return 结果
     */
    public boolean deleteAuthUsers(Long roleId, String userIds) {
        return userRoleService.delete().eq(UserRole::getRoleId, roleId).in(UserRole::getUserId, StringUtils.split2List(userIds)).execute();

    }

    /**
     * 批量选择授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要删除的用户数据ID
     * @return 结果
     */
    public boolean insertAuthUsers(Long roleId, String userIds) {
        userRoleService.saveBatch(
                StringUtils.split2List(userIds, TypeUtils::castToLong).stream().map(userId -> {
                    UserRole ur = new UserRole();
                    ur.setUserId(userId);
                    ur.setRoleId(roleId);
                    return ur;
                }).collect(Collectors.toList())
        );
        return true;
    }

}
