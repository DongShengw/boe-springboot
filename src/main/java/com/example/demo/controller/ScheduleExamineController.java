package com.example.demo.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.ScheduleExamine;
import com.example.demo.entity.ScheduleList;
import com.example.demo.mapper.ScheduleExamineMapper;
import com.example.demo.mapper.ScheduleListMapper;
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
@RequestMapping("/schedule-examine")
public class ScheduleExamineController {
    @Resource
    ScheduleExamineMapper scheduleExamineMapper;
    //新增
    @PostMapping
    public Result save(@RequestBody ScheduleExamine scheduleExamine){
        scheduleExamineMapper.insert(scheduleExamine);

        return Result.succ(200,"新增成功",scheduleExamine);
    }
    //更新
    @PutMapping
    public Result update(@RequestBody ScheduleExamine scheduleExamine){
        scheduleExamineMapper.updateById(scheduleExamine);
        return Result.succ(200,"更新成功",scheduleExamine);
    }
    //删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id){
        scheduleExamineMapper.deleteById(id);
        return Result.succ(200,"删除成功",id);
    }
    //查询
    @GetMapping
    public Result findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam (defaultValue = "10") Integer pageSize,
                           @RequestParam (defaultValue = "") String search){
        LambdaQueryWrapper<ScheduleExamine> wrapper =  Wrappers.<ScheduleExamine>lambdaQuery();
        if (StrUtil.isNotBlank(search)){
            wrapper.like(ScheduleExamine::getExamineName,search);
        }
        Page<ScheduleExamine> userPage = (Page<ScheduleExamine>) scheduleExamineMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
        return Result.succ(userPage);
    }
}
