package com.example.demo.mapper;

import com.example.demo.entity.ScheduleList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author anonymous
 * @since 2022-06-24
 */
public interface ScheduleListMapper extends BaseMapper<ScheduleList> {
    //得到计划数量
    @Select("select count(list_id) from schedule_list")
    int getSum();
    //得到前3条计划
    @Select("SELECT list_id,list_name,list_state,list_update,list_author  from schedule_list LIMIT 5")
    List<ScheduleList> getSchedule();
}
