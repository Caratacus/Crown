package org.crown.project.system.post.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.crown.common.annotation.Log;
import org.crown.common.enums.BusinessType;
import org.crown.common.utils.poi.ExcelUtils;
import org.crown.framework.enums.ErrorCodeEnum;
import org.crown.framework.model.ExcelDTO;
import org.crown.framework.utils.ApiAssert;
import org.crown.framework.web.controller.WebController;
import org.crown.framework.web.page.TableData;
import org.crown.project.system.post.domain.Post;
import org.crown.project.system.post.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 岗位信息操作处理
 *
 * @author Crown
 */
@Controller
@RequestMapping("/system/post")
public class PostController extends WebController<Post> {

    private final String prefix = "system/post";

    @Autowired
    private IPostService postService;

    @RequiresPermissions("system:post:view")
    @GetMapping
    public String operlog() {
        return prefix + "/post";
    }

    @RequiresPermissions("system:post:list")
    @PostMapping("/list")
    @ResponseBody
    public TableData<Post> list(Post post) {
        startPage();
        List<Post> list = postService.selectPostList(post);
        return getTableData(list);
    }

    @Log(title = "岗位管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:post:export")
    @PostMapping("/export")
    @ResponseBody
    public ExcelDTO export(Post post) {
        List<Post> list = postService.selectPostList(post);
        ExcelUtils<Post> util = new ExcelUtils<>(Post.class);
        return new ExcelDTO(util.exportExcel(list, "岗位数据"));

    }

    @RequiresPermissions("system:post:remove")
    @Log(title = "岗位管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public void remove(String ids) {
        postService.deletePostByIds(ids);
    }

    /**
     * 新增岗位
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存岗位
     */
    @RequiresPermissions("system:post:add")
    @Log(title = "岗位管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public void addSave(@Validated Post post) {
        ApiAssert.isTrue(ErrorCodeEnum.POST_NAME_EXIST.overrideMsg("岗位名称[" + post.getPostName() + "]已存在"), postService.checkPostNameUnique(post));
        ApiAssert.isTrue(ErrorCodeEnum.POST_CODE_EXIST.overrideMsg("岗位编码[" + post.getPostCode() + "]已存在"), postService.checkPostCodeUnique(post));
        postService.save(post);
    }

    /**
     * 修改岗位
     */
    @GetMapping("/edit/{postId}")
    public String edit(@PathVariable("postId") Long postId, ModelMap mmap) {
        mmap.put("post", postService.getById(postId));
        return prefix + "/edit";
    }

    /**
     * 修改保存岗位
     */
    @RequiresPermissions("system:post:edit")
    @Log(title = "岗位管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public void editSave(@Validated Post post) {
        ApiAssert.isTrue(ErrorCodeEnum.POST_NAME_EXIST.overrideMsg("岗位名称[" + post.getPostName() + "]已存在"), postService.checkPostNameUnique(post));
        ApiAssert.isTrue(ErrorCodeEnum.POST_CODE_EXIST.overrideMsg("岗位编码[" + post.getPostCode() + "]已存在"), postService.checkPostCodeUnique(post));
        postService.updateById(post);
    }

    /**
     * 校验岗位名称
     */
    @PostMapping("/checkPostNameUnique")
    @ResponseBody
    public boolean checkPostNameUnique(Post post) {
        return postService.checkPostNameUnique(post);
    }

    /**
     * 校验岗位编码
     */
    @PostMapping("/checkPostCodeUnique")
    @ResponseBody
    public boolean checkPostCodeUnique(Post post) {
        return postService.checkPostCodeUnique(post);
    }
}
