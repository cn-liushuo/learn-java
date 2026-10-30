# tlias-web-management-maven-module-splitting

Tlias 智能学习辅助系统后端（Maven 分模块设计与开发练习版），由 [tlias-web-management](../tlias-web-management/README.md) 复制而来：实体类拆到 `tlias-pojo`，工具类拆到 `tlias-utils`，依赖版本由父工程 `tlias-parent` 统一管理。业务功能与原项目一致：

- 部门管理、员工分页与条件查询、员工新增 / 修改 / 批量删除
- 员工登录（JWT）与登录校验拦截器；Cookie / Session 演示
- 图片上传到阿里云 OSS
- Filter 过滤器入门、Interceptor 拦截器
- 全局异常处理
- Spring 声明式事务（解散部门 + 部门操作日志）
- AOP 记录操作日志（`@Log` + `LogAspect`）

上级模块说明见 [DJavaWeb/README.md](../README.md)，完整业务接口见 [API 接口文档](src/main/resources/API/api接口文档.md)。

## 模块拆分

```text
DJavaWeb/                                     # 聚合父工程，继承 spring-boot-starter-parent 2.6.13
└── tlias-parent/                             # Tlias 父工程（packaging=pom）：聚合下面 3 个模块
    ├── ../tlias-pojo/                        # 实体类
    ├── ../tlias-utils/                       # 工具类
    └── ../tlias-web-management-maven-module-splitting/   # Web 工程（本目录）
```

| 模块 | 坐标 | 内容 |
|------|------|------|
| [`tlias-parent`](../tlias-parent/pom.xml) | `com.example:tlias-parent:1.0-SNAPSHOT` | 父工程 + 聚合：`dependencyManagement` 统一管理 `spring-boot-starter-web`、`aliyun-sdk-oss`、`jjwt`、`spring-boot-configuration-processor` 版本；所有子模块共用 Lombok；`distributionManagement` 指向私服 Nexus |
| [`tlias-pojo`](../tlias-pojo/pom.xml) | `com.example:tlias-pojo:1.0-SNAPSHOT` | `com.example.pojo`：`Dept`、`DeptLog`、`Emp`、`OperateLog`、`PageBean`、`Result` |
| [`tlias-utils`](../tlias-utils/pom.xml) | `com.example:tlias-utils:1.0-SNAPSHOT` | `com.example.utils`：`AliOSSUtils`、`AliOSSProperties`（OSS 上传）、`JwtUtils`（JWT 生成 / 解析） |
| 本工程 | `com.example:tlias-web-management-maven-module-splitting:0.0.1-SNAPSHOT` | Controller / Service / Mapper / 拦截器 / AOP 等 Web 层代码，依赖 `tlias-pojo` 与 `tlias-utils` |

拆分后包名保持 `com.example.pojo` / `com.example.utils` 不变，业务代码的 `import` 无需修改；启动类位于 `com.example` 包下，组件扫描可以覆盖 `tlias-utils` 中的 `@Component`。

## 技术栈

- JDK 8、Maven
- Spring Boot `2.6.13`（含 `spring-boot-starter-aop`）
- MyBatis Spring Boot Starter `2.2.2`
- PageHelper `1.4.7`
- MySQL Connector/J
- 阿里云 OSS SDK `3.18.4`（经 `tlias-utils` 引入）
- JWT：jjwt `0.9.1`（经 `tlias-utils` 引入，登录成功后下发令牌）
- fastjson `1.2.84`（拦截器手动返回 JSON、AOP 序列化返回值）
- Nacos 服务注册发现：`spring-cloud-starter-alibaba-nacos-discovery` `2021.0.5.0`
- Lombok `1.18.24`（由 `tlias-parent` 统一引入）

## 环境准备

1. 创建 MySQL 8.x 数据库，并执行 [tlias.sql](src/main/resources/SQL/tlias.sql)。脚本会创建 `tlias` 数据库，以及 `dept` 部门表、`emp` 员工表、`dept_log` 部门操作日志表、`operate_log` 操作日志表。
2. 检查 [application.yml](src/main/resources/application.yml) 中的数据库连接配置。
3. 图片上传需要设置以下环境变量，代码不会从 Markdown 或源码中读取凭据：

   - `OSS_ACCESS_KEY_ID`
   - `OSS_ACCESS_KEY_SECRET`

   `application.yml` 中的 `aliyun.oss.endpoint`、`aliyun.oss.bucketName` 和 `aliyun.oss.region` 需要改成自己的 OSS 信息。
4. `application.yml` 配置了 Nacos 注册中心地址 `127.0.0.1:8848`，注册的服务名为 `ls-tlias-maven-module-splitting`（与原 `tlias-web-management` 的 `ls-tlias` 区分）。本地未启动 Nacos 时，需要先启动 Nacos，或移除 Nacos 依赖与配置。
5. Nacos 2.x 客户端除 `8848` 外还需要 gRPC 端口 `9848`。用 Docker 运行 Nacos 时需同时映射 `8848`、`9848`、`9849`，否则启动时报 `Client not connected, current status:STARTING`。

## 构建与启动

推荐在父工程目录一次构建全部模块，Maven 会按依赖顺序先构建 `tlias-pojo`、`tlias-utils`：

