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
import org.crown.framework.test.ControllerTest;
import org.crown.model.dto.TokenDTO;
import org.crown.service.IResourceService;
import org.crown.service.IUserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

/**
 * <p>
 * AccountRestControllerTest
 * </p>
 *
 * @author Caratacus
 */
public class ResourceRestControllerTest extends SuperRestControllerTest implements ControllerTest {

    @Autowired
    private ResourceRestController restController;

    @Autowired
    private IResourceService resourceService;

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
    public void page() throws Exception {
        isOk(mockMvc, get("/resources", token.getToken()));
    }

    @Test
    public void refresh() throws Exception {
        isOk(mockMvc, put("/resources", token.getToken()));
    }

    @Test
    public void list() throws Exception {
        isOk(mockMvc, get("/resources/resources", token.getToken()));
    }

    @Test
    public void getUserPerms() {
        resourceService.getUserPerms(1);
    }

    @Test
    public void getOpenPerms() {
        resourceService.getOpenPerms();
    }

    @Test
    public void getLoginPerms() {
        resourceService.getLoginPerms();
    }

    @Test
    public void getPerms() {
        resourceService.getPerms();
    }

    @Test
    public void getUserResourcePerms() {
        resourceService.getUserResourcePerms(1);
    }
}
