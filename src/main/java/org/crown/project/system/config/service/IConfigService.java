package org.crown.project.system.config.service;

import java.util.List;

import org.crown.framework.service.BaseService;
import org.crown.project.system.config.domain.Config;

/**
 * 参数配置 服务层
 *
 * @author Crown
 */
public interface IConfigService extends BaseService<Config> {

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数键名
     * @return 参数键值
     */
    Config selectConfigByKey(String configKey);

    /**
     * 查询参数配置列表
     *
     * @param config 参数配置信息
     * @return 参数配置集合
     */
    List<Config> selectConfigList(Config config);

    /**
     * 校验参数键名是否唯一
     *
     * @param config 参数信息
     * @return 结果
     */
    boolean checkConfigKeyUnique(Config config);

    /**
     * 根据config key 获取 value
     *
     * @param configkey
     * @return 结果
     */
    String getConfigValueByKey(String configkey);
}
