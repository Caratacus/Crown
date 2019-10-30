package org.crown.project.tool.builds;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.crown.framework.web.controller.WebController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * build 表单构建
 *
 * @author Crown
 */
@Controller
@RequestMapping("/tool/build")
public class BuildController extends WebController {

    private final String prefix = "tool/build";

    @RequiresPermissions("tool:build:view")
    @GetMapping
    public String build() {
        return prefix + "/build";
    }
}
