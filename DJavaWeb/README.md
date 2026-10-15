# DJavaWeb

JavaWeb 学习模块：前端（HTML / CSS / JS / Ajax / Vue）、Maven 多模块、手写 HTTP Server、Spring Boot Web（快速入门 + 请求/响应 + 员工三层架构）、MySQL DDL / DML / DQL 基础练习、MyBatis 入门与 CRUD，以及 Tlias 智能学习辅助系统。

配套课程：[黑马程序员 JavaWeb 开发教程（Spring / MyBatis / SpringMVC / SpringBoot 全流程）](https://www.bilibili.com/video/BV1m84y1w7Tb/?spm_id_from=333.788.video.desc.click)

仓库总览见根目录 [README.md](../README.md)。

---

## 环境要求

1. **JDK 8**、**Maven 3.6.1+**
2. IDE：IntelliJ IDEA / Eclipse
3. **Node.js LTS**：仅 [vue-project](vue-project/README.md) 需要
4. **MySQL 8.x**：SQL 练习、MyBatis 两个工程、Tlias 需要。SQL 练习说明见 [static/SQL](springboot-web-req-resp/src/main/resources/static/SQL/README.md)

主要依赖：

| 子工程 / 项目 | 依赖要点 |
|---------------|----------|
| `vue-project` | Vue `2.6`、Vue Router `3`、Element UI `2`、Axios |
| `springboot-web-quickstart` | Spring Boot `2.6.13`（`spring-boot-starter-web`） |
| `springboot-web-quickstart2` | Spring Boot `2.7.6`（`spring-boot-starter-web`） |
| `springboot-web-req-resp` | Spring Boot `2.7.6`、dom4j `2.1.3`（XML 解析） |
| `springboot-mybatis-quickstart` | Spring Boot `2.6.13`、MyBatis `2.2.2`、Druid `1.2.28`、MySQL 驱动 |
| `springboot-mybatis-crud` | Spring Boot `2.6.13`、MyBatis `2.2.2`、MySQL 驱动 |
| `tlias-web-management` | Spring Boot `2.6.13`、MyBatis `2.2.2`、PageHelper `1.4.7`、阿里云 OSS SDK `3.18.4`、Lombok、MySQL 驱动 |

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
├── tlias-web-management/       # Tlias：部门 CRUD + 员工分页与增删改 + OSS 图片上传
└── pom.xml                     # 聚合父工程
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
| [`tlias-web-management`](tlias-web-management/README.md) | 部门 CRUD（`/depts`）+ 员工分页、增改删（`/emps`）+ OSS 图片上传（`/upload`）；PageHelper；脚本 `SQL/tlias.sql`；库名 `tlias`；前端静态资源在 `src/main/resources/FE/` |

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
4. 接口入口：`/depts`、`/emps`、`/upload`。字段与示例见 [API 接口文档](tlias-web-management/src/main/resources/API/api接口文档.md)
5. 配套前端为构建产物，入口在 `src/main/resources/FE/nginx-1.22.0-tlias/html/`

`springboot-web-quickstart`、`springboot-web-quickstart2`、`springboot-web-req-resp`、`tlias-web-management` 与手写 HTTP Server 都默认占用 `8080`，不要同时启动。

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
| `tlias-web-management/` | 先执行 `tlias.sql`，按 [Tlias README](tlias-web-management/README.md) 配置数据库与 OSS，再 `mvn spring-boot:run`；`/depts`、`/emps`、`/upload` 见 API 文档 |

建议学习顺序：前端静态页 → Vue → Maven → HTTP Server → Spring Boot 快速入门 → 请求/响应与三层架构 → MySQL SQL 练习 → MyBatis 入门 → MyBatis CRUD → Tlias。

---

## 相关链接

- Vue 子项目：[vue-project/README.md](vue-project/README.md)
- MySQL 练习：[static/SQL/README.md](springboot-web-req-resp/src/main/resources/static/SQL/README.md)
- Tlias 项目：[tlias-web-management/README.md](tlias-web-management/README.md)
- Tlias 接口：[api接口文档.md](tlias-web-management/src/main/resources/API/api接口文档.md)
- 全部 Markdown 索引：[docs/README.md](../docs/README.md)
