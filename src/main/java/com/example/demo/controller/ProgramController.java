package com.example.demo.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Img;
import com.example.demo.entity.Program;
import com.example.demo.entity.User;
import com.example.demo.mapper.ProgramMapper;
import com.example.demo.mapper.UserMapper;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author anonymous
 * @since 2022-06-24
 */
@RestController
@RequestMapping("/program")
public class ProgramController {
    @Resource
    ProgramMapper programMapper;
    //新增
    @PostMapping
    public Result save(String program_name,String resolving_power,String program_time,String program_size,Integer program_state,String program_author,MultipartFile file, HttpServletRequest request)throws FileNotFoundException {
        Program program=new Program();
        program.setProgramUpdate(LocalDateTime.now());
        program.setProgramAuthor(program_author);
        program.setProgramName(program_name);
        program.setProgramState(program_state);
        program.setProgramAuthor(program_author);
        program.setProgramTime(program_time);
        program.setProgramSize(program_size);
        program.setResolvingPower(resolving_power);
        SimpleDateFormat dateFormat = new SimpleDateFormat("/yyyy-MM-DD/");
        String filename = file.getOriginalFilename();//取得文件名称
        if (!filename.endsWith(".png") && !filename.endsWith(".jpg")) {
            return Result.fail("文件类型不对");
        }

        String format = dateFormat.format(new Date(System.currentTimeMillis()));
        //realPath为文件保存的位置
        String realPath = ResourceUtils.getURL("classpath:").getPath() + "static/img" + format;
        realPath = realPath.substring(1);//去掉第一个/符号
        File folder = new File(realPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String newName;
        if (filename.endsWith(".png"))
            newName = UUID.randomUUID().toString() + ".png";
        else
            newName = UUID.randomUUID().toString() + ".jpg";
        try {
            //新建文件
            file.transferTo(new File(folder, newName));
            //String url = realPath + newName;
        } catch (IOException e) {
            return Result.fail(e.getMessage());
        }

        //返回可供外界访问的url
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/img" + format + newName;
        program.setProgramImg(url);
        programMapper.insert(program);
        return Result.succ(200,"新增成功",program);
    }
    //更新
    @PutMapping
    public Result update(@RequestBody Program program){
        programMapper.updateById(program);
        return Result.succ(200,"更新成功",program);
    }
    //重命名
    @PutMapping("/rename")
    public Result reName(@RequestBody  Program program){
        programMapper.reName(program.getProgramName(),program.getProgramId());
        return Result.succ(200,"更新成功",program);
    }
    //删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id){
        programMapper.deleteById(id);
        return Result.succ(200,"删除成功",id);
    }
    //查询
    @GetMapping
    public Result findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam (defaultValue = "10") Integer pageSize,
                           @RequestParam (defaultValue = "") String name,
                           @RequestParam (defaultValue = "") String resolving,
                           @RequestParam (defaultValue = "") String state){
        LambdaQueryWrapper<Program> wrapper =  Wrappers.<Program>lambdaQuery();
        if (StrUtil.isNotBlank(name)||StrUtil.isNotBlank(resolving)||StrUtil.isNotBlank(state)){
            wrapper.and(i->i.like(Program::getProgramName,name).like(Program::getResolvingPower,resolving).like(Program::getProgramState,state));
        }
        Page<Program> userPage = (Page<Program>) programMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
        return Result.succ(userPage);
    }
    //得到节目数量
    @GetMapping("/sum")
    public Result getSum(){
        return Result.succ(programMapper.getSum());
    }
}
