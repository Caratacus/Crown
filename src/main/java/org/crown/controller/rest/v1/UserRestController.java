package org.crown.controller.rest.v1;


import java.util.List;

import org.crown.common.framework.controller.SuperController;
import org.crown.model.entity.User;
import org.crown.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 系统用户表 前端控制器
 * </p>
 *
 * @author Caratacus
 * @since 2018-10-25
 */
@RestController("/api")
public class UserRestController extends SuperController {

    @Autowired
    private IUserService userService;

    @GetMapping("/user")
    public List<User> users() {
        return userService.list();
    }
}

