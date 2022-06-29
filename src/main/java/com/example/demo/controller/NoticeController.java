package com.example.demo.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Notice;
import com.example.demo.entity.User;
import com.example.demo.mapper.NoticeMapper;
import com.example.demo.mapper.UserMapper;
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
@RequestMapping("/notice")
public class NoticeController {
    @Resource
    NoticeMapper noticeMapper;
    //新增
    @PostMapping
    public Result save(@RequestBody Notice notice){
        notice.setNoticeStart(LocalDateTime.now());
        noticeMapper.insert(notice);
        return Result.succ(200,"添加成功",notice);
    }
    //更新
    @PutMapping
    public Result update(@RequestBody Notice notice){
        noticeMapper.updateById(notice);
        return Result.succ(200,"更新成功",notice);
    }
    //删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id){
        noticeMapper.deleteById(id);
        return Result.succ(200,"删除成功",id);
    }
    //查询
    @GetMapping
    public Result findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam (defaultValue = "10") Integer pageSize,
                           @RequestParam (defaultValue = "") String search){
        LambdaQueryWrapper<Notice> wrapper =  Wrappers.<Notice>lambdaQuery();
        if (StrUtil.isNotBlank(search)){
            wrapper.like(Notice::getNoticeContent,search);
        }
        Page<Notice> userPage = (Page<Notice>) noticeMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
        return Result.succ(userPage);
    }

}
