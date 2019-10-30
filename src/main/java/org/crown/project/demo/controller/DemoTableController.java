package org.crown.project.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.crown.common.utils.StringUtils;
import org.crown.framework.web.controller.WebController;
import org.crown.framework.web.page.PageDomain;
import org.crown.framework.web.page.TableData;
import org.crown.project.demo.domain.UserTableModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 表格相关
 *
 * @author Crown
 */
@Controller
@RequestMapping("/demo/table")
public class DemoTableController extends WebController {

    private final String prefix = "demo/table";

    private final static List<UserTableModel> users = new ArrayList<>();

    static {
        users.add(new UserTableModel(1, "1000001", "测试1", "0", "15888888888", "crown@qq.com", 150.0, "0"));
        users.add(new UserTableModel(2, "1000002", "测试2", "1", "15666666666", "crown@qq.com", 180.0, "1"));
        users.add(new UserTableModel(3, "1000003", "测试3", "0", "15666666666", "crown@qq.com", 110.0, "1"));
        users.add(new UserTableModel(4, "1000004", "测试4", "1", "15666666666", "crown@qq.com", 220.0, "1"));
        users.add(new UserTableModel(5, "1000005", "测试5", "0", "15666666666", "crown@qq.com", 140.0, "1"));
        users.add(new UserTableModel(6, "1000006", "测试6", "1", "15666666666", "crown@qq.com", 330.0, "1"));
        users.add(new UserTableModel(7, "1000007", "测试7", "0", "15666666666", "crown@qq.com", 160.0, "1"));
        users.add(new UserTableModel(8, "1000008", "测试8", "1", "15666666666", "crown@qq.com", 170.0, "1"));
        users.add(new UserTableModel(9, "1000009", "测试9", "0", "15666666666", "crown@qq.com", 180.0, "1"));
        users.add(new UserTableModel(10, "1000010", "测试10", "0", "15666666666", "crown@qq.com", 210.0, "1"));
        users.add(new UserTableModel(11, "1000011", "测试11", "1", "15666666666", "crown@qq.com", 110.0, "1"));
        users.add(new UserTableModel(12, "1000012", "测试12", "0", "15666666666", "crown@qq.com", 120.0, "1"));
        users.add(new UserTableModel(13, "1000013", "测试13", "1", "15666666666", "crown@qq.com", 380.0, "1"));
        users.add(new UserTableModel(14, "1000014", "测试14", "0", "15666666666", "crown@qq.com", 280.0, "1"));
        users.add(new UserTableModel(15, "1000015", "测试15", "0", "15666666666", "crown@qq.com", 570.0, "1"));
        users.add(new UserTableModel(16, "1000016", "测试16", "1", "15666666666", "crown@qq.com", 260.0, "1"));
        users.add(new UserTableModel(17, "1000017", "测试17", "1", "15666666666", "crown@qq.com", 210.0, "1"));
        users.add(new UserTableModel(18, "1000018", "测试18", "1", "15666666666", "crown@qq.com", 340.0, "1"));
        users.add(new UserTableModel(19, "1000019", "测试19", "1", "15666666666", "crown@qq.com", 160.0, "1"));
        users.add(new UserTableModel(20, "1000020", "测试20", "1", "15666666666", "crown@qq.com", 220.0, "1"));
        users.add(new UserTableModel(21, "1000021", "测试21", "1", "15666666666", "crown@qq.com", 120.0, "1"));
        users.add(new UserTableModel(22, "1000022", "测试22", "1", "15666666666", "crown@qq.com", 130.0, "1"));
        users.add(new UserTableModel(23, "1000023", "测试23", "1", "15666666666", "crown@qq.com", 490.0, "1"));
        users.add(new UserTableModel(24, "1000024", "测试24", "1", "15666666666", "crown@qq.com", 570.0, "1"));
        users.add(new UserTableModel(25, "1000025", "测试25", "1", "15666666666", "crown@qq.com", 250.0, "1"));
        users.add(new UserTableModel(26, "1000026", "测试26", "1", "15666666666", "crown@qq.com", 250.0, "1"));
    }

    /**
     * 搜索相关
     */
    @GetMapping("/search")
    public String search() {
        return prefix + "/search";
    }

    /**
     * 数据汇总
     */
    @GetMapping("/footer")
    public String footer() {
        return prefix + "/footer";
    }

    /**
     * 组合表头
     */
    @GetMapping("/groupHeader")
    public String groupHeader() {
        return prefix + "/groupHeader";
    }

    /**
     * 表格导出
     */
    @GetMapping("/export")
    public String export() {
        return prefix + "/export";
    }

    /**
     * 翻页记住选择
     */
    @GetMapping("/remember")
    public String remember() {
        return prefix + "/remember";
    }

    /**
     * 跳转至指定页
     */
    @GetMapping("/pageGo")
    public String pageGo() {
        return prefix + "/pageGo";
    }

    /**
     * 自定义查询参数
     */
    @GetMapping("/params")
    public String params() {
        return prefix + "/params";
    }

    /**
     * 多表格
     */
    @GetMapping("/multi")
    public String multi() {
        return prefix + "/multi";
    }

    /**
     * 点击按钮加载表格
     */
    @GetMapping("/button")
    public String button() {
        return prefix + "/button";
    }

    /**
     * 表格冻结列
     */
    @GetMapping("/fixedColumns")
    public String fixedColumns() {
        return prefix + "/fixedColumns";
    }

    /**
     * 自定义触发事件
     */
    @GetMapping("/event")
    public String event() {
        return prefix + "/event";
    }

    /**
     * 表格细节视图
     */
    @GetMapping("/detail")
    public String detail() {
        return prefix + "/detail";
    }

    /**
     * 表格图片预览
     */
    @GetMapping("/image")
    public String image() {
        return prefix + "/image";
    }

    /**
     * 动态增删改查
     */
    @GetMapping("/curd")
    public String curd() {
        return prefix + "/curd";
    }

    /**
     * 表格拖拽操作
     */
    @GetMapping("/reorder")
    public String reorder() {
        return prefix + "/reorder";
    }

    /**
     * 表格其他操作
     */
    @GetMapping("/other")
    public String other() {
        return prefix + "/other";
    }

    /**
     * 查询数据
     */
    @PostMapping("/list")
    @ResponseBody
    public TableData<UserTableModel> list(UserTableModel userModel) {
        TableData rspData = new TableData();
        List<UserTableModel> userList = new ArrayList<>(Arrays.asList(new UserTableModel[users.size()]));
        Collections.copy(userList, users);
        // 查询条件过滤
        if (StringUtils.isNotEmpty(userModel.getUserName())) {
            userList.clear();
            for (UserTableModel user : users) {
                if (user.getUserName().equals(userModel.getUserName())) {
                    userList.add(user);
                }
            }
        }
        PageDomain pageDomain = getPageDomain();
        if (null == pageDomain.getPageNum() || null == pageDomain.getPageSize()) {
            rspData.setRows(userList);
            rspData.setTotal(userList.size());
            return rspData;
        }
        int pageNum = (pageDomain.getPageNum() - 1) * 10;
        int pageSize = pageDomain.getPageNum() * 10;
        if (pageSize > userList.size()) {
            pageSize = userList.size();
        }
        rspData.setRows(userList.subList(pageNum, pageSize));
        rspData.setTotal(userList.size());
        return rspData;
    }
}

