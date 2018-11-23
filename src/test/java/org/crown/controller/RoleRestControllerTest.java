/*
 * Copyright (c) 2018-2019 Caratacus, (caratacus@qq.com).
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

import java.util.List;

import org.crown.CrownApplication;
import org.crown.common.api.model.responses.SuccessResponses;
import org.crown.common.kit.JacksonUtils;
import org.crown.framework.SuperRestControllerTest;
import org.crown.framework.test.ControllerTest;
import org.crown.model.dto.TokenDTO;
import org.crown.model.entity.Role;
import org.crown.model.parm.RolePARM;
import org.crown.service.IUserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * <p>
 * RoleRestControllerTest
 * </p>
 *
 * @author Caratacus
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CrownApplication.class)
@AutoConfigureMockMvc
@WebAppConfiguration
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
    public void tests() throws Exception {
        //测试获取所有(分页)
        mockMvc.perform(
                MockMvcRequestBuilders.get("/role")
                        .header("Authorization", "Bearer " + token.getToken())
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        //测试获取所有
        String responseString = mockMvc.perform(
                MockMvcRequestBuilders.get("/role/roles")
                        .header("Authorization", "Bearer " + token.getToken())
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
        SuccessResponses<List<Role>> responses = JacksonUtils.readValue(responseString, new TypeReference<SuccessResponses<List<Role>>>() {
        });
        //添加测试
        RolePARM rolePARM = new RolePARM();
        rolePARM.setRoleName("角色测试");
        rolePARM.setRemark("角色测试");
        mockMvc.perform(
                MockMvcRequestBuilders.post("/role")
                        .header("Authorization", "Bearer " + token.getToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JacksonUtils.toJson(rolePARM))
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        List<Role> records = responses.getResult();
        for (Role record : records) {
            //测试获取单个
            mockMvc.perform(
                    MockMvcRequestBuilders.get("/role/" + record.getId())
                            .header("Authorization", "Bearer " + token.getToken())
            )
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
            if ("角色测试".equals(record.getRemark())) {
                //测试修改
                rolePARM.setRoleName("角色测试PUT");
                rolePARM.setRemark("角色测试PUT");
                mockMvc.perform(
                        MockMvcRequestBuilders.put("/role" + record.getId())
                                .header("Authorization", "Bearer " + token.getToken())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JacksonUtils.toJson(rolePARM))
                )
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
                //测试删除
                mockMvc.perform(
                        MockMvcRequestBuilders.delete("/role" + record.getId())
                                .header("Authorization", "Bearer " + token.getToken())
                )
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
            }
        }

    }

}

