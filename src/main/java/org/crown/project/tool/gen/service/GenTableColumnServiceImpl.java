package org.crown.project.tool.gen.service;

import java.util.List;

import org.crown.common.utils.StringUtils;
import org.crown.framework.service.impl.BaseServiceImpl;
import org.crown.project.tool.gen.domain.GenTableColumn;
import org.crown.project.tool.gen.mapper.GenTableColumnMapper;
import org.springframework.stereotype.Service;

/**
 * 业务字段 服务层实现
 *
 * @author Crown
 */
@Service
public class GenTableColumnServiceImpl extends BaseServiceImpl<GenTableColumnMapper, GenTableColumn> implements IGenTableColumnService {

    @Override
    public List<GenTableColumn> selectGenTableColumnListByTableId(Long tableId) {
        return query().eq(GenTableColumn::getTableId, tableId).orderByAsc(GenTableColumn::getSort).list();
    }

    @Override
    public boolean deleteGenTableColumnByIds(String ids) {
        return delete().in(GenTableColumn::getTableId, StringUtils.split2List(ids)).execute();
    }

    @Override
    public List<GenTableColumn> selectDbTableColumnsByName(String tableName) {
        return baseMapper.selectDbTableColumnsByName(tableName);
    }
}