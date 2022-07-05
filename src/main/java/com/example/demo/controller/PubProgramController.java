package com.example.demo.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.PubNotice;
import com.example.demo.entity.PubProgram;
import com.example.demo.mapper.PubNoticeMapper;
import com.example.demo.mapper.PubProgramMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author anonymous
 * @since 2022-07-05
 */
@RestController
@RequestMapping("/pub-program")
public class PubProgramController {
    @Resource
    PubProgramMapper programMapper;
    //新增
    @PostMapping
    public Result save(@RequestBody PubProgram pubProgram){
        programMapper.insert(pubProgram);
        return Result.succ(200,"发布成功",pubProgram);
    }
    //更新
    @PutMapping
    public Result update(@RequestBody PubProgram pubProgram){
        programMapper.updateById(pubProgram);
        return Result.succ(200,"更新成功",pubProgram);
    }
    //删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id){
        programMapper.deleteById(id);
        return Result.succ(200,"删除成功",id);
    }
    //查询
    @GetMapping
    public Object findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam (defaultValue = "10") Integer pageSize,
                           @RequestParam (defaultValue = "") String search){
        LambdaQueryWrapper<PubProgram> wrapper =  Wrappers.<PubProgram>lambdaQuery();
        if (StrUtil.isNotBlank(search)){
            wrapper.like(PubProgram::getPubProgramId,search);
        }
        Page<PubProgram> userPage = (Page<PubProgram>) programMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
        return userPage;
    }
}
