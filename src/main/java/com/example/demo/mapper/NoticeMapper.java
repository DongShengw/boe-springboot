package com.example.demo.mapper;

import com.example.demo.entity.Notice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author anonymous
 * @since 2022-06-29
 */
public interface NoticeMapper extends BaseMapper<Notice> {
    @Update("update notice set notice_state=4 where notice_id=#{id}")
    int setState(@Param("id") int id);
}
