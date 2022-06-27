package com.example.demo.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Program;
import com.example.demo.entity.User;
import com.example.demo.mapper.ProgramMapper;
import com.example.demo.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author anonymous
 * @since 2022-06-24
 */
@RestController
@RequestMapping("/program")
public class ProgramController {
    @Resource
    ProgramMapper programMapper;
    //新增
    @PostMapping
    public Result save(@RequestBody Program program){
        programMapper.insert(program);

        return Result.succ(200,"新增成功",program);
    }
    //更新
    @PutMapping
    public Result update(@RequestBody Program program){
        programMapper.updateById(program);
        return Result.succ(200,"更新成功",program);
    }
    //删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id){
        programMapper.deleteById(id);
        return Result.succ(200,"删除成功",id);
    }
    //查询
    @GetMapping
    public Result findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam (defaultValue = "10") Integer pageSize,
                           @RequestParam (defaultValue = "") String search){
        LambdaQueryWrapper<Program> wrapper =  Wrappers.<Program>lambdaQuery();
        if (StrUtil.isNotBlank(search)){
            wrapper.like(Program::getProgramName,search);
        }
        Page<Program> userPage = (Page<Program>) programMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
        return Result.succ(userPage);
    }

}
