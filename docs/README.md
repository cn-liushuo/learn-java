# learn-java 文档索引

本文档索引按当前仓库结构整理，维护基线为 **2026-10-30**。代码目录中的 README 负责说明模块如何运行；课程笔记保留在对应工程附近，便于边看源码边对照。

## 从这里开始

| 文档 | 内容 |
|------|------|
| [仓库总览](../README.md) | 四个顶层模块、环境要求和总体学习顺序 |
| [BJavaSEBasic](../BJavaSEBasic/README.md) | Java SE 基础、Swing、员工管理系统、石头迷宫 |
| [CJavaSEPorjectMax](../CJavaSEPorjectMax/README.md) | 集合、IO、多线程、网络、反射、注解、代理 |
| [ALeetCode](../ALeetCode/README.md) | LeetCode 题解练习 |
| [DJavaWeb](../DJavaWeb/README.md) | 前端、Maven、HTTP、Spring Boot、MyBatis、Tlias、AOP、SpringBoot 原理、自定义 Starter、Maven 分模块与私服 |

## DJavaWeb 项目文档

| 文档 | 内容 |
|------|------|
| [Vue 前端](../DJavaWeb/vue-project/README.md) | Vue 2、Element UI、部门与员工页面 |
| [Tlias 后端](../DJavaWeb/tlias-web-management/README.md) | 当前后端实现、数据库、OSS / Nacos 配置、登录校验、全局异常、AOP 操作日志和启动方式 |
| [Tlias Filter](../DJavaWeb/tlias-web-management/src/main/java/com/example/Filter/README.md) | Servlet Filter 入门：执行流程、拦截路径、过滤器链与运行观察 |
| [Tlias Interceptor](../DJavaWeb/tlias-web-management/src/main/java/com/example/interceptor/README.md) | Spring MVC 拦截器：JWT 登录校验、执行流程、与 Filter 的区别 |
| [Tlias Config](../DJavaWeb/tlias-web-management/src/main/java/com/example/config/README.md) | `WebConfig` 注册拦截器与路径规则 |
| [Tlias Transaction](../DJavaWeb/tlias-web-management/src/main/java/com/example/service/README.md) | Spring 声明式事务：解散部门、`REQUIRES_NEW` 操作日志与回滚观察 |
| [Tlias 分模块](../DJavaWeb/tlias-web-management-maven-module-splitting/README.md) | `tlias-parent` / `tlias-pojo` / `tlias-utils` 拆分结构、统一版本管理、构建顺序、Nexus 私服发布；端口 `8082` |
| [AOP 入门](../DJavaWeb/springboot-aop-quickstart/src/main/java/com/itheima/aop/README.md) | 通知类型、通知顺序、切入点表达式、连接点 |
| [配置优先级](../DJavaWeb/springboot-web-config/src/main/resources/README.md) | `properties` / `yml` / `yaml`、Java 系统属性与命令行参数 |
| [Bean 管理与 SpringBoot 原理](../DJavaWeb/springboot-web-config2/src/main/resources/README.md) | 获取 Bean、作用域、第三方 Bean、起步依赖、自动配置 |
| [自定义 Starter](../DJavaWeb/README.md#子模块--目录对照) | `aliyun-oss-spring-boot-autoconfigure` / `-starter` 与 `springboot-autoconfiguration-test`（无独立 README，说明在 DJavaWeb README） |
| [Tlias API](../DJavaWeb/tlias-web-management/src/main/resources/API/api接口文档.md) | 部门、员工、上传等接口说明；与源码冲突时以 Controller / Tlias README 为准 |
| [MySQL 练习](../DJavaWeb/springboot-web-req-resp/src/main/resources/static/SQL/README.md) | SQL 文件说明与执行顺序 |
| [局域网即时通讯](../CJavaSEPorjectMax/src/main/java/org/example/e_net/i_chat/README.md) | Swing + TCP Socket 聊天室设计与运行 |

## 课程笔记

课程原始笔记较长，仍放在对应工程的资源目录中；下表只提供统一入口。

### 前端

- [Web 开发介绍](../DJavaWeb/HTML/Web开发介绍.md)
- [HTML / CSS](../DJavaWeb/HTML/day01-HTML-CSS.md)
- [JavaScript / Vue 指令](../DJavaWeb/JS/day02-JavaScript-Vue.md)
- [Ajax / Vue / Element UI](../DJavaWeb/vue-project/day03_Vue_Element.md)

### Java Web

- [Maven](../DJavaWeb/maven-project01/src/main/resources/01.%20Maven/Maven.md)
- [Spring Boot Web 入门](../DJavaWeb/springboot-web-quickstart/src/main/resources/static/02.%20SpringBootWeb%E5%85%A5%E9%97%A8/SpringBootWeb%E5%85%A5%E9%97%A8.md)
- [Spring Boot 请求与响应](../DJavaWeb/springboot-web-req-resp/src/main/resources/static/SpringBootWeb%E8%AF%B7%E6%B1%82%E5%93%8D%E5%BA%94.md)

### SQL

- [MySQL 第一部分](../DJavaWeb/springboot-web-req-resp/src/main/resources/static/SQL/%E6%95%B0%E6%8D%AE%E5%BA%93-MySQL-01.md)
- [MySQL 第二部分](../DJavaWeb/springboot-web-req-resp/src/main/resources/static/SQL/%E6%95%B0%E6%8D%AE%E5%BA%93-MySQL-02.md)
- [MySQL 多表查询](../DJavaWeb/springboot-web-req-resp/src/main/resources/static/SQL/%E6%95%B0%E6%8D%AE%E5%BA%93-MySQL-04.md)

## 自动生成文档

以下文件由 Spring Initializr 生成，内容只是通用参考，不作为项目运行说明；项目级运行方式以各模块 README 为准。

- [springboot-web-config/HELP.md](../DJavaWeb/springboot-web-config/HELP.md)

## 维护约定

1. 模块新增或删除工程时，先更新对应模块 README，再更新本索引。
2. 启动类、端口、接口和配置以源码为准；课程笔记中的历史示例不覆盖项目 README 的当前说明。
3. 配置示例不得提交真实密码、AccessKey 或其他凭据。需要本地运行时，使用环境变量或未纳入版本控制的配置文件。
