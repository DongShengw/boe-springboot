package com.example.demo.mapper;

import com.example.demo.entity.Program;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
    //重命名
    @Update("update program set program_name=#{name} where program_id=#{id}")
    int reName(@Param("name") String name,@Param("id") int id);
}
