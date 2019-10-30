package org.crown.project.system.user.controller;

import java.io.IOException;

import org.crown.common.annotation.Log;
import org.crown.common.enums.BusinessType;
import org.crown.common.utils.Crowns;
import org.crown.common.utils.file.FileUploadUtils;
import org.crown.common.utils.file.MimeTypes;
import org.crown.framework.enums.ErrorCodeEnum;
import org.crown.framework.shiro.service.PasswordService;
import org.crown.framework.utils.ApiAssert;
import org.crown.framework.web.controller.WebController;
import org.crown.project.system.user.domain.User;
import org.crown.project.system.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 个人信息 业务处理
 *
 * @author Crown
 */
@Controller
@RequestMapping("/system/user/profile")
public class ProfileController extends WebController {

    private final String prefix = "system/user/profile";

    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordService passwordService;

    /**
     * 个人信息
     */
    @GetMapping
    public String profile(ModelMap mmap) {
        User user = getSysUser();
        mmap.put("user", user);
        mmap.put("roleGroup", userService.selectUserRoleGroup(user.getUserId()));
        mmap.put("postGroup", userService.selectUserPostGroup(user.getUserId()));
        return prefix + "/profile";
    }

    @GetMapping("/checkPassword")
    @ResponseBody
    public boolean checkPassword(String password) {
        User user = getSysUser();
        return passwordService.matches(user, password);
    }

    @GetMapping("/resetPwd")
    public String resetPwd(ModelMap mmap) {
        User user = getSysUser();
        mmap.put("user", userService.selectUserById(user.getUserId()));
        return prefix + "/resetPwd";
    }

    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @PostMapping("/resetPwd")
    @ResponseBody
    public void resetPwd(String oldPassword, @RequestParam("newPassword") String newPassword) {
        User user = getSysUser();
        ApiAssert.isTrue(ErrorCodeEnum.USER_OLD_PASSWORD_ERROR, passwordService.matches(user, oldPassword));
        user.setPassword(newPassword);
        userService.resetUserPwd(user);
        setSysUser(userService.selectUserById(user.getUserId()));
    }

    /**
     * 修改用户
     */
    @GetMapping("/edit")
    public String edit(ModelMap mmap) {
        User user = getSysUser();
        mmap.put("user", userService.selectUserById(user.getUserId()));
        return prefix + "/edit";
    }

    /**
     * 修改头像
     */
    @GetMapping("/avatar")
    public String avatar(ModelMap mmap) {
        User user = getSysUser();
        mmap.put("user", userService.selectUserById(user.getUserId()));
        return prefix + "/avatar";
    }

    /**
     * 修改用户
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    @ResponseBody
    public void update(User user) {
        User currentUser = getSysUser();
        currentUser.setUserName(user.getUserName());
        currentUser.setEmail(user.getEmail());
        currentUser.setPhonenumber(user.getPhonenumber());
        currentUser.setSex(user.getSex());
        userService.updateById(currentUser);
        setSysUser(userService.selectUserById(currentUser.getUserId()));
    }

    /**
     * 保存头像
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PostMapping("/updateAvatar")
    @ResponseBody
    public void updateAvatar(@RequestParam("avatarfile") MultipartFile file) {
        User currentUser = getSysUser();
        ApiAssert.isFalse(ErrorCodeEnum.USER_AVATAR_NOT_EMPTY, file.isEmpty());
        String avatar = null;
        try {
            avatar = FileUploadUtils.upload(Crowns.getAvatarUploadPath(), file, MimeTypes.IMAGE_EXTENSION);
        } catch (IOException e) {
            ApiAssert.failure(ErrorCodeEnum.USER_AVATAR_UPLOAD_FAIL);
        }
        currentUser.setAvatar(avatar);
        userService.updateById(currentUser);
        setSysUser(userService.selectUserById(currentUser.getUserId()));
    }
}
