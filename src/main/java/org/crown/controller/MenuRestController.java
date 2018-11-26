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

import org.crown.common.annotations.Resources;
import org.crown.common.api.model.responses.ApiResponses;
import org.crown.common.framework.controller.SuperController;
import org.crown.emuns.MenuTypeEnum;
import org.crown.model.dto.ComboDTO;
import org.crown.model.entity.Menu;
import org.crown.service.IMenuService;
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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author Caratacus
 * @since 2018-10-25
 */
@Api(tags = {"Menu"}, description = "菜单相关接口")
@RestController
@RequestMapping(value = "/menu", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class MenuRestController extends SuperController {

    @Autowired
    private IMenuService menuService;

    @Resources(verify = false)
    @ApiOperation(value = "查询所有菜单")
    @GetMapping
    public ApiResponses<List<Menu>> list() {
        return success(menuService.list());
    }

    @Resources(verify = false)
    @ApiOperation(value = "查询父级菜单(下拉框)")
    @GetMapping("/combos")
    public ApiResponses<List<ComboDTO>> list1() {
        List<ComboDTO> combos = menuService.entitys(Wrappers.<Menu>query().select(Menu.ID, Menu.MENU_NAME).in(Menu.MENU_TYPE, MenuTypeEnum.CATALOG, MenuTypeEnum.MENU), e -> {
            ComboDTO combo = new ComboDTO();
            combo.setId(e.getId());
            combo.setName(e.getMenuName());
            return combo;
        });
        return success(combos);
    }

    @Resources(verify = false)
    @ApiOperation(value = "查询单个菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "菜单ID", required = true, paramType = "path")
    })
    @GetMapping("/{id}")
    public ApiResponses<Menu> get(@PathVariable("id") Integer id) {
        Menu menu = menuService.getById(id);
        return success(menu);
    }

    @Resources
    @ApiOperation(value = "添加菜单")
    @PostMapping
    public ApiResponses<Void> create(@RequestBody @Validated Menu menu) {
        menuService.save(menu);
        return empty();
    }

    @Resources
    @ApiOperation(value = "修改菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "菜单ID", required = true, paramType = "path")
    })
    @PutMapping("/{id}")
    public ApiResponses<Void> update(@PathVariable("id") Integer id, @RequestBody @Validated Menu menu) {
        menu.setId(id);
        menuService.updateById(menu);
        return empty();
    }

    @Resources
    @ApiOperation(value = "删除菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "菜单ID", required = true, paramType = "path")
    })
    @DeleteMapping("/{id}")
    public ApiResponses<Void> delete(@PathVariable("id") Integer id) {
        menuService.removeMenu(id);
        return empty();
    }

}

