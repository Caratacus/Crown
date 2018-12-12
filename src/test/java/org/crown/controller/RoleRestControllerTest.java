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

import java.util.Arrays;
import java.util.List;

import org.crown.framework.SuperRestControllerTest;
import org.crown.framework.responses.SuccessResponses;
import org.crown.framework.test.ControllerTest;
import org.crown.model.dto.TokenDTO;
import org.crown.model.entity.Role;
import org.crown.model.parm.RolePARM;
import org.crown.service.IUserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * <p>
 * RoleRestControllerTest
 * </p>
 *
 * @author Caratacus
 */
public class RoleRestControllerTest extends SuperRestControllerTest implements ControllerTest {

    @Autowired
    private RoleRestController restController;

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
    public void menus() throws Exception {
        //修改角色菜单
        isOk(mockMvc, put("/roles/1/menus", token.getToken(), Arrays.asList(1, 2, 3)));
    }

    @Test
    public void tests() throws Exception {
        //测试获取所有(分页)
        isOk(mockMvc, get("/roles", token.getToken()));
        //添加测试
        RolePARM rolePARM = new RolePARM();
        rolePARM.setRoleName("角色测试");
        rolePARM.setRemark("角色测试");
        isCreated(mockMvc, post("/roles", token.getToken(), rolePARM));
        //测试获取所有
        List<Role> records = getResult(mockMvc, get("/roles/roles", token.getToken()), new TypeReference<SuccessResponses<List<Role>>>() {
        });
        for (Role record : records) {
            //测试获取单个
            isOk(mockMvc, get("/roles/" + record.getId(), token.getToken()));
            if ("角色测试".equals(record.getRemark())) {
                //测试修改
                rolePARM.setRoleName("角色测试PUT");
                rolePARM.setRemark("角色测试PUT");
                isOk(mockMvc, put("/roles/" + record.getId(), token.getToken(), rolePARM));
                //测试删除
                isNoContent(mockMvc, delete("/roles/" + record.getId(), token.getToken()));
            }
        }

    }

}