```bash
cd DJavaWeb/tlias-parent
mvn install
```

之后启动 Web 工程：

```bash
cd DJavaWeb/tlias-web-management-maven-module-splitting
mvn spring-boot:run
```

- 后端端口为 `8082`（见 `application.yml` 的 `server.port`），启动类为 `com.example.TliasWebManagementMavenModuleSplittingApplication`（含 `@ServletComponentScan` 用于扫描 `@WebFilter`，`@EnableDiscoveryClient` 用于注册到 Nacos）。
- 本工程 `pom.xml` 设置了 `<skipTests>true</skipTests>`，`install` / `package` 时跳过测试执行（测试类仍会编译）；需要运行测试时加 `-DskipTests=false`。
- 单独在本目录构建时，`tlias-pojo`、`tlias-utils` 及其父工程 `tlias-parent`、`DJavaWeb` 都从本地仓库读取。若报 `Could not find artifact com.example:DJavaWeb:pom:1.0-SNAPSHOT`，先在 `DJavaWeb` 目录执行 `mvn -N install` 安装聚合父 POM，再按上面的方式在 `tlias-parent` 执行 `mvn install`。
- 修改 `tlias-pojo` / `tlias-utils` 后，需要重新 `install`，或在 `DJavaWeb` 目录执行 `mvn compile -pl tlias-web-management-maven-module-splitting -am` 一起构建。

仓库中还保留了构建后的前端资源，入口位于 `src/main/resources/FE/nginx-1.22.0-tlias/html/`；前端通过 Nginx（监听 `90`）将 `/api` 请求代理到后端，代理地址见 `conf/nginx.conf` 中的 `proxy_pass`，需与后端端口保持一致。

## 发布到私服

`tlias-parent` 的 `distributionManagement` 配置了本地 Nexus（`http://localhost:8091`）：

| 仓库 id | 地址 | 用途 |
|---------|------|------|
| `maven-releases` | `/repository/maven-releases/` | 发布 release 版本 |
| `maven-snapshots` | `/repository/maven-snapshots/` | 发布 SNAPSHOT 版本（当前各模块均为 SNAPSHOT） |

在 `tlias-parent` 目录执行 `mvn deploy` 会依次发布 4 个模块。Maven `settings.xml` 需要：

1. `<servers>` 中配置 `maven-releases`、`maven-snapshots` 的账号，用于上传。
2. 若 `<mirrors>` 把所有仓库（`mirrorOf=*`）镜像到 Nexus 的 `maven-public`，且 Nexus 关闭了匿名访问，还需要为 `maven-public` 配置同一账号，否则下载依赖时报 `Not authorized`。

账号密码只写在本机 `settings.xml` 中，不要提交到仓库。

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
├── TliasWebManagementMavenModuleSplittingApplication.java   # 启动类
├── anno/         # 自定义注解 @Log
├── aop/          # LogAspect：AOP 记录操作日志
├── config/       # WebConfig：注册拦截器
├── controller/   # HTTP 接口（部门 / 员工 / 登录 / Session / 上传）
├── exception/    # GlobalExceptionHandler：全局异常处理
├── Filter/       # Servlet Filter 入门（AbcFilter、DemoFilter、LoginCheckFilter）
├── interceptor/  # LoginCheckInterceptor：登录校验拦截器
├── mapper/       # MyBatis Mapper（含 DeptLogMapper、OperateLogMapper）
└── service/      # 业务逻辑、分页与事务示例（解散部门）
```

实体类在 [`tlias-pojo`](../tlias-pojo/src/main/java/com/example/pojo)，工具类在 [`tlias-utils`](../tlias-utils/src/main/java/com/example/utils)。MyBatis XML 位于 `src/main/resources/com/example/mapper/EmpMapper.xml`，数据库脚本位于 `src/main/resources/SQL/tlias.sql`（含 `dept_log`、`operate_log`）。

| 目录 | 笔记 |
|------|------|
| `Filter/` | [Filter/README.md](src/main/java/com/example/Filter/README.md) |
| `interceptor/` | [interceptor/README.md](src/main/java/com/example/interceptor/README.md) |
| `config/` | [config/README.md](src/main/java/com/example/config/README.md) |
| `service/` | [service/README.md](src/main/java/com/example/service/README.md)（事务） |

---

## 相关链接

- 上级模块：[DJavaWeb/README.md](../README.md)
- 拆分前的原项目：[tlias-web-management/README.md](../tlias-web-management/README.md)
- 父工程：[tlias-parent/pom.xml](../tlias-parent/pom.xml)
- Filter 笔记：[Filter/README.md](src/main/java/com/example/Filter/README.md)
- 拦截器笔记：[interceptor/README.md](src/main/java/com/example/interceptor/README.md)
- 配置类笔记：[config/README.md](src/main/java/com/example/config/README.md)
- 事务笔记：[service/README.md](src/main/java/com/example/service/README.md)
- AOP 入门：[aop/README.md](../springboot-aop-quickstart/src/main/java/com/itheima/aop/README.md)
- 接口文档：[api接口文档.md](src/main/resources/API/api接口文档.md)
- 前端工程：[vue-project/README.md](../vue-project/README.md)
- 全仓库文档索引：[docs/README.md](../../docs/README.md)
