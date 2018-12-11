/*
 * Copyright (c) 2018-2022 Caratacus, (caratacus@qq.com).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.crown.controller;

import java.util.Collections;

import org.crown.emuns.StatusEnum;
import org.crown.framework.SuperRestControllerTest;
import org.crown.framework.test.ControllerTest;
import org.crown.model.dto.TokenDTO;
import org.crown.model.entity.User;
import org.crown.model.parm.UserInfoPARM;
import org.crown.model.parm.UserPARM;
import org.crown.service.IUserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;

/**
 * <p>
 * UserRestController 测试类
 * </p>
 *
 * @author Caratacus
 */
public class UserRestControllerTest extends SuperRestControllerTest implements ControllerTest {

    @Autowired
    private UserRestController restController;

    @Autowired
    private IUserService userService;

    private MockMvc mockMvc;
    private TokenDTO token;

    @Before
    @Override
    public void before() {
        mockMvc = getMockMvc(restController);
        token = userService.getToken(userService.getById(1));

    }

    @Test
    public void getUserDetails1() throws Exception {
        User user = new User();
        user.setId(2);
        user.setIp("2222");
        userService.update(user, Wrappers.<User>update().set("uid", 22).eq("uid", 1));
    }

    @Test
    public void getUserDetails() throws Exception {
        isOk(mockMvc, get("/user/details", token.getToken()));
    }

    @Test
    public void updateUserInfo() throws Exception {
        UserInfoPARM userInfoPARM = new UserInfoPARM();
        userInfoPARM.setNickname("Crown");
        userInfoPARM.setEmail("caratacus@qq.com");
        userInfoPARM.setPhone("13712345678");
        isOk(mockMvc, put("/user/info", token.getToken(), userInfoPARM));
    }

    @Test
    public void page() throws Exception {
        isOk(mockMvc, get("/user", token.getToken()));
    }

    @Test
    public void get() throws Exception {
        isOk(mockMvc, get("/user/1", token.getToken()));
    }

    @Test
    public void resetPwd() throws Exception {
        isOk(mockMvc, put("/user/" + token.getUid() + "/password/reset", token.getToken()));
    }

    @Test
    public void updateStatus() throws Exception {
        UserPARM userPARM = new UserPARM();
        userPARM.setStatus(StatusEnum.NORMAL);
        isOk(mockMvc, put("/user/" + token.getUid() + "/status", token.getToken(), userPARM));
    }

    @Test
    public void create() throws Exception {
        UserPARM userPARM = new UserPARM();
        userPARM.setLoginName("12121");
        userPARM.setNickname("222");
        userPARM.setEmail("11@qq.com");
        userPARM.setPhone("13617828937");
        userPARM.setStatus(StatusEnum.DISABLE);
        userPARM.setRoleIds(Collections.singletonList(1));
        isOk(mockMvc, post("/user", token.getToken(), userPARM));

    }

    @Test
    public void permMenus() throws Exception {
        isOk(mockMvc, get("/user/perm/menus", token.getToken()));
    }

    @Test
    public void permBottonAliases() throws Exception {
        isOk(mockMvc, get("/user/perm/botton/aliases", token.getToken()));
    }

}
