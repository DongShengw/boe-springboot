package com.example.demo.controller;


import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
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
import java.util.concurrent.ConcurrentHashMap;

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
    //新增
    @PostMapping
    public Result save(@RequestBody PubNotice pubNotice){

        ConcurrentHashMap<String, WebSocket> webSocketMap = WebSocket.getWebSocketMap();
        for(WebSocket item :webSocketMap.values()){
            item.sendMessage("1"+JSON.toJSONString(pubNotice));
        }
//        System.out.println(JSON.toJSONString(pubNotice));
        return Result.succ(200,"发布成功",pubNotice);
    }
}
