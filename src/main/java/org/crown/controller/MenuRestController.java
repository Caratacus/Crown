package org.crown.controller;

import java.util.List;

import org.crown.common.annotations.Resources;
import org.crown.common.api.model.responses.ApiResponses;
import org.crown.common.framework.controller.SuperController;
import org.crown.model.entity.Menu;
import org.crown.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
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

    @Resources
    @ApiOperation(value = "查询所有菜单")
    @GetMapping
    public ApiResponses<List<Menu>> list() {
        return success(menuService.list());
    }

}

