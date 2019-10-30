package org.crown.project.monitor.log.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.crown.framework.consolelog.ConsoleLog;
import org.crown.framework.web.controller.WebController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 日志监控
 *
 * @author Crown
 */
@Controller
@RequestMapping("/monitor/consolelog")
public class ConsoleLogController extends WebController {

    private final String prefix = "monitor/consolelog";

    @RequiresPermissions(ConsoleLog.VIEW_PERM)
    @GetMapping
    public String server() {
        return prefix + "/log";
    }
}
