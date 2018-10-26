package org.crown.controller.rest.v1;


import java.time.LocalDate;
import java.util.List;

import org.crown.common.api.ApiAssert;
import org.crown.common.api.model.responses.ApiResponses;
import org.crown.common.framework.controller.SuperController;
import org.crown.emuns.ErrorCodeEnum;
import org.crown.model.entity.User;
import org.crown.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 系统用户表 前端控制器
 * </p>
 *
 * @author Caratacus
 * @since 2018-10-25
 */
@RestController
@RequestMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserRestController extends SuperController {

    @Autowired
    private IUserService userService;

    @GetMapping
    public ApiResponses<List<User>> users() {
        User user = new User();
        user.setCreateTime(LocalDate.now());
        ApiAssert.isNull(ErrorCodeEnum.FOR_EXAMPLE.convert("我就是想测试"), request.getParameter("a"));
        List<User> list = userService.list();
        boolean add = list.add(user);
        return success(list);
    }
}

