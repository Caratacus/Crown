package org.crown.project.system.notice.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.crown.framework.mapper.BaseMapper;
import org.crown.project.system.notice.domain.Notice;

/**
 * 公告 数据层
 *
 * @author Crown
 */
@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {

}