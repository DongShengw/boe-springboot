package com.example.demo.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Grouping;
import com.example.demo.entity.Notice;
import com.example.demo.mapper.GroupingMapper;
import com.example.demo.mapper.NoticeMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author anonymous
 * @since 2022-06-29
 */
@RestController
@RequestMapping("/grouping")
public class GroupingController {
    @Resource
    GroupingMapper groupingMapper;
    //新增
    @PostMapping
    public Result save(@RequestBody Grouping grouping){
        groupingMapper.insert(grouping);
        return Result.succ(200,"添加成功",grouping);
    }
    //更新
    @PutMapping
    public Result update(@RequestBody Grouping grouping){
        groupingMapper.updateById(grouping);
        return Result.succ(200,"更新成功",grouping);
    }
    //删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id){
        groupingMapper.deleteById(id);
        return Result.succ(200,"删除成功",id);
    }
    //查询
    @GetMapping
    public Result findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam (defaultValue = "10") Integer pageSize,
                           @RequestParam (defaultValue = "") String name,
                           @RequestParam (defaultValue = "") String mechanism){
        LambdaQueryWrapper<Grouping> wrapper =  Wrappers.<Grouping>lambdaQuery();
        if (StrUtil.isNotBlank(name)||StrUtil.isNotBlank(mechanism)){
            wrapper.and(i->i.like(Grouping::getGroupingDescribe,name).like(Grouping::getGroupingMechanism,mechanism));
        }
        Page<Grouping> userPage = (Page<Grouping>) groupingMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
        return Result.succ(userPage);
    }
    //得到每个分组的设备数量
    @GetMapping("/group")
    public Result getGroup(){
        return  Result.succ(groupingMapper.getGroup());
    }
    //得到每个机构的设备数量
    @GetMapping("/mechanism")
    public Result getMechanism(){
        return  Result.succ(groupingMapper.getMechanism());
    }
}
