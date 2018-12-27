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

import java.util.List;

import org.crown.enums.MenuTypeEnum;
import org.crown.enums.StatusEnum;
import org.crown.framework.SuperRestControllerTest;
import org.crown.framework.responses.SuccessResponses;
import org.crown.framework.test.ControllerTest;
import org.crown.model.dto.TokenDTO;
import org.crown.model.entity.Menu;
import org.crown.model.parm.MenuPARM;
import org.crown.service.IUserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * <p>
 * MenuRestControllerTest
 * </p>
 *
 * @author Caratacus
 */
public class MenuRestControllerTest extends SuperRestControllerTest implements ControllerTest {

    @Autowired
    private MenuRestController restController;

    private MockMvc mockMvc;
    private TokenDTO token;

    @Autowired
    private IUserService userService;

    @Before
    @Override
    public void before() {
        mockMvc = getMockMvc(restController);
        token = userService.getToken(userService.getById(1));
    }

    @Test
    public void list() throws Exception {
        //测试获取所有
        List<Menu> result = getResult(mockMvc, get("/menus", token.getToken()),
                new TypeReference<SuccessResponses<List<Menu>>>() {
                });
        for (Menu menu : result) {
            //获取单个
            isOk(mockMvc, get("/menus/" + menu.getId(), token.getToken()));
            //修改
            isOk(mockMvc, put("/menus/" + menu.getId(), token.getToken(), menu.convert(MenuPARM.class)));
            //修改状态
            MenuPARM menuPARM = new MenuPARM();
            menuPARM.setStatus(StatusEnum.NORMAL);
            isOk(mockMvc, put("/menus/" + menu.getId() + "/status", token.getToken(), menuPARM));
        }

    }

    @Test
    public void combos() throws Exception {
        isOk(mockMvc, get("/menus/combos", token.getToken()));
    }


    @Test
    public void create() throws Exception {
        MenuPARM menuPARM = new MenuPARM();
        menuPARM.setParentId(0);
        menuPARM.setMenuName("菜单名称");
        menuPARM.setPath("罗技");
        menuPARM.setMenuType(MenuTypeEnum.MENU);
        menuPARM.setIcon("icon");
        menuPARM.setStatus(StatusEnum.NORMAL);
        isCreated(mockMvc, post("/menus", token.getToken(), menuPARM));
        //测试获取所有
        List<Menu> result = getResult(mockMvc, get("/menus", token.getToken()),
                new TypeReference<SuccessResponses<List<Menu>>>() {
                });
        for (Menu menu : result) {
            isNoContent(mockMvc, delete("/menus/" + menu.getId(), token.getToken()));
        }

    }
}

