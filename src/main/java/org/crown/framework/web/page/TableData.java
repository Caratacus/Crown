package org.crown.framework.web.page;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 表格分页数据对象
 *
 * @author Crown
 */
@Setter
@Getter
public class TableData<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 总记录数
     */
    private long total;
    /**
     * 列表数据
     */
    private List<T> rows;

    /**
     * 表格数据对象
     */
    public TableData() {
    }

    /**
     * 分页
     *
     * @param list  列表数据
     * @param total 总记录数
     */
    public TableData(List<T> list, int total) {
        this.rows = list;
        this.total = total;
    }

}
