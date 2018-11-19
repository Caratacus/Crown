package org.crown.controller;

import java.util.List;

import org.crown.common.annotations.Resources;
import org.crown.common.api.model.responses.ApiResponses;
import org.crown.common.framework.controller.SuperController;
import org.crown.model.entity.Role;
import org.crown.model.parm.RolePARM;
import org.crown.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author Caratacus
 * @since 2018-10-25
 */
@Api(tags = {"Role"}, description = "角色相关接口")
@RestController
@RequestMapping(value = "/role", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class RoleRestController extends SuperController {

    @Autowired
    private IRoleService roleService;

    @Resources
    @ApiOperation(value = "查询所有角色(分页)")
    @GetMapping
    public ApiResponses<IPage<Role>> page() {
        IPage<Role> page = roleService.page(this.<Role>getPage());
        return success(page);
    }

    @Resources
    @ApiOperation(value = "查询所有角色")
    @GetMapping("/roles")
    public ApiResponses<List<Role>> list() {
        return success(roleService.list());
    }

    @Resources
    @ApiOperation(value = "查询当个角色")
    @GetMapping("/{id}")
    public ApiResponses<Role> get(@PathVariable("id") Integer id) {
        Role role = roleService.getById(id);
        return success(role);
    }

    @Resources
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
    }
}

