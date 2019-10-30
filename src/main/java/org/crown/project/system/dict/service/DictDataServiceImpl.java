package org.crown.project.system.dict.service;

import java.util.List;

import org.crown.common.utils.StringUtils;
import org.crown.common.utils.TypeUtils;
import org.crown.framework.service.impl.BaseServiceImpl;
import org.crown.project.system.dict.domain.DictData;
import org.crown.project.system.dict.mapper.DictDataMapper;
import org.springframework.stereotype.Service;

/**
 * 字典 业务层处理
 *
 * @author Crown
 */
@Service
public class DictDataServiceImpl extends BaseServiceImpl<DictDataMapper, DictData> implements IDictDataService {

    @Override
    public List<DictData> selectDictDataList(DictData dictData) {
        return query().eq(StringUtils.isNotEmpty(dictData.getDictType()), DictData::getDictType, dictData.getDictType())
                .like(StringUtils.isNotEmpty(dictData.getDictLabel()), DictData::getDictLabel, dictData.getDictLabel())
                .eq(StringUtils.isNotEmpty(dictData.getStatus()), DictData::getStatus, dictData.getStatus())
                .list();
    }

    @Override
    public List<DictData> selectDictDataByType(String dictType) {
        return query().eq(DictData::getDictType, dictType).eq(DictData::getStatus, "0").orderByAsc(DictData::getDictSort).list();
    }

    @Override
    public String selectDictLabel(String dictType, String dictValue) {
        return query().select(DictData::getDictLabel).eq(DictData::getDictType, dictType).eq(DictData::getDictValue, dictValue).getObj(TypeUtils::castToString);
    }

}
