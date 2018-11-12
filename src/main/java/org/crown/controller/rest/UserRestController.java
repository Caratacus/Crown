package org.crown.controller.rest;


import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.Min;

import org.crown.common.api.ApiAssert;
import org.crown.common.api.model.responses.ApiResponses;
import org.crown.common.emuns.ErrorCodeEnum;
import org.crown.common.framework.controller.SuperController;
import org.crown.model.dto.UserDetailsDTO;
import org.crown.model.entity.User;
import org.crown.model.parm.UserInfoPARM;
import org.crown.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class UserRestController extends SuperController {

    @Autowired
    private IUserService userService;

    @ApiOperation("获取用户详情")
    @GetMapping("/details")
    public ApiResponses<UserDetailsDTO> getUserDetails() {
        Integer uid = currentUid();
        UserDetailsDTO userDetails = userService.getUserDetails(uid);
        return success(userDetails);
    }

    @ApiOperation("修改用户信息")
    @PutMapping("/info")
    public ApiResponses<Void> updateUserInfo(@RequestBody @Validated UserInfoPARM userInfoPARM) {
        Integer uid = currentUid();
        User user = userInfoPARM.convert(User.class);
        user.setId(uid);
        userService.updateById(user);
        return empty();
    }


    @GetMapping
    @ApiOperation("1")
    public ApiResponses<List<User>> users(@RequestParam @Min(value = 2010) Integer aa) {
        User user = new User();
        user.setCreateTime(LocalDateTime.now());
        ApiAssert.notNull(ErrorCodeEnum.FORBIDDEN.convert("我就是想测试"), aa);
        List<User> list = userService.list();
        boolean add = list.add(user);
        return success(list);
    }

    @PostMapping
    @ApiOperation("测试")
    public ApiResponses<List<User>> users(@RequestBody @Validated List<User> list1) {
        System.out.println(list1);
        User user = new User();
        user.setCreateTime(LocalDateTime.now());
        ApiAssert.isNull(ErrorCodeEnum.FORBIDDEN.convert("我就是想测试"), request.getParameter("a"));
        List<User> list = userService.list();
        boolean add = list.add(user);
        return success(list);
    }
}

