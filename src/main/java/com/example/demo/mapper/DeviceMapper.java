package com.example.demo.mapper;

import com.example.demo.entity.Device;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
}
