package com.example.demo.mapper;

import com.example.demo.entity.Program;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author anonymous
 * @since 2022-06-24
 */
public interface ProgramMapper extends BaseMapper<Program> {
    //得到节目数量
    @Select("select count(program_id) from program")
    int getSum();
}
