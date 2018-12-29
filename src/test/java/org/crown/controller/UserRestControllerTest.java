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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.crown.enums.StatusEnum;
import org.crown.framework.SuperRestControllerTest;
import org.crown.framework.test.ControllerTest;
import org.crown.model.dto.TokenDTO;
import org.crown.model.entity.User;
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
    public void getUserDetails1() {
        User user = new User();
        user.setId(2);
        user.setIp("2222");
        userService.update(user, Wrappers.<User>update().set("uid", 22).eq("uid", 1));
    }

    @Test
    public void page() throws Exception {
        isOk(mockMvc, get("/users", token.getToken()));
    }

    @Test
    public void get() throws Exception {
        isOk(mockMvc, get("/users/1", token.getToken()));
    }

    @Test
    public void resetPwd() throws Exception {
        isOk(mockMvc, put("/users/" + token.getUid() + "/password", token.getToken()));
    }

    @Test
    public void updateStatus() throws Exception {
        UserPARM userPARM = new UserPARM();
        userPARM.setStatus(StatusEnum.NORMAL);
        isOk(mockMvc, put("/users/" + token.getUid() + "/status", token.getToken(), userPARM));
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
        isCreated(mockMvc, post("/users", token.getToken(), userPARM));
    }

    @Test
    public void batchCreate() {
        User userPARM = new User();
        userPARM.setLoginName("12121");
        userPARM.setPassword("2222222a");
        userPARM.setNickname("222");
        userPARM.setEmail("11@qq.com");
        userPARM.setPhone("13617828937");
        userPARM.setStatus(StatusEnum.DISABLE);
        userPARM.setCreateUid(1);
        userPARM.setCreateUid(1);
        userPARM.setCreateTime(LocalDateTime.now());
        userPARM.setUpdateTime(LocalDateTime.now());
        User userPARM1 = new User();
        userPARM1.setLoginName("12121222");
        userPARM1.setPassword("2222222a");
        userPARM1.setNickname("222333");
        userPARM1.setEmail("11@qq.com");
        userPARM1.setPhone("13617828937");
        userPARM1.setStatus(StatusEnum.DISABLE);
        userPARM1.setCreateUid(1);
        userPARM1.setCreateUid(1);
        userPARM1.setCreateTime(LocalDateTime.now());
        userPARM1.setUpdateTime(LocalDateTime.now());
        List<User> list = new ArrayList<>(2);
        list.add(userPARM);
        list.add(userPARM1);
        userService.saveBatch(list);
    }

}
