# tlias-web-management

Tlias 智能学习辅助系统后端，当前实现包含部门管理、员工分页与条件查询、员工新增/修改/批量删除，以及图片上传到阿里云 OSS。

上级模块说明见 [DJavaWeb/README.md](../README.md)，完整接口见 [API 接口文档](src/main/resources/API/api接口文档.md)。

## 技术栈

- JDK 8、Maven
- Spring Boot `2.6.13`
- MyBatis Spring Boot Starter `2.2.2`
- PageHelper `1.4.7`
- MySQL Connector/J
- 阿里云 OSS SDK `3.18.4`
- Lombok

## 环境准备

1. 创建 MySQL 8.x 数据库，并执行 [tlias.sql](src/main/resources/SQL/tlias.sql)。脚本会创建 `tlias` 数据库、`dept` 部门表和 `emp` 员工表。
2. 检查 [application.yml](src/main/resources/application.yml) 中的数据库连接配置。
3. 图片上传需要设置以下环境变量，代码不会从 Markdown 或源码中读取凭据：

   - `OSS_ACCESS_KEY_ID`
   - `OSS_ACCESS_KEY_SECRET`

   `application.yml` 中的 `aliyun.oss.endpoint`、`aliyun.oss.bucketName` 和 `aliyun.oss.region` 需要改成自己的 OSS 信息。

## 启动

```bash
cd DJavaWeb/tlias-web-management
mvn spring-boot:run
```

后端默认使用 `8080` 端口，启动类为 `com.example.TliasWebManagementApplication`。仓库中还保留了构建后的前端资源，入口位于 `src/main/resources/FE/nginx-1.22.0-tlias/html/`；前端通过 Nginx 将 `/api` 请求代理到后端。

## 当前接口

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
| 文件 | POST | `/upload` | 以 `multipart/form-data` 的 `image` 字段上传图片 |

员工列表支持 `page`、`pageSize`、`name`、`gender`、`begin`、`end` 查询参数，其中日期格式为 `yyyy-MM-dd`。所有接口返回统一的 `Result` 结构：`code` 为 `1` 表示成功，`0` 表示失败。

## 代码结构

```text
src/main/java/com/example/
├── controller/   # HTTP 接口
├── service/      # 业务逻辑与分页
├── mapper/       # MyBatis Mapper
├── pojo/         # Dept、Emp、PageBean、Result
└── utils/        # OSS 配置与上传工具
```

MyBatis XML 位于 `src/main/resources/com/example/mapper/EmpMapper.xml`，数据库脚本位于 `src/main/resources/SQL/tlias.sql`。

---

## 相关链接

- 上级模块：[DJavaWeb/README.md](../README.md)
- 接口文档：[api接口文档.md](src/main/resources/API/api接口文档.md)
- 前端工程：[vue-project/README.md](../vue-project/README.md)
- 全仓库文档索引：[docs/README.md](../../docs/README.md)
