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

import org.crown.framework.responses.SuccessResponses;
import org.crown.framework.SuperRestControllerTest;
import org.crown.framework.test.ControllerTest;
import org.crown.model.dto.TokenDTO;
import org.crown.model.parm.LoginPARM;
import org.crown.model.parm.PasswordPARM;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * <p>
 * AccountRestControllerTest
 * </p>
 *
 * @author Caratacus
 * @date 2018/11/7
 */
public class AccountRestControllerTest extends SuperRestControllerTest implements ControllerTest {

    @Autowired
    private AccountRestController restController;

    private MockMvc mockMvc;

    @Before
    @Override
    public void before() {
        mockMvc = getMockMvc(restController);
    }

    @Test
    public void getToken() throws Exception {
        //登陆
        LoginPARM loginPARM = new LoginPARM();
        loginPARM.setLoginName("crown");
        loginPARM.setPassword("crown");
        TokenDTO tokenDTO = getResult(mockMvc, post("/account/token", null, loginPARM), new TypeReference<SuccessResponses<TokenDTO>>() {
        });
        //updatePassword
        PasswordPARM passwordPARM = new PasswordPARM();
        passwordPARM.setOldPassword("crown");
        passwordPARM.setNewPassword("crown");
        isOk(mockMvc, put("/account/password", tokenDTO.getToken(), passwordPARM));
    }

    @Test
    public void removeToken() throws Exception {
        isOk(mockMvc, delete("/account/token", null));
    }

}
