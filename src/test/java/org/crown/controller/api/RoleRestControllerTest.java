package org.crown.controller.api;


import java.util.List;

import org.crown.CrownApplication;
import org.crown.common.api.model.responses.SuccessResponses;
import org.crown.common.framework.controller.SuperController;
import org.crown.common.kit.JacksonUtils;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
public class RoleRestControllerTest extends SuperController {

    @Autowired
    private RoleRestController roleRestController;

    @Autowired
    private IUserService userService;

    private MockMvc mockMvc;
    private TokenDTO token;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(roleRestController).build();
        token = userService.getToken(userService.getById(1));
    }

    @Test
    public void tests() throws Exception {
        //测试获取所有
        String responseString = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/role")
                        .header("Authorization", "Bearer " + token.getToken())
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
        SuccessResponses<Page<Role>> responses = JacksonUtils.readValue(responseString, new TypeReference<SuccessResponses<Page<Role>>>() {
        });
        //添加测试
        RolePARM rolePARM = new RolePARM();
        rolePARM.setRoleName("角色测试");
        rolePARM.setRemark("角色测试");
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/role")
                        .header("Authorization", "Bearer " + token.getToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JacksonUtils.toJson(rolePARM))
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        List<Role> records = responses.getResult().getRecords();
        for (Role record : records) {
            //测试获取单个
            mockMvc.perform(
                    MockMvcRequestBuilders.get("/api/role/" + record.getId())
                            .header("Authorization", "Bearer " + token.getToken())
            )
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
            if ("角色测试".equals(record.getRemark())) {
                //测试修改
                rolePARM.setRoleName("角色测试PUT");
                rolePARM.setRemark("角色测试PUT");
                mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/role" + record.getId())
                                .header("Authorization", "Bearer " + token.getToken())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JacksonUtils.toJson(rolePARM))
                )
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
                //测试删除
                mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/role" + record.getId())
                                .header("Authorization", "Bearer " + token.getToken())
                )
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
            }
        }

    }


/*     mockMvc.perform(
             MockMvcRequestBuilders.get("/api/user")
             .param("aa", "2018"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk());

    @ApiOperation(value = "查询所有角色")
    @GetMapping
    public ApiResponses<IPage<Role>> list() {
        IPage<Role> page = roleService.page(this.<Role>getPage());
        return success(page);
    }

    @ApiOperation(value = "查询当个角色")
    @GetMapping("/{id}")
    public ApiResponses<Role> get(@PathVariable("id") Integer id) {
        Role role = roleService.getById(id);
        return success(role);
    }

    @ApiOperation(value = "添加角色")
    @PostMapping
    public ApiResponses<Void> create(@RequestBody @Validated(RolePARM.Create.class) RolePARM rolePARM) {
        roleService.save(rolePARM.convert(Role.class));
        return empty();
    }

    @ApiOperation(value = "修改角色")
    @PutMapping("/{id}")
    public ApiResponses<Void> update(@PathVariable("id") Integer id, @RequestBody @Validated(RolePARM.Update.class) RolePARM rolePARM) {
        Role role = rolePARM.convert(Role.class);
        role.setId(id);
        roleService.updateById(role);
        return empty();
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("/{id}")
    public ApiResponses<Void> delete(@PathVariable("id") Integer id) {
        roleService.removeById(id);
        return empty();
    }*/
}

