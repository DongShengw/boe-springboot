package com.example.demo.mapper;

import com.example.demo.entity.Device;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author anonymous
 * @since 2022-06-28
 */
public interface DeviceMapper extends BaseMapper<Device> {
    @Select("select distinct device_schedule from device")
    List<String> querySchedule();
    @Select("select distinct device_group from device")
    List<String> queryGroup();
    @Delete("delete from device where device_id=#{id}")
    int deleteSchedule(@Param("id") Long id);
}
