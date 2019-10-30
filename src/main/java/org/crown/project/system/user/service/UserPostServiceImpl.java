package org.crown.project.system.user.service;

import org.crown.framework.service.impl.BaseServiceImpl;
import org.crown.project.system.user.domain.UserPost;
import org.crown.project.system.user.mapper.UserPostMapper;
import org.springframework.stereotype.Service;

/**
 * 用户岗位 业务层处理
 *
 * @author Crown
 */
@Service
public class UserPostServiceImpl extends BaseServiceImpl<UserPostMapper, UserPost> implements IUserPostService {

}
