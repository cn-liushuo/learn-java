package com.example.controller;

import com.example.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Slf4j
@RestController
public class UploadController {

    @PostMapping("/upload")
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
    }
}
