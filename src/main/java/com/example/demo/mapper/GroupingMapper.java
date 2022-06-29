package com.example.demo.mapper;

import com.example.demo.entity.Grouping;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author anonymous
 * @since 2022-06-29
 */
public interface GroupingMapper extends BaseMapper<Grouping> {
    //得到每个分组的设备数量
    @Select("select grouping_name,grouping_devicecount from grouping")
    List<Grouping> getGroup();
    //得到每个机构的设备数量
    @Select("select distinct grouping_mechanism,count(grouping_devicecount) AS grouping_devicecount from grouping GROUP BY grouping_mechanism")
    List<Grouping> getMechanism();
}
