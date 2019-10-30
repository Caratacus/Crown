package org.crown.project.system.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.crown.framework.mapper.BaseMapper;
import org.crown.project.system.user.domain.UserPost;

/**
 * 用户与岗位 表 数据层
 *
 * @author Crown
 */
@Mapper
public interface UserPostMapper extends BaseMapper<UserPost> {

    /**
     * 批量新增用户岗位信息
     *
     * @param userPostList 用户角色列表
     * @return 结果
     */
    int batchUserPost(List<UserPost> userPostList);
}
