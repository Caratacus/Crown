package org.crown.project.system.user.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.crown.common.utils.TypeUtils;
import org.crown.framework.web.controller.WebController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

/**
 * 登录验证
 *
 * @author Crown
 */
@Controller
@Slf4j
public class LoginController extends WebController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public void ajaxLogin(String username, String password) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, true);
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
    }

    @PostMapping("/captcha")
    @ResponseBody
    public boolean captcha(@RequestBody List<Integer> datas) {
        double sum = datas.stream().mapToDouble(TypeUtils::castToDouble).sum();
        int size = datas.size();
        double avg = sum / size;
        double stddev = datas.stream().mapToDouble(e -> Math.pow(e - avg, 2)).sum();
        double val = stddev / size;
        // 验证下拖动轨迹，为零时表示Y轴上下没有波动，非人为操作
        return val != 0;
    }

    @GetMapping("/unauth")
    public String unauth() {
        return "error/unauth";
    }
}
