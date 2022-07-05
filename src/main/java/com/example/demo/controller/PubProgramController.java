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
import java.util.concurrent.ConcurrentHashMap;

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
    //新增
    @PostMapping
    public Result save(@RequestBody PubProgram pubProgram){
        ConcurrentHashMap<String, WebSocket> webSocketMap = WebSocket.getWebSocketMap();
        for(WebSocket item :webSocketMap.values()){
            item.sendMessage("0"+pubProgram.getPubProgramImg());
        }
        return Result.succ(200,"发布成功",pubProgram);
    }
}
