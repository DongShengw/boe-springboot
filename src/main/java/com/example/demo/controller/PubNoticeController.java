package com.example.demo.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Notice;
import com.example.demo.entity.PubNotice;
import com.example.demo.mapper.NoticeMapper;
import com.example.demo.mapper.PubNoticeMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author anonymous
 * @since 2022-07-03
 */
@RestController
@RequestMapping("/pub-notice")
public class PubNoticeController {
    @Resource
    PubNoticeMapper pubNoticeMapper;
    //新增
    @PostMapping
    public Result save(@RequestBody PubNotice pubNotice){
        pubNoticeMapper.insert(pubNotice);
        return Result.succ(200,"添加成功",pubNotice);
    }
    //更新
    @PutMapping
    public Result update(@RequestBody PubNotice pubNotice){
        pubNoticeMapper.updateById(pubNotice);
        return Result.succ(200,"更新成功",pubNotice);
    }
    //删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id){
        pubNoticeMapper.deleteById(id);
        return Result.succ(200,"删除成功",id);
    }
    //查询
    @GetMapping
    public Result findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam (defaultValue = "10") Integer pageSize,
                           @RequestParam (defaultValue = "") String search){
        LambdaQueryWrapper<PubNotice> wrapper =  Wrappers.<PubNotice>lambdaQuery();
        if (StrUtil.isNotBlank(search)){
            wrapper.like(PubNotice::getPubNoticeContent,search);
        }
        Page<PubNotice> userPage = (Page<PubNotice>) pubNoticeMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
        return Result.succ(userPage);
    }
}
