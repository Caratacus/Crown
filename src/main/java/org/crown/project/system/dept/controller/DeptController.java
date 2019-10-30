package org.crown.project.system.dept.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.crown.common.annotation.Log;
import org.crown.common.enums.BusinessType;
import org.crown.common.utils.StringUtils;
import org.crown.framework.enums.ErrorCodeEnum;
import org.crown.framework.utils.ApiAssert;
import org.crown.framework.web.controller.WebController;
import org.crown.framework.web.domain.Ztree;
import org.crown.project.system.dept.domain.Dept;
import org.crown.project.system.dept.service.IDeptService;
import org.crown.project.system.role.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;

/**
 * 部门信息
 *
 * @author Crown
 */
@Controller
@RequestMapping("/system/dept")
public class DeptController extends WebController<Dept> {

    private final String prefix = "system/dept";

    @Autowired
    private IDeptService deptService;

    @RequiresPermissions("system:dept:view")
    @GetMapping
    public String dept() {
        return prefix + "/dept";
    }

    @RequiresPermissions("system:dept:list")
    @PostMapping("/list")
    @ResponseBody
    public List<Dept> list(Dept dept) {
        return deptService.selectDeptList(dept);
    }

    /**
     * 新增部门
     */
    @GetMapping("/add/{parentId}")
    public String add(@PathVariable("parentId") Long parentId, ModelMap mmap) {
        mmap.put("dept", deptService.selectDeptById(parentId));
        return prefix + "/add";
    }

    /**
     * 新增保存部门
     */
    @Log(title = "部门管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("system:dept:add")
    @PostMapping("/add")
    @ResponseBody
    public void addSave(@Validated Dept dept) {
        ApiAssert.isTrue(ErrorCodeEnum.DEPT_NAME_EXIST.overrideMsg("部门名称[" + dept.getDeptName() + "]已经存在"), deptService.checkDeptNameUnique(dept));
        deptService.insertDept(dept);
    }

    /**
     * 修改
     */
    @GetMapping("/edit/{deptId}")
    public String edit(@PathVariable("deptId") Long deptId, ModelMap mmap) {
        Dept dept = deptService.selectDeptById(deptId);
        if (StringUtils.isNotNull(dept) && 100L == deptId) {
            dept.setParentName("无");
        }
        mmap.put("dept", dept);
        return prefix + "/edit";
    }

    /**
     * 保存
     */
    @Log(title = "部门管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:dept:edit")
    @PostMapping("/edit")
    @ResponseBody
    public void editSave(@Validated Dept dept) {
        ApiAssert.isTrue(ErrorCodeEnum.DEPT_NAME_EXIST.overrideMsg("部门名称[" + dept.getDeptName() + "]已经存在"), deptService.checkDeptNameUnique(dept));
        ApiAssert.isFalse(ErrorCodeEnum.DEPT_PARENT_DEPT_CANNOT_MYSELF, dept.getParentId().equals(dept.getDeptId()));
        deptService.updateDept(dept);
    }

    /**
     * 删除
     */
    @Log(title = "部门管理", businessType = BusinessType.DELETE)
    @RequiresPermissions("system:dept:remove")
    @GetMapping("/remove/{deptId}")
    @ResponseBody
    public void remove(@PathVariable("deptId") Long deptId) {
        ApiAssert.isFalse(ErrorCodeEnum.DEPT_EXISTING_LOWER_LEVEL_DEPT, deptService.exist(Wrappers.<Dept>lambdaQuery().eq(Dept::getParentId, deptId)));
        ApiAssert.isFalse(ErrorCodeEnum.DEPT_EXISTING_USER, deptService.exist(Wrappers.<Dept>lambdaQuery().eq(Dept::getParentId, deptId)));
    }

    /**
     * 校验部门名称
     */
    @PostMapping("/checkDeptNameUnique")
    @ResponseBody
    public boolean checkDeptNameUnique(Dept dept) {
        return deptService.checkDeptNameUnique(dept);
    }

    /**
     * 选择部门树
     */
    @GetMapping("/selectDeptTree/{deptId}")
    public String selectDeptTree(@PathVariable("deptId") Long deptId, ModelMap mmap) {
        mmap.put("dept", deptService.selectDeptById(deptId));
        return prefix + "/tree";
    }

    /**
     * 加载部门列表树
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData() {
        return deptService.selectDeptTree(new Dept());
    }

    /**
     * 加载角色部门（数据权限）列表树
     */
    @GetMapping("/roleDeptTreeData")
    @ResponseBody
    public List<Ztree> deptTreeData(Role role) {
        return deptService.roleDeptTreeData(role);
    }
}
