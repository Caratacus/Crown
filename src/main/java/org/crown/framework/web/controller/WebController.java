package org.crown.framework.web.controller;

import java.util.List;

import org.crown.common.utils.StringUtils;
import org.crown.common.utils.TypeUtils;
import org.crown.common.utils.security.ShiroUtils;
import org.crown.framework.web.page.PageDomain;
import org.crown.framework.web.page.TableData;
import org.crown.project.system.user.domain.User;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * web层通用数据处理
 *
 * @author Crown
 */
public class WebController<Entity> extends SuperController<Entity> {

    /**
     * 封装分页对象
     */
    protected PageDomain getPageDomain() {
        // 页数
        Integer pageNum = TypeUtils.castToInt(request.getParameter(PAGE_NUM), 1);
        // 分页大小
        Integer pageSize = TypeUtils.castToInt(request.getParameter(PAGE_SIZE), DEFAULT_PAGE_SIZE);
        // 是否查询分页
        Boolean searchCount = TypeUtils.castToBoolean(request.getParameter(SEARCH_COUNT), true);
        pageSize = pageSize > MAX_PAGE_SIZE ? MAX_PAGE_SIZE : pageSize;
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(pageNum);
        pageDomain.setPageSize(pageSize);
        pageDomain.setSort(request.getParameter(PAGE_SORT));
        pageDomain.setOrder(request.getParameter(PAGE_ORDER));
        pageDomain.setTableAlias(getAlias());
        pageDomain.setSearchCount(searchCount);
        return pageDomain;
    }

    /**
     * 设置请求分页数据
     */
    protected void startPage() {
        PageDomain pageDomain = getPageDomain();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        boolean searchCount = pageDomain.isSearchCount();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
            PageHelper.startPage(pageNum, pageSize, searchCount).setOrderBy(pageDomain.getOrderBy());
        }
    }

    /**
     * 响应请求分页数据
     */
    protected <T> TableData<T> getTableData(List<T> list) {
        TableData rspData = new TableData();
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }

    /**
     * 页面跳转
     */
    public String redirect(String url) {
        return StringUtils.format("redirect:{}", url);
    }

    public User getSysUser() {
        return ShiroUtils.getSysUser();
    }

    public void setSysUser(User user) {
        ShiroUtils.setSysUser(user);
    }

    public Long getUserId() {
        return getSysUser().getUserId();
    }

    public String getLoginName() {
        return getSysUser().getLoginName();
    }
}
