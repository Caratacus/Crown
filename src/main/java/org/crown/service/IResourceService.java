package org.crown.service;

import java.util.List;

import org.crown.common.framework.service.BaseService;
import org.crown.model.dto.ResourcePermDTO;
import org.crown.model.entity.Resource;

/**
 * <p>
 * 资源表 服务类
 * </p>
 *
 * @author Caratacus
 * @since 2018-10-25
 */
public interface IResourceService extends BaseService<Resource> {

    /**
     * 根据用户ID获取用户所有权限
     *
     * @param uid
     * @return
     */
    List<ResourcePermDTO> getUserPerms(Integer uid);
}
