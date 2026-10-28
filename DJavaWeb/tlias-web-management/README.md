# tlias-web-management

Tlias 智能学习辅助系统后端，当前实现包含：

- 部门管理、员工分页与条件查询、员工新增 / 修改 / 批量删除
- 员工登录（JWT）与登录校验拦截器；Cookie / Session 演示
- 图片上传到阿里云 OSS
- Filter 过滤器入门、Interceptor 拦截器
- 全局异常处理
- Spring 声明式事务（解散部门 + 部门操作日志）
- AOP 记录操作日志（`@Log` + `LogAspect`）

上级模块说明见 [DJavaWeb/README.md](../README.md)，完整业务接口见 [API 接口文档](src/main/resources/API/api接口文档.md)。

## 技术栈

- JDK 8、Maven
- Spring Boot `2.6.13`（含 `spring-boot-starter-aop`）
- MyBatis Spring Boot Starter `2.2.2`
- PageHelper `1.4.7`
- MySQL Connector/J
- 阿里云 OSS SDK `3.18.4`
- JWT：jjwt `0.9.1`（`JwtUtils`，登录成功后下发令牌）
- fastjson `1.2.84`（拦截器手动返回 JSON、AOP 序列化返回值）
- Nacos 服务注册发现：`spring-cloud-starter-alibaba-nacos-discovery` `2021.0.5.0`
- Lombok

## 环境准备

1. 创建 MySQL 8.x 数据库，并执行 [tlias.sql](src/main/resources/SQL/tlias.sql)。脚本会创建 `tlias` 数据库，以及 `dept` 部门表、`emp` 员工表、`dept_log` 部门操作日志表、`operate_log` 操作日志表。
2. 检查 [application.yml](src/main/resources/application.yml) 中的数据库连接配置。
3. 图片上传需要设置以下环境变量，代码不会从 Markdown 或源码中读取凭据：

   - `OSS_ACCESS_KEY_ID`
   - `OSS_ACCESS_KEY_SECRET`

   `application.yml` 中的 `aliyun.oss.endpoint`、`aliyun.oss.bucketName` 和 `aliyun.oss.region` 需要改成自己的 OSS 信息。
4. `application.yml` 配置了 Nacos 注册中心地址 `127.0.0.1:8848`。本地未启动 Nacos 时，需要先启动 Nacos，或移除 Nacos 依赖与配置。

## 启动

```bash
cd DJavaWeb/tlias-web-management
mvn spring-boot:run
```

后端默认使用 `8080` 端口，启动类为 `com.example.TliasWebManagementApplication`（含 `@ServletComponentScan` 用于扫描 `@WebFilter`，`@EnableDiscoveryClient` 用于注册到 Nacos）。仓库中还保留了构建后的前端资源，入口位于 `src/main/resources/FE/nginx-1.22.0-tlias/html/`；前端通过 Nginx 将 `/api` 请求代理到后端。

## 当前接口

以下路径以当前 Controller 源码为准。

| 资源 | 方法 | 路径 | 用途 |
|------|------|------|------|
| 部门 | GET | `/depts` | 查询全部部门 |
| 部门 | POST | `/depts` | 新增部门 |
| 部门 | GET | `/depts/{id}` | 查询单个部门 |
| 部门 | PUT | `/depts` | 修改部门 |
| 部门 | DELETE | `/depts/{id}` | 删除部门 |
| 员工 | GET | `/emps` | 分页与条件查询 |
| 员工 | POST | `/emps` | 新增员工 |
| 员工 | GET | `/emps/{id}` | 查询单个员工 |
| 员工 | PUT | `/emps` | 修改员工 |
| 员工 | DELETE | `/emps/{ids}` | 按逗号分隔 ID 批量删除 |
| 登录 | POST | `/login` | 员工登录，成功返回 JWT 字符串 |
| 演示 | GET | `/c1`、`/c2` | Cookie 写入 / 读取演示 |
| 演示 | GET | `/s1`、`/s2` | HttpSession 写入 / 读取演示 |
| 文件 | POST | `/upload` | 以 `multipart/form-data` 的 `image` 字段上传图片 |

