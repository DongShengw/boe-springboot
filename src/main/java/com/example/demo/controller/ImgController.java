package com.example.demo.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Img;
import com.example.demo.entity.Program;
import com.example.demo.mapper.ImgMapper;
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
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author anonymous
 * @since 2022-06-30
 */
@RestController
@RequestMapping("/img")
public class ImgController {
    @Resource
    ImgMapper imgMapper;

    @PostMapping("/UploadPic")
    public Result UploadPic(MultipartFile file, HttpServletRequest request) throws FileNotFoundException {

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

        Img img = new Img();
        img.setImgUrl(url);
        img.setImgName("text");
        imgMapper.insert(img);
        return Result.succ(200,"图片上传成功", url);
    }

    //查询
    @GetMapping
    public Result findPage(@RequestParam (defaultValue = "") String name){
        LambdaQueryWrapper<Img> wrapper =  Wrappers.<Img>lambdaQuery();
        if (StrUtil.isNotBlank(name)){
            wrapper.and(i->i.like(Img::getImgName,name));
        }
        Page<Img> userPage = (Page<Img>) imgMapper.selectList(wrapper);
        return Result.succ(userPage);
    }
}
