package com.sky.controller.common;

import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 通用接口
 */
@RestController
@RequestMapping("/admin/common")
@Api(tags = "通用接口")
@Slf4j
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {
        log.info("文件上传, {}", file);

        // 本地开发用这个
        /* String path = "D:\\nutritionCloudSync\\development\\image\\" + newFileName;

        // 将文件存储在本地的磁盘目录中
        file.transferTo(new File(path)); */

        // 阿里云OSS
        try {
            // 原始文件名
            String originalFilename = file.getOriginalFilename();
            // 截取原始文件名的后缀  abc.png
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            // 构造新文件名称(不能重复) - uuid(通用唯一识别码)
            String objectName = UUID.randomUUID().toString() + extension;
            log.info("新的文件名: {}", objectName);

            // 文件的请求路径
            String filePath = aliOssUtil.upload(file.getBytes(), objectName);
            return Result.success(filePath);
        } catch (Exception e) {
            log.error("文件上传失败, {}", e);
        }

        return null;
    }
}
