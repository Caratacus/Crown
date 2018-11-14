package org.crown.controller;


import java.util.List;

import org.crown.common.annotations.Resources;
import org.crown.common.api.ApiAssert;
import org.crown.common.api.model.responses.ApiResponses;
import org.crown.common.emuns.ErrorCodeEnum;
import org.crown.common.framework.controller.SuperController;
import org.crown.common.kit.BeanConverter;
import org.crown.model.dto.UserDTO;
import org.crown.model.dto.UserDetailsDTO;
import org.crown.model.entity.User;
import org.crown.model.parm.UserInfoPARM;
import org.crown.model.parm.UserPARM;
import org.crown.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 系统用户表 前端控制器
 * </p>
 *
 * @author Caratacus
 * @since 2018-10-25
 */
@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class UserRestController extends SuperController {

    @Autowired
    private IUserService userService;

    @Resources
    @ApiOperation("查询所有用户")
    @GetMapping
    public ApiResponses<List<UserDTO>> list() {
        List<User> users = userService.list();
        List<UserDTO> userDTOs = BeanConverter.convert(UserDTO.class, users);
        return success(userDTOs);
    }

    @Resources
    @ApiOperation("查询单个用户")
    @GetMapping("/{id}")
    public ApiResponses<UserDTO> get(@PathVariable("id") Integer id) {
        User user = userService.getById(id);
        ApiAssert.notNull(ErrorCodeEnum.USER_NOT_FOUND, user);
        return success(user.convert(UserDTO.class));
    }

    @Resources
    @ApiOperation("重置用户密码")
    @PutMapping("/{id}/password/reset")
    public ApiResponses<Void> resetPwd(@PathVariable("id") Integer id) {
        userService.resetPwd(id);
        return empty();
    }

    @Resources
    @ApiOperation("设置用户状态")
    @PutMapping("/{id}/status")
    public ApiResponses<Void> updateStatus(@PathVariable("id") Integer id, @RequestBody @Validated(UserPARM.Status.class) UserPARM userPARM) {
        userService.updateStatus(id, userPARM.getStatus());
        return empty();
    }

    @Resources
    @ApiOperation("创建用户")
    @PostMapping
    public ApiResponses<Void> create(@RequestBody @Validated(UserPARM.Create.class) UserPARM userPARM) {
        User user = userPARM.convert(User.class);
        userService.save(user);
        return empty();
    }

    @Resources
    @ApiOperation("修改用户")
    @PutMapping("/{id}")
    public ApiResponses<Void> update(@PathVariable("id") Integer id, @RequestBody @Validated(UserPARM.Update.class) UserPARM userPARM) {
        User user = userPARM.convert(User.class);
        user.setId(id);
        userService.updateById(user);
        return empty();
    }

    //----
    @Resources
    @ApiOperation("获取用户详情")
    @GetMapping("/details")
    public ApiResponses<UserDetailsDTO> getUserDetails() {
        Integer uid = currentUid();
        UserDetailsDTO userDetails = userService.getUserDetails(uid);
        return success(userDetails);
    }

    @Resources
    @ApiOperation("修改用户信息")
    @PutMapping("/info")
    public ApiResponses<Void> updateUserInfo(@RequestBody @Validated UserInfoPARM userInfoPARM) {
        Integer uid = currentUid();
        User user = userInfoPARM.convert(User.class);
        user.setId(uid);
        userService.updateById(user);
        return empty();
    }

}

