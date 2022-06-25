package com.example.demo.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.User;
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
@RequestMapping("/user")
public class UserController {
    @Resource
    UserMapper userMapper;
    //新增
    @PostMapping
    public Result save(@RequestBody User user){
        if(user.getUserPassword() == null){
            user.setUserPassword("123456");
        }
        userMapper.insert(user);

        return Result.succ(0,"注册成功",user);
    }
    //更新
    @PutMapping
    public Result update(@RequestBody User user){
        userMapper.updateById(user);
        return Result.succ(0,"更新成功",user);
    }
    //删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id){
        userMapper.deleteById(id);
        return Result.succ(0,"删除成功",id);
    }
    //查询
    @GetMapping
    public Result findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam (defaultValue = "10") Integer pageSize,
                              @RequestParam (defaultValue = "") String search){
        LambdaQueryWrapper<User> wrapper =  Wrappers.<User>lambdaQuery();
        if (StrUtil.isNotBlank(search)){
            wrapper.like(User::getUserName,search);
        }
        Page<User> userPage = (Page<User>) userMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
        return Result.succ(userPage);
    }
    //注册
    @PostMapping("/register")
    public Result register(@RequestBody User user){
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUserName,user.getUserName()));
        if(res != null){
            return Result.fail(-1,"用户名重复！",null);
        }
        if(user.getUserPassword()==null){
            user.setUserPassword("123456");
        }
        userMapper.insert(user);
        return Result.succ(0,"注册成功",user);
    }
}
