package org.crown.project.system.dict.service;

import java.util.List;

import org.crown.framework.service.BaseService;
import org.crown.framework.web.domain.Ztree;
import org.crown.project.system.dict.domain.DictType;

/**
 * 字典 业务层
 *
 * @author Crown
 */
public interface IDictTypeService extends BaseService<DictType> {

    /**
     * 根据条件分页查询字典类型
     *
     * @param dictType 字典类型信息
     * @return 字典类型集合信息
     */
    List<DictType> selectDictTypeList(DictType dictType);

    /**
     * 批量删除字典类型
     *
     * @param ids 需要删除的数据
     * @return 结果
     * @throws Exception 异常
     */
    boolean deleteDictTypeByIds(String ids);

    /**
     * 修改保存字典类型信息
     *
     * @param dictType 字典类型信息
     * @return 结果
     */
    boolean updateDictType(DictType dictType);

    /**
     * 校验字典类型称是否唯一
     *
     * @param dictType 字典类型
     * @return 结果
     */
    boolean checkDictTypeUnique(DictType dictType);

    /**
     * 查询字典类型树
     *
     * @param dictType 字典类型
     * @return 所有字典类型
     */
    List<Ztree> selectDictTree(DictType dictType);

}
