package org.crown.controller.rest;

import org.crown.CrownApplication;
import org.crown.model.dto.TokenDTO;
import org.crown.service.IUserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * <p>
 * UserRestController 测试类
 * </p>
 *
 * @author Caratacus
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CrownApplication.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class UserRestControllerTest {

    @Autowired
    private UserRestController userRestController;

    @Autowired
    private IUserService userService;

    private MockMvc mockMvc;
    private TokenDTO token;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userRestController).build();
        token = userService.getToken(userService.getById(1));

    }

    @Test
    public void users() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/user")
                        .param("aa", "2018"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getUserDetails() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/user/details").header("Authorization", "Basic " + token.getToken()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}