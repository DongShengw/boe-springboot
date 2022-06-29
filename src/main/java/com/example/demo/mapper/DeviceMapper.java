package com.example.demo.mapper;

import com.example.demo.entity.Device;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
    //得到计划
    @Select("select distinct device_schedule from device")
    List<String> querySchedule();
    //得到分组
    @Select("select distinct device_group from device")
    List<String> queryGroup();
    //删除设备
    @Delete("delete from device where device_id=#{id}")
    int deleteSchedule(@Param("id") Long id);
    //更新设备
    @Update("update device set device_name=#{name},device_group=#{group} where device_id=#{id}")
    int updateDevice(@Param("name") String name,@Param("group") String group,@Param("id") int id);
    //得到设备数量
    @Select("select count(device_id) from device")
    int getSum();
    //得到离线设备数量
    @Select("select count(device_id) from device where device_state=0")
    int getOffline();
    //得到空闲设备数量
    @Select("select count(device_id) from device where device_state=1")
    int getFree();
    //得到播放设备数量
    @Select("select count(device_id) from device where device_state=2")
    int getPlay();


}
