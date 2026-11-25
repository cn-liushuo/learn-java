package com.sky.controller.common;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.LocalImagePathUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

/**
 * 通用接口
 */
@RestController
@RequestMapping("/admin/common")
@Api(tags = "通用接口")
@Slf4j
public class CommonController {

    /**
     * 浏览器可访问的图片 URL 前缀
     */
    private static final String IMAGE_URL_PREFIX = "http://localhost:8080/image/";

    /**
     * 文件上传
     *
     * @param file 上传文件
     * @return 可访问的图片 URL
     */
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {
        log.info("文件上传, {}", file);

        try {
            // 原始文件名
            String originalFilename = file.getOriginalFilename();
            // 截取原始文件名的后缀  abc.png
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            // 构造新文件名称(不能重复) - uuid(通用唯一识别码)
            String objectName = UUID.randomUUID().toString() + extension;
            log.info("新的文件名: {}", objectName);

            // 本地开发：按系统选择目录并自动创建后写入磁盘
            String imageDir = LocalImagePathUtil.getImageDir();
            file.transferTo(new File(imageDir + objectName));

            // 返回浏览器可访问的 HTTP 地址（由静态资源映射提供）
            // 生产环境可改回：aliOssUtil.upload(file.getBytes(), objectName)
            String fileUrl = IMAGE_URL_PREFIX + objectName;
            return Result.success(fileUrl);
        } catch (Exception e) {
            log.error("文件上传失败", e);
        }

        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
