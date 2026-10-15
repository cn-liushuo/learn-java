# Java

Java 学习与实战练习仓库。关键词：Java SE、算法、前端 Web、Vue、Maven、Spring Boot、MyBatis。

文档统一索引见 [docs/README.md](docs/README.md)。本文档和各模块 README 以当前源码为准，课程原始笔记保留在对应工程目录中。

当前已落地四个模块（根目录 `pom.xml` 聚合，Java 侧统一 **JDK 1.8**）：

| 顺序 | 模块 | 类型 | 一句话说明 |
|------|------|------|------------|
| 1 | [**BJavaSEBasic**](BJavaSEBasic/README.md) | Maven | Java SE 语法、面向对象、Swing 小项目 |
| 2 | [**CJavaSEPorjectMax**](CJavaSEPorjectMax/README.md) | Maven | 集合 / IO / 多线程 / 网络 / 反射与代理 |
| 3 | [**ALeetCode**](ALeetCode/README.md) | Maven | LeetCode 算法题解 |
| 4 | [**DJavaWeb**](DJavaWeb/README.md) | Maven 聚合 + 前端静态页 + Vue CLI | HTML / CSS / JS / Ajax / Vue；Maven；HTTP 与 Spring Boot Web；MySQL 基础练习；MyBatis；Tlias 智能学习辅助系统 |

> 建议按上表顺序学习：先 SE，再进阶与网络，再刷题巩固，最后进入 DJavaWeb。该模块内部顺序为前端与 Spring Boot，再 SQL，再 MyBatis，最后 Tlias。

各模块的包对照、接口明细、运行步骤见对应子目录 README，本页只做总览与导航。

---

## 目录结构

```text
learn-java/
├── pom.xml              # 聚合 A / B / C / D 四个模块
├── ALeetCode/           # 算法题解
├── BJavaSEBasic/        # Java SE 基础 + 员工系统 / 石头迷宫
├── CJavaSEPorjectMax/   # Java SE 进阶（集合 / IO / 线程 / 网络 / 反射等）
├── DJavaWeb/            # JavaWeb（前端 → Maven → HTTP → Spring Boot → SQL → MyBatis → Tlias）
├── docs/                # Markdown 统一索引与维护说明
└── README.md
```

---

## 环境要求

| 环境 | 说明 |
|------|------|
| **JDK 8** | 建议 [Eclipse Temurin 8](https://adoptium.net/zh-CN/temurin/releases?version=8&os=any&arch=any)，配置 `JAVA_HOME` |
| **Maven 3.6.1+** | 建议 [Apache Maven](https://maven.apache.org/)，配置 `MAVEN_HOME` 与 PATH |
| **IDE** | IntelliJ IDEA / Eclipse；使用 Lombok 的模块需开启注解处理 |
| **Node.js LTS** | 仅 [vue-project](DJavaWeb/vue-project/README.md) 需要 |
| **MySQL 8.x** | SQL 练习、MyBatis 两个工程、Tlias 需要。SQL 练习说明见 [static/SQL](DJavaWeb/springboot-web-req-resp/src/main/resources/static/SQL/README.md) |

主要依赖版本见各模块 README。

---

## 安装与运行（入口）

在仓库根目录执行 `mvn compile`，即可编译四个模块。也可进入对应模块目录单独编译，再运行类的 `main`（详见各模块 README）。

前端、Spring Boot、手写 HTTP Server、MySQL、MyBatis、Tlias 等详见 [DJavaWeb/README.md](DJavaWeb/README.md)。Tlias 当前接口和 OSS 配置见 [Tlias README](DJavaWeb/tlias-web-management/README.md)。

---

## 课程参考

| 模块 | 课程 |
|------|------|
| [BJavaSEBasic](BJavaSEBasic/README.md)、[CJavaSEPorjectMax](CJavaSEPorjectMax/README.md) | [黑马程序员 2024 Java 基础](https://www.bilibili.com/video/BV1gb42177hm/?spm_id_from=333.788.video.desc.click) |
| [DJavaWeb](DJavaWeb/README.md) | [黑马程序员 JavaWeb 开发教程（Spring / MyBatis / SpringMVC / SpringBoot 全流程）](https://www.bilibili.com/video/BV1m84y1w7Tb/?spm_id_from=333.788.video.desc.click) |
