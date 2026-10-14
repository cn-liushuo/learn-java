package com.example.controller;

import com.example.pojo.Result;
import com.example.utils.AliOSSUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
public class UploadController {

    @Autowired
    private AliOSSUtils aliOSSUtils;

    // 本地存储文件
    /* @PostMapping("/upload")
    public Result update(String username, Integer age, @RequestParam("image") MultipartFile image) throws Exception {
        log.info("文件上传: {}, {}, {}", username, age, image);
        // 获取原始文件名 - 1.jpg 123.0.0.jpg
        String originalFilename = image.getOriginalFilename();

        // 构造唯一的文件名(不能重复) - uuid(通用唯一识别码) a86ed6cf-428b-4982-94db-1b11aa248b09
        int index = originalFilename.lastIndexOf(".");
        String extname = originalFilename.substring(index);
        String newFileName = UUID.randomUUID().toString() + extname;
        log.info("新的文件名：{}", newFileName);

        // 将文件存储在服务器的磁盘目录中 D:\dev\images
        image.transferTo(new File("D:\\dev\\images\\" + newFileName));

        return Result.success();
    } */

    @PostMapping("/upload")
    public Result upload(MultipartFile image) throws Exception {
        log.info("文件上传，文件名：{}", image.getOriginalFilename());
        // 调用阿里云OSS工具类进行文件上传
        String url = aliOSSUtils.upload(image);
        log.info("文件上传完成, 文件访问的url: {}", url);

        return Result.success(url);
    }
}
