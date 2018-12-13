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

import org.crown.framework.SuperRestControllerTest;
import org.crown.framework.responses.SuccessResponses;
import org.crown.framework.test.ControllerTest;
import org.crown.model.dto.TokenDTO;
import org.crown.model.parm.AccountInfoPARM;
import org.crown.model.parm.LoginPARM;
import org.crown.model.parm.PasswordPARM;
import org.crown.service.IUserService;
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
 */
public class AccountRestControllerTest extends SuperRestControllerTest implements ControllerTest {

    @Autowired
    private AccountRestController restController;

    @Autowired
    private IUserService userService;

    private TokenDTO token;

    private MockMvc mockMvc;

    @Before
    @Override
    public void before() {
        mockMvc = getMockMvc(restController);
        token = userService.getToken(userService.getById(1));
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
        isNoContent(mockMvc, delete("/account/token", null));
    }

    @Test
    public void getAccountInfo() throws Exception {
        isOk(mockMvc, get("/account/info", token.getToken()));
    }

    @Test
    public void menus() throws Exception {
        isOk(mockMvc, get("/account/menus", token.getToken()));
    }

    @Test
    public void buttonsAliases() throws Exception {
        isOk(mockMvc, get("/account/buttons/aliases", token.getToken()));
    }

    @Test
    public void updateAccountInfo() throws Exception {
        AccountInfoPARM userInfoPARM = new AccountInfoPARM();
        userInfoPARM.setNickname("Crown");
        userInfoPARM.setEmail("caratacus@qq.com");
        userInfoPARM.setPhone("13712345678");
        isOk(mockMvc, put("/account/info", token.getToken(), userInfoPARM));
    }

}