员工列表支持 `page`、`pageSize`、`name`、`gender`、`begin`、`end` 查询参数，其中日期格式为 `yyyy-MM-dd`。所有接口返回统一的 `Result` 结构：`code` 为 `1` 表示成功，`0` 表示失败。

除 `/login` 外，所有请求都要在请求头 `token` 中携带登录返回的 JWT，否则拦截器返回 `{"code":0,"msg":"NOT_LOGIN"}`，详见 [interceptor/README.md](src/main/java/com/example/interceptor/README.md)。

## 横切功能

| 功能 | 实现 | 说明 |
|------|------|------|
| 登录校验 | `interceptor/LoginCheckInterceptor` + `config/WebConfig` | 拦截 `/**`，放行 `/login`；Filter 版 `LoginCheckFilter` 已注释关闭 |
| 全局异常处理 | `exception/GlobalExceptionHandler` | `@RestControllerAdvice` 捕获所有 `Exception`，返回 `Result.error("对不起，操作失败，请联系管理员")` |
| 声明式事务 | `service/impl/DeptServiceImpl.delete` | 解散部门时删除部门与员工，日志用 `REQUIRES_NEW` 独立事务 |
| 操作日志 | `anno/Log` + `aop/LogAspect` | `@Around("@annotation(com.example.anno.Log)")` |

操作日志：部门、员工的新增 / 修改 / 删除 6 个接口加了 `@Log`。`LogAspect` 从请求头 `token` 解析操作人 ID，记录类名、方法名、参数、返回值和耗时，写入 `operate_log` 表。AOP 基础知识见 [aop/README.md](../springboot-aop-quickstart/src/main/java/com/itheima/aop/README.md)。

> 说明：若 [API 接口文档](src/main/resources/API/api接口文档.md) 与上表冲突，以本 README 与 Controller 源码为准（例如文档中仍写「未实现登录」时，以 `LoginController` 的 `POST /login` 为准）。

## 代码结构

```text
src/main/java/com/example/
├── anno/         # 自定义注解 @Log
├── aop/          # LogAspect：AOP 记录操作日志
├── config/       # WebConfig：注册拦截器
├── controller/   # HTTP 接口（部门 / 员工 / 登录 / Session / 上传）
├── exception/    # GlobalExceptionHandler：全局异常处理
├── Filter/       # Servlet Filter 入门（AbcFilter、DemoFilter、LoginCheckFilter）
├── interceptor/  # LoginCheckInterceptor：登录校验拦截器
├── mapper/       # MyBatis Mapper（含 DeptLogMapper、OperateLogMapper）
├── pojo/         # Dept、DeptLog、Emp、OperateLog、PageBean、Result
├── service/      # 业务逻辑、分页与事务示例（解散部门）
└── utils/        # OSS、JWT 等工具
```

MyBatis XML 位于 `src/main/resources/com/example/mapper/EmpMapper.xml`，数据库脚本位于 `src/main/resources/SQL/tlias.sql`（含 `dept_log`、`operate_log`）。

| 目录 | 笔记 |
|------|------|
| `Filter/` | [Filter/README.md](src/main/java/com/example/Filter/README.md) |
| `interceptor/` | [interceptor/README.md](src/main/java/com/example/interceptor/README.md) |
| `config/` | [config/README.md](src/main/java/com/example/config/README.md) |
| `service/` | [service/README.md](src/main/java/com/example/service/README.md)（事务） |

---

## 相关链接

- 上级模块：[DJavaWeb/README.md](../README.md)
- Filter 笔记：[Filter/README.md](src/main/java/com/example/Filter/README.md)
- 拦截器笔记：[interceptor/README.md](src/main/java/com/example/interceptor/README.md)
- 配置类笔记：[config/README.md](src/main/java/com/example/config/README.md)
- 事务笔记：[service/README.md](src/main/java/com/example/service/README.md)
- AOP 入门：[aop/README.md](../springboot-aop-quickstart/src/main/java/com/itheima/aop/README.md)
- 接口文档：[api接口文档.md](src/main/resources/API/api接口文档.md)
- 前端工程：[vue-project/README.md](../vue-project/README.md)
- 全仓库文档索引：[docs/README.md](../../docs/README.md)
