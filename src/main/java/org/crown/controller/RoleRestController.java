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

import javax.validation.constraints.NotEmpty;

import org.crown.common.annotations.Resources;
import org.crown.framework.controller.SuperController;
import org.crown.framework.responses.ApiResponses;
import org.crown.model.dto.RoleDTO;
import org.crown.model.entity.Role;
import org.crown.model.parm.RolePARM;
import org.crown.service.IRoleMenuService;
import org.crown.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author Caratacus
 */
@Api(tags = {"Role"}, description = "角色相关接口")
@RestController
@RequestMapping(value = "/roles", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class RoleRestController extends SuperController {

    @Autowired
    private IRoleService roleService;
    @Autowired
    private IRoleMenuService roleMenuService;

    @Resources
    @ApiOperation(value = "查询所有角色(分页)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleName", value = "需要查询的角色名", paramType = "query")
    })
    @GetMapping
    public ApiResponses<IPage<RoleDTO>> page(@RequestParam(value = "roleName", required = false) String roleName) {
        return success(roleService.pageRoleDTO(this.<Role>getPage(), roleName));
    }

    @Resources
    @ApiOperation(value = "查询所有角色")
    @GetMapping("/roles")
    public ApiResponses<List<Role>> list() {
        return success(roleService.list());
    }

    @Resources
    @ApiOperation(value = "查询单个角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色ID", required = true, paramType = "path")
    })
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
        return success(HttpStatus.CREATED);
    }

    @Resources
    @ApiOperation(value = "修改角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色ID", required = true, paramType = "path")
    })
    @PutMapping("/{id}")
    public ApiResponses<Void> update(@PathVariable("id") Integer id, @RequestBody @Validated(RolePARM.Update.class) RolePARM rolePARM) {
        Role role = rolePARM.convert(Role.class);
        role.setId(id);
        roleService.updateById(role);
        return success();
    }

    @Resources
    @ApiOperation(value = "删除角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色ID", required = true, paramType = "path")
    })
    @DeleteMapping("/{id}")
    public ApiResponses<Void> delete(@PathVariable("id") Integer id) {
        roleService.removeById(id);
        return success(HttpStatus.NO_CONTENT);
    }

    @Resources
    @ApiOperation(value = "修改角色菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色ID", required = true, paramType = "path")
    })
    @PutMapping("/{id}/menus")
    public ApiResponses<Void> menus(@PathVariable("id") Integer id, @RequestBody @NotEmpty(message = "菜单ID不能为空") List<Integer> menuIds) {
        roleMenuService.saveRoleMenu(id, menuIds);
        return success();
    }
}

