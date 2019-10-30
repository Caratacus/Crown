package org.crown.framework.web.service;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.crown.project.system.config.service.IConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * html调用 thymeleaf 实现参数管理
 *
 * @author Crown
 */
@Service("config")
public class ConfigService {

    private final List<String> skins = Arrays.asList("skin-blue", "skin-green", "skin-purple", "skin-red", "skin-yellow");

    @Autowired
    private IConfigService configService;

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数名称
     * @return 参数键值
     */
    public String getKey(String configKey) {
        return configService.getConfigValueByKey(configKey);
    }

    /**
     * 根据键名查询后台皮肤
     *
     * @param skinConfigKey 皮肤参数名称
     * @return 参数键值
     */
    public String getSkinKey(String skinConfigKey) {
        String configValue = getKey(skinConfigKey);
        return configValue.equals("skin-random") ? skins.get(RandomUtils.nextInt(0, 4)) : configValue;
    }

}
