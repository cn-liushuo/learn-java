package com.sky.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * 本地图片存储路径工具类
 * <p>
 * Windows 优先使用 D 盘，不可用时回退到 C 盘；macOS / Linux 使用用户主目录。
 * 目录不存在时自动创建。
 */
@Slf4j
public final class LocalImagePathUtil {

    /**
     * 相对业务目录
     */
    private static final String RELATIVE_DIR = "nutritionCloudSync/development/image";

    /**
     * 已解析并创建完成的目录缓存（以 / 结尾）
     */
    private static volatile String cachedImageDir;

    /**
     * 工具类禁止实例化
     */
    private LocalImagePathUtil() {
    }

    /**
     * 获取本地图片目录绝对路径（以 / 结尾），目录不存在时自动创建
     *
     * @return 本地图片目录路径
     */
    public static String getImageDir() {
        if (cachedImageDir == null) {
            synchronized (LocalImagePathUtil.class) {
                if (cachedImageDir == null) {
                    cachedImageDir = resolveAndCreate();
                }
            }
        }
        return cachedImageDir;
    }

    /**
     * 解析路径并创建目录
     *
     * @return 规范化后的目录路径（以 / 结尾）
     */
    private static String resolveAndCreate() {
        File dir = new File(resolveBaseDir(), RELATIVE_DIR);
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IllegalStateException("无法创建本地图片目录: " + dir.getAbsolutePath());
        }
        String path = dir.getAbsolutePath().replace('\\', '/');
        if (!path.endsWith("/")) {
            path = path + "/";
        }
        log.info("本地图片目录: {}", path);
        return path;
    }

    /**
     * 按操作系统选择根目录：Windows 优先 D 盘，否则 C 盘；其他系统使用用户主目录
     *
     * @return 根目录路径
     */
    private static String resolveBaseDir() {
        String os = System.getProperty("os.name", "").toLowerCase();
        if (os.contains("win")) {
            File dDrive = new File("D:/");
            if (dDrive.exists()) {
                return "D:/";
            }
            return "C:/";
        }
        // macOS、Linux 等使用用户主目录
        return System.getProperty("user.home");
    }
}
