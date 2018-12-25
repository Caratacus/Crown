// 表格封装处理
var table = {
    // 初始化表格参数
    init: function (options) {
        $('#bootstrap-table').bootstrapTable({
            url: options.url,                                   // 请求后台的URL（*）
            contentType: "application/x-www-11form-urlencoded",   // 编码类型
            method: 'get',                                     // 请求方式（*）
            cache: false,                                       // 是否使用缓存
            striped: _striped,                                  // 是否显示行间隔色
            sortable: true,                                     // 是否启用排序
            sortStable: true,                                   // 设置为 true 将获得稳定的排序
            sortName: _sortName,                                // 排序列名称
            sortOrder: _sortOrder,                              // 排序方式  asc 或者 desc
            pagination: ,   // 是否显示分页（*）
            pageNumber: 1,                                      // 初始化加载第一页，默认第一页
            pageSize: 10,                                       // 每页的记录行数（*）
            pageList: [10, 25, 50],                             // 可供选择的每页的行数（*）
            escape: _escape,                                    // 转义HTML字符串
            iconSize: 'outline',                                // 图标大小：undefined默认的按钮尺寸 xs超小按钮sm小按钮lg大按钮
            toolbar: '#toolbar',                                // 指定工作栏
            sidePagination: "server",                           // 启用服务端分页
            search: ,           // 是否显示搜索框功能
            showSearch: ,   // 是否显示检索信息
            showRefresh: , // 是否显示刷新按钮
            showColumns: , // 是否显示隐藏某列下拉框
            showToggle: ,   // 是否显示详细视图和列表视图的切换按钮
            showExport: ,   // 是否支持导出文件
            queryParams: table._params,                       // 传递参数（*）
            columns: options.columns,                           // 显示列信息（*）
            responseHandler:             // 回调函数
        });
    }

}