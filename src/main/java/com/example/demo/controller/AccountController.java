package com.example.demo.controller;


import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.common.LoginDto;
import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.util.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class AccountController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    JwtUtils jwtUtils;

    @RequestMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response){
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("user_name", loginDto.getUsername()));
        Assert.notNull(user,"用户不存在");//断言拦截
        //判断账号密码是否错误 因为是md5加密所以这里md5判断
        if(!SecureUtil.md5(user.getUserPassword()).equals(SecureUtil.md5(loginDto.getPassword()))){
            //密码不同则抛出异常
            return Result.fail("密码不正确");
        }
        String jwt = jwtUtils.generateToken(user.getUserId());
        System.out.println(user.getUserName());
        //将token 放在我们的header里面
        response.setHeader("Authorization",jwt);
        response.setHeader("Access-control-Expose-Headers","Authorization");

        return Result.succ(MapUtil.builder()
                .put("id",user.getUserId())
                .put("username",user.getUserName())
                .put("avatar",user.getUserPhone())
                .put("email",user.getUserEmail()).map()
        );
    }

    //需要认证权限才能退出登录
    @RequiresAuthentication
    @RequestMapping("/logout")
    public Result logout() {
        //退出登录
        SecurityUtils.getSubject().logout();
        return Result.succ("退出登录成功");
    }

}

