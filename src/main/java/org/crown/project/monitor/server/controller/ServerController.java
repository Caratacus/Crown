package org.crown.project.monitor.server.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.crown.framework.web.controller.WebController;
import org.crown.project.monitor.server.domain.Server;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 服务器监控
 *
 * @author Crown
 */
@Controller
@RequestMapping("/monitor/server")
public class ServerController extends WebController {

    private final String prefix = "monitor/server";

    @RequiresPermissions("monitor:server:view")
    @GetMapping
    public String server(ModelMap mmap) {
        Server server = new Server();
        server.copyTo();
        mmap.put("server", server);
        return prefix + "/server";
    }
}
