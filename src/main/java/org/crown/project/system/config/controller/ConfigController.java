package org.crown.project.system.config.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.crown.common.annotation.Log;
import org.crown.common.enums.BusinessType;
import org.crown.common.utils.StringUtils;
import org.crown.common.utils.poi.ExcelUtils;
import org.crown.framework.enums.ErrorCodeEnum;
import org.crown.framework.model.ExcelDTO;
import org.crown.framework.utils.ApiAssert;
import org.crown.framework.web.controller.WebController;
import org.crown.framework.web.page.TableData;
import org.crown.project.system.config.domain.Config;
import org.crown.project.system.config.service.IConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;

/**
 * 参数配置 信息操作处理
 *
 * @author Crown
 */
@Controller
@RequestMapping("/system/config")
public class ConfigController extends WebController<Config> {

    private final String prefix = "system/config";

    @Autowired
    private IConfigService configService;

    @RequiresPermissions("system:config:view")
    @GetMapping
    public String config() {
        return prefix + "/config";
    }

    /**
     * 查询参数配置列表
     */
    @RequiresPermissions("system:config:list")
    @PostMapping("/list")
    @ResponseBody
    public TableData<Config> list(Config config) {
        startPage();
        List<Config> list = configService.selectConfigList(config);
        return getTableData(list);
    }

    @Log(title = "参数管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:config:export")
    @PostMapping("/export")
    @ResponseBody
    public ExcelDTO export(Config config) {
        List<Config> list = configService.selectConfigList(config);
        ExcelUtils<Config> util = new ExcelUtils<>(Config.class);
        return new ExcelDTO(util.exportExcel(list, "参数数据"));
    }

    /**
     * 新增参数配置
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存参数配置
     */
    @RequiresPermissions("system:config:add")
    @Log(title = "参数管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public void addSave(@Validated Config config) {
        ApiAssert.isTrue(ErrorCodeEnum.CONFIG_KEY_EXIST.overrideMsg("参数键名" + config.getConfigName() + "已存在"), configService.checkConfigKeyUnique(config));
        configService.save(config);
    }

    /**
     * 修改参数配置
     */
    @GetMapping("/edit/{configId}")
    public String edit(@PathVariable("configId") Long configId, ModelMap mmap) {
        mmap.put("config", configService.getById(configId));
        return prefix + "/edit";
    }

    /**
     * 修改保存参数配置
     */
    @RequiresPermissions("system:config:edit")
    @Log(title = "参数管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public void editSave(@Validated Config config) {
        ApiAssert.isTrue(ErrorCodeEnum.CONFIG_KEY_EXIST.overrideMsg("参数键名" + config.getConfigName() + "已存在"), configService.checkConfigKeyUnique(config));
        configService.updateById(config);
    }

    /**
     * 删除参数配置
     */
    @RequiresPermissions("system:config:remove")
    @Log(title = "参数管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public void remove(String ids) {

        configService.remove(
                Wrappers.<Config>lambdaQuery().in(Config::getConfigId, StringUtils.split2List(ids)
                )
        );
    }

    /**
     * 校验参数键名
     */
    @PostMapping("/checkConfigKeyUnique")
    @ResponseBody
    public Boolean checkConfigKeyUnique(Config config) {
        return configService.checkConfigKeyUnique(config);
    }
}
