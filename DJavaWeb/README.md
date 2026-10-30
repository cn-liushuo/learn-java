# DJavaWeb

JavaWeb 学习模块：前端（HTML / CSS / JS / Ajax / Vue）、Maven 多模块、手写 HTTP Server、Spring Boot Web（快速入门 + 请求/响应 + 员工三层架构）、MySQL DDL / DML / DQL 基础练习、MyBatis 入门与 CRUD、Tlias 智能学习辅助系统，以及 AOP、Spring Boot 配置优先级、Bean 管理与 SpringBoot 原理、自定义 Starter（阿里云 OSS）、Maven 分模块设计（Tlias 拆分）与私服发布。

配套课程：[黑马程序员 JavaWeb 开发教程（Spring / MyBatis / SpringMVC / SpringBoot 全流程）](https://www.bilibili.com/video/BV1m84y1w7Tb/?spm_id_from=333.788.video.desc.click)

仓库总览见根目录 [README.md](../README.md)。

---

## 环境要求

1. **JDK 8**、**Maven 3.6.1+**
2. IDE：IntelliJ IDEA / Eclipse
3. **Node.js LTS**：仅 [vue-project](vue-project/README.md) 需要
4. **MySQL 8.x**：SQL 练习、MyBatis 两个工程、Tlias、AOP 入门需要。SQL 练习说明见 [static/SQL](springboot-web-req-resp/src/main/resources/static/SQL/README.md)
5. **Nacos**：仅两个 Tlias Web 工程需要（注册中心 `127.0.0.1:8848`；Nacos 2.x 客户端还需 gRPC 端口 `9848`，Docker 部署时需同时映射 `8848`、`9848`、`9849`）
6. **Nexus 私服**（可选）：仅 `tlias-parent` 执行 `mvn deploy` 时需要（`http://localhost:8091`），配置见 [Tlias 分模块 README](tlias-web-management-maven-module-splitting/README.md#发布到私服)

聚合父工程 `DJavaWeb/pom.xml`（`com.example:DJavaWeb:1.0-SNAPSHOT`）继承 `spring-boot-starter-parent` `2.6.13`。

主要依赖：

| 子工程 / 项目 | 依赖要点 |
|---------------|----------|
| `vue-project` | Vue `2.6`、Vue Router `3`、Element UI `2`、Axios |
| `springboot-web-quickstart` | Spring Boot `2.6.13`（`spring-boot-starter-web`） |
| `springboot-web-quickstart2` | Spring Boot `2.7.6`（`spring-boot-starter-web`） |
| `springboot-web-req-resp` | Spring Boot `2.7.6`、dom4j `2.1.3`（XML 解析） |
| `springboot-mybatis-quickstart` | Spring Boot `2.6.13`、MyBatis `2.2.2`、Druid `1.2.28`、MySQL 驱动 |
| `springboot-mybatis-crud` | Spring Boot `2.6.13`、MyBatis `2.2.2`、MySQL 驱动 |
| `tlias-web-management` | Spring Boot `2.6.13`、MyBatis `2.2.2`、PageHelper `1.4.7`、阿里云 OSS SDK `3.18.4`、jjwt `0.9.1`、fastjson `1.2.84`、AOP、Nacos Discovery `2021.0.5.0`、Lombok、MySQL 驱动 |
| `tlias-parent` | 父工程（`pom`）：`dependencyManagement` 管理 `spring-boot-starter-web` `2.6.13`、OSS SDK `3.18.4`、jjwt `0.9.1`、`spring-boot-configuration-processor`；统一引入 Lombok `1.18.24`；`distributionManagement` 指向 Nexus |
| `tlias-pojo` | 仅继承 `tlias-parent`（Lombok） |
| `tlias-utils` | `spring-boot-starter-web`、OSS SDK、jjwt、`spring-boot-configuration-processor`（版本由 `tlias-parent` 管理） |
| `tlias-web-management-maven-module-splitting` | 与 `tlias-web-management` 相同的 Web 依赖，OSS / jjwt 改为经 `tlias-utils` 引入，另依赖 `tlias-pojo` |
| `springboot-aop-quickstart` | Spring Boot `2.7.2`、`spring-boot-starter-aop`、MyBatis `2.2.2`、Lombok、MySQL 驱动 |
| `springboot-web-config` | Spring Boot `2.6.13`（`spring-boot-starter-web`） |
| `springboot-web-config2` | Spring Boot `2.7.5`、MyBatis `2.2.2`、PageHelper `1.4.2`、OSS `3.15.1`、jjwt、fastjson、dom4j `2.1.3`、`itheima-utils` |
| `itheima-utils` | `spring-boot-starter` `2.7.5`（模拟第三方工具包） |
| `aliyun-oss-spring-boot-autoconfigure` | Spring Boot `2.6.13`、`spring-boot-starter-web`、OSS SDK `3.18.4`、`spring-boot-configuration-processor` |
| `aliyun-oss-spring-boot-starter` | `spring-boot-starter` + `aliyun-oss-spring-boot-autoconfigure`（只做依赖聚合，无代码） |
| `springboot-autoconfiguration-test` | Spring Boot `2.6.13`、`spring-boot-starter-web`、`aliyun-oss-spring-boot-starter` |

---

## 目录结构

```text
DJavaWeb/
├── HTML/                       # HTML / CSS 示例
├── JS/                         # JavaScript + Vue 指令示例
├── Ajax/                       # Ajax 原生与 Axios
├── vue-project/                # Vue CLI 实战（Tlias 管理页）
├── maven-project01/            # Maven 入门
├── maven-project02/            # Maven 练习
├── maven-projectA/B/C/         # Maven 多模块依赖演示
├── http-server-demo/           # 基于 ServerSocket 的简易 HTTP 服务
├── springboot-web-quickstart/  # Spring Boot Web 快速入门
├── springboot-web-quickstart2/ # Spring Boot 脚手架练习
├── springboot-web-req-resp/    # 请求参数 / 统一响应 / 员工三层 + SQL 练习
├── springboot-mybatis-quickstart/ # MyBatis 注解查询入门
├── springboot-mybatis-crud/    # MyBatis 员工表增删改查
├── tlias-web-management/       # Tlias：部门/员工/登录/上传 + Filter / 拦截器 + 事务 + AOP 日志 + OSS
├── springboot-aop-quickstart/  # AOP 入门：通知类型 / 顺序 / 切入点表达式 / 连接点
├── springboot-web-config/      # 配置文件格式与配置优先级
├── springboot-web-config2/     # Bean 管理 + SpringBoot 原理（自动配置）
├── itheima-utils/              # 模拟第三方 jar，供 springboot-web-config2 演示自动配置
├── aliyun-oss-spring-boot-autoconfigure/ # 自定义 Starter：OSS 自动配置类 + spring.factories
├── aliyun-oss-spring-boot-starter/       # 自定义 Starter：依赖聚合（引入 autoconfigure）
├── springboot-autoconfiguration-test/    # 引入自定义 OSS Starter，直接注入 AliOSSUtils 上传文件
├── tlias-parent/               # Tlias 分模块父工程：聚合 pojo / utils / Web，统一依赖版本，私服发布
├── tlias-pojo/                 # Tlias 分模块：实体类
├── tlias-utils/                # Tlias 分模块：OSS / JWT 工具类
├── tlias-web-management-maven-module-splitting/ # Tlias 分模块：Web 工程（端口 8082）
└── pom.xml                     # 聚合父工程（继承 spring-boot-starter-parent 2.6.13）
```

---

## 子模块 / 目录对照

| 目录 / 子模块 | 主题 |
|---------------|------|
| `HTML` | 排版案例、表格 / 表单、盒子模型 |
| `JS` | 语法 → DOM/事件 → Vue 指令与生命周期（01–16 JS；17–23 Vue） |
| `Ajax` | 原生 Ajax / Axios / 案例 |
| [vue-project](vue-project/README.md) | Element UI + Tlias 部门 / 员工页 |
| `maven-project*` | Maven 工程结构、依赖管理、多模块 |
| `http-server-demo` | Socket 级 HTTP 服务原理 |
| `springboot-web-quickstart` | Spring Boot Web、Controller、静态资源 |
| `springboot-web-quickstart2` | Spring Boot 脚手架练习 |
| `springboot-web-req-resp` | 请求参数绑定；`Result` 统一响应；员工列表三层架构；[MySQL 练习](springboot-web-req-resp/src/main/resources/static/SQL/README.md) |
| `springboot-mybatis-quickstart` | MyBatis 注解查询 `user`；无 Web 依赖；库名 `mybatis` |
| `springboot-mybatis-crud` | 员工表注解 / XML 增删改查；脚本 `SQL/emp.sql`；库名 `mybatis` |
| [`tlias-web-management`](tlias-web-management/README.md) | 部门 CRUD（`/depts`）+ 员工分页、增改删（`/emps`）+ 登录（`/login`）+ OSS 上传（`/upload`）+ [Filter](tlias-web-management/src/main/java/com/example/Filter/README.md) / [拦截器](tlias-web-management/src/main/java/com/example/interceptor/README.md) + [事务](tlias-web-management/src/main/java/com/example/service/README.md) + AOP 操作日志；PageHelper；脚本 `SQL/tlias.sql`；库名 `tlias`；前端静态资源在 `src/main/resources/FE/` |
| [`springboot-aop-quickstart`](springboot-aop-quickstart/src/main/java/com/itheima/aop/README.md) | AOP 入门：五种通知、通知顺序、`execution` / `@annotation` 切入点、连接点；复用库 `tlias` |
| [`springboot-web-config`](springboot-web-config/src/main/resources/README.md) | `properties` / `yml` / `yaml` 配置格式，Java 系统属性与命令行参数的优先级 |
| [`springboot-web-config2`](springboot-web-config2/src/main/resources/README.md) | 获取 Bean、Bean 作用域、第三方 Bean（`@Bean`）；起步依赖、自动配置、`@Import` / `@EnableXxx` |
| `itheima-utils` | 模拟第三方工具包：`TokenParser`、`HeaderConfig`、`MyImportSelector`、`@EnableHeaderConfig` |
| `aliyun-oss-spring-boot-autoconfigure` | `AlOSSAutoConfiguration`（`@EnableConfigurationProperties(AliOSSProperties.class)` + `@Bean AliOSSUtils`），通过 `META-INF/spring.factories` 注册；配置前缀 `aliyun.oss` |
| `aliyun-oss-spring-boot-starter` | Starter 命名规范示例：本身无代码，只依赖 `aliyun-oss-spring-boot-autoconfigure` |
| `springboot-autoconfiguration-test` | 引入 `aliyun-oss-spring-boot-starter` 后无需 `@Import` / `@ComponentScan`，`UploadController` 直接 `@Autowired AliOSSUtils`，`POST /upload` 上传图片 |
| [`tlias-web-management-maven-module-splitting`](tlias-web-management-maven-module-splitting/README.md) | Tlias 分模块版：实体类拆到 `tlias-pojo`、工具类拆到 `tlias-utils`，由 `tlias-parent` 聚合并统一版本；Nacos 服务名 `ls-tlias-maven-module-splitting`；支持 `mvn deploy` 到 Nexus |
| `tlias-parent` / `tlias-pojo` / `tlias-utils` | 分模块的父工程 / 实体类 / 工具类，说明见 [Tlias 分模块 README](tlias-web-management-maven-module-splitting/README.md#模块拆分) |

---

## 安装与运行

**聚合工程**

```bash
cd DJavaWeb
mvn compile
```

**前端 vue-project**

详见 [vue-project/README.md](vue-project/README.md)：

```bash
cd vue-project
npm install
npm run serve
```

静态 HTML / JS / Ajax 示例：用浏览器直接打开对应 `.html` 即可。

**Spring Boot 快速入门**

```bash
cd springboot-web-quickstart
mvn spring-boot:run
```

启动后访问：

- `http://localhost:8080/hello` → 返回 `Hello World`
- `http://localhost:8080/01.GET-POST.html` → GET / POST 表单演示页

**Spring Boot 请求响应（参数绑定 / 统一响应 / 员工列表）**

```bash
cd springboot-web-req-resp
mvn spring-boot:run
```

`RequestController` 统一前缀 `/param`：

| 路径 | 说明 |
|------|------|
| `/param/simpleParam` | 简单参数（`@RequestParam`） |
| `/param/simplePojo` | 简单实体封装 |
| `/param/complexPojo` | 复杂实体（含嵌套 Address） |
| `/param/arrayParam` | 数组参数 |
| `/param/listParam` | 集合参数 |
| `/param/dateParam` | 日期时间（`yyyy-MM-dd HH:mm:ss`） |
| `/param/jsonParam` | JSON 请求体（`@RequestBody`） |
| `/param/path/{id}`、`/param/path/{id}/{name}` | 路径变量（`@PathVariable`） |

`ResponseController` 统一前缀 `/response`（返回 `Result`）：

| 路径 | 说明 |
|------|------|
| `/response/hello` | 字符串数据封装 |
| `/response/getAddr` | 单个 Address 对象 |
| `/response/listAddr` | Address 列表 |

员工列表（三层架构 + XML）：

- `GET /listEmp` → `EmpController` → Service → Dao，解析 `emp.xml`，返回 `Result`
- 页面：`http://localhost:8080/emp.html`

**手写 HTTP Server**

运行 `http-server-demo` 中 `com.itheima.Server` 的 `main`（默认端口 `8080`），浏览器访问对应静态资源路径即可。注意：与 Spring Boot 同时启动时勿占用同一端口。

**MySQL DDL / DML / DQL 练习**

与 Spring Boot 运行无关，详见：[static/SQL/README.md](springboot-web-req-resp/src/main/resources/static/SQL/README.md)

**MyBatis 入门与 CRUD**

这两个工程没有 Web 依赖，不是 HTTP 服务。先在本地 MySQL 建库，再在 IDE 中运行对应启动类。连接信息写在各自 `application.properties`，按本机账号修改，文档不记录口令。

1. `springboot-mybatis-quickstart`：执行 [mybatis.sql](springboot-mybatis-quickstart/src/main/resources/SQL/mybatis.sql)，创建库 `mybatis` 与 `user` 表。启动类：`com.example.SpringbootMybatisQuickstartApplication`。`UserMapper` 用注解查询全部用户。
2. `springboot-mybatis-crud`：使用同一个库 `mybatis`，再执行 [emp.sql](springboot-mybatis-crud/src/main/resources/SQL/emp.sql) 准备部门 / 员工表。启动类：`com.example.SpringbootMybatisCrudApplication`。`EmpMapper` 用注解与 XML 做增删改查。

**Tlias 智能学习辅助系统**

1. 执行 [tlias.sql](tlias-web-management/src/main/resources/SQL/tlias.sql)，创建库 `tlias` 及部门 / 员工表
2. 按 [Tlias README](tlias-web-management/README.md) 配置数据库与 OSS 环境变量
3. 在 `tlias-web-management` 目录执行：`mvn spring-boot:run`（默认端口 `8080`）
4. 接口入口：`/depts`、`/emps`、`/login`、`/upload` 等，详见 [Tlias README](tlias-web-management/README.md) 与 [API 接口文档](tlias-web-management/src/main/resources/API/api接口文档.md)
5. 除 `/login` 外的请求需在请求头 `token` 中携带 JWT；Filter / 拦截器 / 事务笔记见 Tlias README 中的「代码结构」
6. 配套前端为构建产物，入口在 `src/main/resources/FE/nginx-1.22.0-tlias/html/`
7. 已接入 Nacos 注册中心（`127.0.0.1:8848`），本地需先启动 Nacos

**Tlias 分模块（Maven 分模块设计与私服）**

1. 在 `tlias-parent` 目录执行 `mvn install`，按依赖顺序构建 `tlias-pojo`、`tlias-utils` 与 Web 工程（Web 工程已配置跳过测试执行）
2. 在 `tlias-web-management-maven-module-splitting` 目录执行 `mvn spring-boot:run`（端口 `8082`，启动类 `TliasWebManagementMavenModuleSplittingApplication`）
3. 单独构建 Web 工程报 `Could not find artifact com.example:DJavaWeb:pom:1.0-SNAPSHOT` 时，先在 `DJavaWeb` 目录执行 `mvn -N install`
4. 发布到 Nexus：在 `tlias-parent` 目录执行 `mvn deploy`，`settings.xml` 账号配置见 [Tlias 分模块 README](tlias-web-management-maven-module-splitting/README.md#发布到私服)

**AOP 入门**

复用库 `tlias`。修改 `application.yml` 中的数据库账号后，运行 `SpringbootAopQuickstart1ApplicationTests`，各切面的启用方式见 [aop/README.md](springboot-aop-quickstart/src/main/java/com/itheima/aop/README.md)。

**配置优先级**

`springboot-web-config` 演示 `properties` / `yml` / `yaml` 与命令行参数的优先级，详见 [README](springboot-web-config/src/main/resources/README.md)。

**Bean 管理与 SpringBoot 原理**

1. 先在 `itheima-utils` 目录执行 `mvn install`（或在 `DJavaWeb` 目录整体 `mvn install`）
2. 再运行 `springboot-web-config2` 中的 `SpringbootWebConfig2ApplicationTests`、`AutoConfigurationTests`
3. 详见 [README](springboot-web-config2/src/main/resources/README.md)

**自定义 Starter（阿里云 OSS）**

1. 在 `DJavaWeb` 目录执行 `mvn install -pl aliyun-oss-spring-boot-starter -am`，把 `aliyun-oss-spring-boot-autoconfigure` 与 `aliyun-oss-spring-boot-starter` 安装到本地仓库
2. 设置环境变量 `OSS_ACCESS_KEY_ID`、`OSS_ACCESS_KEY_SECRET`，并在 `springboot-autoconfiguration-test` 的 `application.yml` 中填写 `aliyun.oss.endpoint` / `bucketName` / `region`
3. 运行 `com.itheima.SpringbootAutoconfigurationTestApplication`，以 `multipart/form-data` 的 `image` 字段请求 `POST /upload`，返回文件访问 URL

`springboot-web-quickstart`、`springboot-web-quickstart2`、`springboot-web-req-resp`、`tlias-web-management`、`springboot-aop-quickstart`、`springboot-web-config`、`springboot-web-config2`、`springboot-autoconfiguration-test` 与手写 HTTP Server 都默认占用 `8080`，不要同时启动；`tlias-web-management-maven-module-splitting` 使用 `8082`。

---

## 使用说明

| 路径 | 用法 |
|------|------|
| `HTML/` | 浏览器打开 HTML/CSS 示例 |
| `JS/` | 01–16 JS 基础；17–23 Vue 指令与生命周期 |
| `Ajax/` | 原生 Ajax / Axios / 案例 |
| `vue-project/` | `npm run serve`；默认 `/dept`，另有 `/emp` |
| `maven-project01` ~ `maven-projectC` | Maven 依赖、测试、多模块练习 |
| `http-server-demo/` | 运行 `com.itheima.Server`，体验 HTTP 请求/响应 |
| `springboot-web-quickstart/` | 运行启动类；`HelloController` 提供 `/hello` |
| `springboot-web-quickstart2/` | Spring Boot 脚手架练习工程 |
| `springboot-web-req-resp/` | 参数绑定、统一响应、员工列表；`static/SQL` 为 MySQL 练习 |
| `springboot-mybatis-quickstart/` | 先执行 `mybatis.sql`，再运行启动类；注解查询 `user` |
| `springboot-mybatis-crud/` | 在库 `mybatis` 中执行 `emp.sql`，再运行启动类；员工表增删改查 |
| `tlias-web-management/` | 先执行 `tlias.sql`，按 [Tlias README](tlias-web-management/README.md) 配置数据库、OSS 与 Nacos，再 `mvn spring-boot:run`；`/depts`、`/emps`、`/login`、`/upload` 见 API / README |
| `springboot-aop-quickstart/` | 打开需要的切面类 `@Aspect`，运行测试类观察通知日志 |
| `springboot-web-config/` | 切换三种配置文件与启动参数，观察端口变化 |
| `springboot-web-config2/` | 先 `mvn install` `itheima-utils`，再运行 Bean 管理与自动配置测试 |
| `aliyun-oss-spring-boot-*/` | 自定义 Starter：先 `mvn install`，供 `springboot-autoconfiguration-test` 引用 |
| `springboot-autoconfiguration-test/` | 配置 OSS 环境变量后运行启动类，`POST /upload` 验证自定义 Starter |
| `tlias-parent/` | 执行 `mvn install` 构建全部 Tlias 分模块，`mvn deploy` 发布到 Nexus |
| `tlias-web-management-maven-module-splitting/` | 分模块版 Tlias，端口 `8082`；数据库 / OSS / Nacos 配置同原项目 |

建议学习顺序：前端静态页 → Vue → Maven → HTTP Server → Spring Boot 快速入门 → 请求/响应与三层架构 → MySQL SQL 练习 → MyBatis 入门 → MyBatis CRUD → Tlias（含 Filter / 拦截器 / 事务）→ AOP 入门 → Tlias AOP 操作日志 → 配置优先级 → Bean 管理 → SpringBoot 原理 → 自定义 Starter → Maven 分模块（Tlias 拆分）与私服。

---

## 相关链接

- Vue 子项目：[vue-project/README.md](vue-project/README.md)
- MySQL 练习：[static/SQL/README.md](springboot-web-req-resp/src/main/resources/static/SQL/README.md)
- Tlias 项目：[tlias-web-management/README.md](tlias-web-management/README.md)
- Tlias Filter：[Filter/README.md](tlias-web-management/src/main/java/com/example/Filter/README.md)
- Tlias 拦截器：[interceptor/README.md](tlias-web-management/src/main/java/com/example/interceptor/README.md)
- Tlias 事务：[service/README.md](tlias-web-management/src/main/java/com/example/service/README.md)
- Tlias 接口：[api接口文档.md](tlias-web-management/src/main/resources/API/api接口文档.md)
- Tlias 分模块：[tlias-web-management-maven-module-splitting/README.md](tlias-web-management-maven-module-splitting/README.md)
- AOP 入门：[aop/README.md](springboot-aop-quickstart/src/main/java/com/itheima/aop/README.md)
- 配置优先级：[springboot-web-config](springboot-web-config/src/main/resources/README.md)
- Bean 管理与 SpringBoot 原理：[springboot-web-config2](springboot-web-config2/src/main/resources/README.md)
- 全部 Markdown 索引：[docs/README.md](../docs/README.md)
