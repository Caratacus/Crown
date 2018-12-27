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
import org.crown.enums.AuthTypeEnum;
import org.crown.enums.MenuTypeEnum;
import org.crown.framework.controller.SuperController;
import org.crown.framework.responses.ApiResponses;
import org.crown.model.dto.ComboDTO;
import org.crown.model.dto.MenuDTO;
import org.crown.model.entity.Menu;
import org.crown.model.parm.MenuPARM;
import org.crown.service.IMenuService;
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
import org.springframework.web.bind.annotation.RestController;

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
 */
@Api(tags = {"Menu"}, description = "菜单相关接口")
@RestController
@RequestMapping(value = "/menus", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class MenuRestController extends SuperController {

    @Autowired
    private IMenuService menuService;

    @Resources
    @ApiOperation(value = "查询所有菜单")
    @GetMapping
    public ApiResponses<List<Menu>> list() {
        return success(menuService.list());
    }

    @Resources(auth = AuthTypeEnum.OPEN)
    @ApiOperation(value = "查询父级菜单(下拉框)")
    @GetMapping("/combos")
    public ApiResponses<List<ComboDTO>> combos() {
        return success(
                menuService.query().select(Menu::getId, Menu::getMenuName)
                        .in(Menu::getMenuType, MenuTypeEnum.CATALOG, MenuTypeEnum.MENU)
                        .entitys(e -> {
                            ComboDTO combo = new ComboDTO();
                            combo.setId(e.getId());
                            combo.setName(e.getMenuName());
                            return combo;
                        })
        );
    }

    @Resources
    @ApiOperation(value = "查询单个菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "菜单ID", required = true, paramType = "path")
    })
    @GetMapping("/{id}")
    public ApiResponses<MenuDTO> get(@PathVariable("id") Integer id) {
        return success(menuService.getMenuDTODetails(id));
    }

    @Resources
    @ApiOperation(value = "添加菜单")
    @PostMapping
    public ApiResponses<Void> create(@RequestBody @Validated(MenuPARM.Create.class) MenuPARM menuPARM) {
        Menu menu = menuPARM.convert(Menu.class);
        menuService.saveMenu(menu, menuPARM.getResourceIds());
        return success(HttpStatus.CREATED);
    }

    @Resources
    @ApiOperation(value = "修改菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "菜单ID", required = true, paramType = "path")
    })
    @PutMapping("/{id}")
    public ApiResponses<Void> update(@PathVariable("id") Integer id, @RequestBody @Validated(MenuPARM.Update.class) MenuPARM menuPARM) {
        Menu menu = menuPARM.convert(Menu.class);
        menu.setId(id);
        menuService.updateMenu(menu, menuPARM.getResourceIds());
        return success();
    }

    @Resources
    @ApiOperation(value = "删除菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "菜单ID", required = true, paramType = "path")
    })
    @DeleteMapping("/{id}")
    public ApiResponses<Void> delete(@PathVariable("id") Integer id) {
        menuService.removeMenu(id);
        return success(HttpStatus.NO_CONTENT);
    }

    @Resources
    @ApiOperation("设置菜单状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "菜单ID", required = true, paramType = "path")
    })
    @PutMapping("/{id}/status")
    public ApiResponses<Void> updateStatus(@PathVariable("id") Integer id, @RequestBody @Validated(MenuPARM.Status.class) MenuPARM menuPARM) {
        menuService.updateStatus(id, menuPARM.getStatus());
        return success();
    }

}

