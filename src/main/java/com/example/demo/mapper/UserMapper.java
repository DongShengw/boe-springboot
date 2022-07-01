package com.example.demo.mapper;

import com.example.demo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author anonymous
 * @since 2022-06-24
 */
public interface UserMapper extends BaseMapper<User> {
    @Update("update user set user_state=0 where user_id=#{id}")
    int setState(@Param("id") int id);
}
