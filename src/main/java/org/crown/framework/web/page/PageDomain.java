package org.crown.framework.web.page;

import org.crown.common.utils.StringUtils;
import org.crown.common.utils.sql.AntiSQLFilter;

import lombok.Getter;
import lombok.Setter;

/**
 * 分页数据
 *
 * @author Crown
 */
@Setter
@Getter
public class PageDomain {

    /**
     * 当前记录起始索引
     */
    private Integer pageNum;
    /**
     * 每页显示记录数
     */
    private Integer pageSize;
    /**
     * 排序列
     */
    private String sort;
    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    private String order;
    /**
     * 排序表别名
     */
    private String tableAlias;
    /**
     * 查询Count
     */
    private boolean searchCount;

    public String getOrderBy() {
        if (StringUtils.isEmpty(sort)) {
            return "";
        }
        if (StringUtils.isNotEmpty(tableAlias)) {
            return tableAlias + "." + StringUtils.toUnderScoreCase(sort) + " " + order;
        }

        return StringUtils.toUnderScoreCase(sort) + " " + order;
    }

    public String getSort() {
        return AntiSQLFilter.getSafeValue(sort);
    }

}
