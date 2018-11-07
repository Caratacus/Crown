package org.crown.controller.rest;


import org.crown.common.api.model.responses.ApiResponses;
import org.crown.common.framework.controller.SuperController;
import org.crown.common.kit.IpUtils;
import org.crown.model.dto.TokenDTO;
import org.crown.model.entity.User;
import org.crown.model.parm.LoginPARM;
import org.crown.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 账户 前端控制器
 * </p>
 *
 * @author Caratacus
 * @since 2018-11-5
 */
@Api(tags = {"Account"}, description = "账号操作相关接口")
@RestController
@RequestMapping(value = "/api/account", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class AccountRestController extends SuperController {

    @Autowired
    private IUserService userService;

    @ApiOperation("获取Token")
    @PostMapping("/token")
    public ApiResponses<TokenDTO> getToken(@RequestBody @Validated LoginPARM loginPARM) {
        User user = userService.login(loginPARM.getLoginName(), loginPARM.getPassword(), IpUtils.getIpAddr(request));
        TokenDTO tokenDTO  = userService.getToken(user);
        return success(tokenDTO);
    }

    @ApiOperation("清理Token")
    @DeleteMapping("/token")
    public ApiResponses<Void> removeToken() {
        return empty();
    }
}

