package com.example.demo.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Program;
import com.example.demo.entity.ScheduleList;
import com.example.demo.mapper.ProgramMapper;
import com.example.demo.mapper.ScheduleListMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author anonymous
 * @since 2022-06-24
 */
@RestController
@RequestMapping("/schedule-list")
public class ScheduleListController {
    @Resource
    ScheduleListMapper scheduleListMapper;
    //新增
    @PostMapping
    public Result save(@RequestBody ScheduleList scheduleList){
        scheduleList.setListUpdate(LocalDateTime.now());
        scheduleListMapper.insert(scheduleList);
        return Result.succ(200,"新增成功",scheduleList);
    }
    //更新
    @PutMapping
    public Result update(@RequestBody ScheduleList scheduleList){
        scheduleList.setListUpdate(LocalDateTime.now());
        scheduleListMapper.updateById(scheduleList);
        return Result.succ(200,"更新成功",scheduleList);
    }
    //删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id){
        scheduleListMapper.deleteById(id);
        return Result.succ(200,"删除成功",id);
    }
    //查询
    @GetMapping
    public Result findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam (defaultValue = "10") Integer pageSize,
                           @RequestParam (defaultValue = "") String name,
                           @RequestParam (defaultValue = "") String state){
        LambdaQueryWrapper<ScheduleList> wrapper =  Wrappers.<ScheduleList>lambdaQuery();
        if (StrUtil.isNotBlank(name)||StrUtil.isNotBlank(state)){
            wrapper.and(i->i.like(ScheduleList::getListName,name).like(ScheduleList::getListState,state));
        }
        Page<ScheduleList> userPage = (Page<ScheduleList>) scheduleListMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
        return Result.succ(userPage);
    }
    //计划总数
    @GetMapping("/sum")
    public Result getSum(){
        return Result.succ(scheduleListMapper.getSum());
    }

    //前3条计划
    @GetMapping("/schedule")
    public Result getSchedule(){
        return Result.succ(scheduleListMapper.getSchedule());
    }
}
