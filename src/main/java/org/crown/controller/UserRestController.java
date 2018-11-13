package org.crown.controller;


import org.crown.common.annotations.Resources;
import org.crown.common.api.model.responses.ApiResponses;
import org.crown.common.framework.controller.SuperController;
import org.crown.model.dto.UserDetailsDTO;
import org.crown.model.entity.User;
import org.crown.model.parm.UserInfoPARM;
import org.crown.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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

