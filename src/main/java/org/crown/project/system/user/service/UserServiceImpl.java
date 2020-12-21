package org.crown.project.system.user.service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.crown.common.annotation.DataScope;
import org.crown.common.utils.StringUtils;
import org.crown.common.utils.TypeUtils;
import org.crown.common.utils.security.ShiroUtils;
import org.crown.framework.enums.ErrorCodeEnum;
import org.crown.framework.exception.Crown2Exception;
import org.crown.framework.service.impl.BaseServiceImpl;
import org.crown.framework.shiro.service.PasswordService;
import org.crown.framework.utils.ApiAssert;
import org.crown.project.system.config.service.IConfigService;
import org.crown.project.system.post.domain.Post;
import org.crown.project.system.post.service.IPostService;
import org.crown.project.system.role.domain.Role;
import org.crown.project.system.role.service.IRoleService;
import org.crown.project.system.user.domain.User;
import org.crown.project.system.user.domain.UserPost;
import org.crown.project.system.user.domain.UserRole;
import org.crown.project.system.user.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;

/**
 * 用户 业务层处理
 *
 * @author Crown
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements IUserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPostService postService;

    @Autowired
    private IUserPostService userPostService;

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IConfigService configService;

    @Autowired
    private PasswordService passwordService;

    @Override
    @DataScope
    public List<User> selectUserList(User user) {
        // 生成数据权限过滤条件
        return baseMapper.selectUserList(user);
    }

    /**
     * 根据条件分页查询已分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @DataScope
    public List<User> selectAllocatedList(User user) {
        return baseMapper.selectAllocatedList(user);
    }

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @DataScope
    public List<User> selectUnallocatedList(User user) {
        return baseMapper.selectUnallocatedList(user);
    }

    @Override
    public User selectUserByLoginName(String userName) {
        return baseMapper.selectUserByLoginName(userName);
    }

    @Override
    public User selectUserByPhoneNumber(String phoneNumber) {
        return baseMapper.selectUserByPhoneNumber(phoneNumber);
    }

    @Override
    public User selectUserByEmail(String email) {
        return baseMapper.selectUserByEmail(email);
    }

    @Override
    public User selectUserById(Long userId) {
        return baseMapper.selectUserById(userId);
    }

    @Override
    public boolean deleteUserByIds(String ids) {
        List<Long> userIds = StringUtils.split2List(ids, TypeUtils::castToLong);
        for (Long userId : userIds) {
            ApiAssert.isFalse(ErrorCodeEnum.USER_CANNOT_UPDATE_SUPER_ADMIN, User.isAdmin(userId));
        }
        return delete().in(User::getUserId, userIds).execute();
    }

    @Override
    @Transactional
    public boolean insertUser(User user) {
        user.randomSalt();
        user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        // 新增用户信息
        save(user);
        // 新增用户岗位关联
        insertUserPost(user);
        // 新增用户与角色管理
        insertUserRole(user);
        return true;
    }

    @Override
    @Transactional
    public boolean updateUser(User user) {
        Long userId = user.getUserId();
        user.setUpdateBy(ShiroUtils.getLoginName());
        // 删除用户与角色关联
        userRoleService.delete().eq(UserRole::getUserId, userId).execute();
        // 新增用户与角色管理
        insertUserRole(user);
        // 删除用户与岗位关联
        userPostService.delete().eq(UserPost::getUserId, userId).execute();
        // 新增用户与岗位管理
        insertUserPost(user);
        return updateById(user);
    }

    @Override
    public boolean resetUserPwd(User user) {
        user.randomSalt();
        user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        return updateById(user);
    }

    /**
     * 新增用户角色信息
     *
     * @param user 用户对象
     */
    public void insertUserRole(User user) {
        userRoleService.saveBatch(
                Arrays.stream(user.getRoleIds()).map(roleId -> {
                    UserRole ur = new UserRole();
                    ur.setUserId(user.getUserId());
                    ur.setRoleId(roleId);
                    return ur;
                }).collect(Collectors.toList())
        );
    }

    /**
     * 新增用户岗位信息
     *
     * @param user 用户对象
     */
    public void insertUserPost(User user) {
        userPostService.saveBatch(
                Arrays.stream(user.getPostIds()).map(postId -> {
                    UserPost up = new UserPost();
                    up.setUserId(user.getUserId());
                    up.setPostId(postId);
                    return up;
                }).collect(Collectors.toList())
        );
    }

    @Override
    public boolean checkLoginNameUnique(String loginName) {
        return query().eq(User::getLoginName, loginName).nonExist();
    }

    @Override
    public boolean checkPhoneUnique(User user) {
        Long userId = user.getUserId();
        User info = query().select(User::getUserId, User::getPhonenumber).eq(User::getPhonenumber, user.getPhonenumber()).getOne();
        return Objects.isNull(info) || info.getUserId().equals(userId);
    }

    @Override
    public boolean checkEmailUnique(User user) {
        Long userId = user.getUserId();
        User info = query().select(User::getUserId, User::getEmail).eq(User::getEmail, user.getEmail()).getOne();
        return Objects.isNull(info) || info.getUserId().equals(userId);
    }

    @Override
    public String selectUserRoleGroup(Long userId) {
        List<Role> list = roleService.selectRolesByUserId(userId);
        StringBuilder idsStr = new StringBuilder();
        for (Role role : list) {
            idsStr.append(role.getRoleName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString())) {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    @Override
    public String selectUserPostGroup(Long userId) {
        List<Post> list = postService.selectPostsByUserId(userId);
        StringBuilder idsStr = new StringBuilder();
        for (Post post : list) {
            idsStr.append(post.getPostName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString())) {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    @Override
    public String importUser(List<User> userList, Boolean isUpdateSupport) {
        if (CollectionUtils.isEmpty(userList)) {
            throw new Crown2Exception(HttpServletResponse.SC_BAD_REQUEST, "导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String operName = ShiroUtils.getLoginName();
        String password = configService.getConfigValueByKey("sys.user.initPassword");
        for (User user : userList) {
            try {
                // 验证是否存在这个用户
                User u = baseMapper.selectUserByLoginName(user.getLoginName());
                if (StringUtils.isNull(u)) {
                    user.setPassword(password);
                    user.setCreateBy(operName);
                    this.insertUser(user);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、账号 ").append(user.getLoginName()).append(" 导入成功");
                } else if (isUpdateSupport) {
                    user.setUpdateBy(operName);
                    this.updateUser(user);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、账号 ").append(user.getLoginName()).append(" 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>").append(failureNum).append("、账号 ").append(user.getLoginName()).append(" 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + user.getLoginName() + " 导入失败：";
                failureMsg.append(msg).append(e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new Crown2Exception(HttpServletResponse.SC_BAD_REQUEST, failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    @Override
    public boolean changeStatus(User user) {
        ApiAssert.isFalse(ErrorCodeEnum.USER_CANNOT_UPDATE_SUPER_ADMIN, User.isAdmin(user.getUserId()));
        return updateById(user);
    }
}
