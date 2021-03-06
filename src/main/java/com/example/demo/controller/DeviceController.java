package com.example.demo.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Device;
import com.example.demo.entity.ScheduleList;
import com.example.demo.mapper.DeviceMapper;
import com.example.demo.mapper.ScheduleListMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author anonymous
 * @since 2022-06-28
 */
@RestController
@RequestMapping("/device")
public class DeviceController {
    @Resource
    DeviceMapper deviceMapper;
    //新增
    @PostMapping
    public Result save(@RequestBody Device device){
        deviceMapper.insert(device);

        return Result.succ(200,"新增成功",device);
    }
    //更新
    @PutMapping
    public Result update(@RequestBody Device device){
        deviceMapper.updateDevice(device.getDeviceName(),device.getDeviceGroup(),device.getDeviceId());
        return Result.succ(200,"更新成功",device);
    }
    //删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id){
        deviceMapper.deleteSchedule(id);
        return Result.succ(200,"删除成功",id);
    }
    //查询
    @GetMapping
    public Result findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam (defaultValue = "10") Integer pageSize,
                           @RequestParam (defaultValue = "") String name,
                           @RequestParam (defaultValue = "") String mechanism,
                           @RequestParam (defaultValue = "") String group,
                           @RequestParam (defaultValue = "") String mac,
                           @RequestParam (defaultValue = "") String resolving,
                           @RequestParam (defaultValue = "") String state,
                           @RequestParam (defaultValue = "") String schedule,
                           @RequestParam (defaultValue = "") String system){
        LambdaQueryWrapper<Device> wrapper =  Wrappers.<Device>lambdaQuery();
        if (StrUtil.isNotBlank(name)||StrUtil.isNotBlank(mechanism)||StrUtil.isNotBlank(group)||StrUtil.isNotBlank(mac)||StrUtil.isNotBlank(resolving)||
                StrUtil.isNotBlank(state)||StrUtil.isNotBlank(schedule)||StrUtil.isNotBlank(system)){
//            wrapper.like(Device::getDeviceName,name).like(Device::getDeviceMechanism,mechanism).like(Device::getDeviceGroup,group)
//                    .like(Device::getMacAddress,mac).like(Device::getResolvingPower,resolving).like(Device::getDeviceState,state)
//                    .like(Device::getDeviceSchedule,schedule).like(Device::getDeviceSystem,system);
            wrapper.and(i->i.like(Device::getDeviceName,name).like(Device::getDeviceMechanism,mechanism).like(Device::getDeviceGroup,group)
                    .like(Device::getMacAddress,mac).like(Device::getResolvingPower,resolving).like(Device::getDeviceState,state)
                    .like(Device::getDeviceSchedule,schedule).like(Device::getDeviceSystem,system));
        }
        Page<Device> userPage = (Page<Device>) deviceMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
        return Result.succ(userPage);
    }
    //得到计划列表
    @GetMapping("/schedule")
    public Result getSchedule(){
        return Result.succ(deviceMapper.querySchedule());
    }

    //得到分组
    @GetMapping("/group")
    public Result getGroup (){
        return Result.succ(deviceMapper.queryGroup());
    }

    //得到设备数量
    @GetMapping("/sum")
    public Result getSum(){
        return Result.succ(deviceMapper.getSum());
    }

    //得到不同状态设备数量
    @GetMapping("/statesum")
    public Result getStatesum(){
        HashMap<Integer,Integer> map=new HashMap<Integer, Integer>();
        map.put(0,deviceMapper.getOffline());
        map.put(1,deviceMapper.getFree());
        map.put(2,deviceMapper.getPlay());
        return Result.succ(map);
    }
}
