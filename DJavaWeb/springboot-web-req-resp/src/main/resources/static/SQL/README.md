# MySQL 基础练习（DDL / DML / DQL）

黑马 JavaWeb 课程配套：MySQL 数据库与表结构（DDL）、数据增删改（DML）、数据查询（DQL）及多表关联练习脚本，放在 `springboot-web-req-resp` 静态资源下便于随课程查阅。

> 本目录为 **手写练习用 SQL**，不是 Spring Boot 运行时依赖。请在本地 MySQL 客户端（如命令行、Navicat、DataGrip）中执行。

上级模块说明见 [DJavaWeb/README.md](../../../../../../README.md)。

---

## 目录文件

| 文件 | 内容 |
|------|------|
| `mysql_01.sql` | 库操作（查询 / 使用 / 创建 / 删除）+ 表 DDL（建表与约束、查看、修改、删除）+ DML（`INSERT` / `UPDATE` / `DELETE`） |
| `mysql_02.sql` | DQL 参考答案：`tb_emp` 建表与测试数据 + 基础查询 / 条件查询 / 分组与聚合（可对照练习题核对） |
| `mysql_03.sql` | 多表 DDL 准备：员工 / 部门等表结构示例（含约束与关联字段） |
| `mysql_04.sql` | 部门 / 员工完整建表、测试数据及后续多表查询相关脚本 |
| `1. DQL-数据准备.sql` | DQL 练习：员工表建表与测试数据准备 |
| `2. DQL-基础查询.sql` | DQL 练习题：指定字段、全字段、别名、`DISTINCT` |
| `3. DQL-条件查询.sql` | DQL 练习题：`WHERE`、比较、空值、区间、`IN`、模糊查询 |
| `4. DQL-分组查询.sql` | DQL 练习题：聚合函数与 `GROUP BY` / `HAVING` |
| `5. DQL-排序查询.sql` | DQL 练习题：`ORDER BY` 升序 / 降序 / 多字段排序 |
| `6. DQL-分页查询.sql` | DQL 练习题：`LIMIT` 分页 |
| `dept & emp.sql` | 部门与员工相关练习脚本 |
| `student & course.sql` | 学生与课程多表练习脚本 |
| `user & card.sql` | 用户与证件等一对一 / 关联练习脚本 |
| `mybatis.sql` | MyBatis 入门配套脚本：建库 `mybatis` 与 `user` 表（与 `springboot-mybatis-quickstart` 使用同一份脚本） |
| `数据库-MySQL-01.md` | 课程笔记：MySQL 入门与库表基础 |
| `数据库-MySQL-02.md` | 课程笔记：查询等进阶内容 |
| `数据库-MySQL-04.md` | 课程笔记：多表与综合内容 |
| `MySQL数据类型.xlsx` | MySQL 常用数据类型对照参考 |
| `assets/` | 课程笔记配图（gif / png） |

---

## 环境要求

1. **MySQL 8.x**（课程常用；5.7 亦可，注意部分语法差异）
2. 建议安装并配置 `mysql` 命令行到 PATH，或使用图形化客户端

连接示例（按本机账号修改）：

```bash
mysql -u root -p
```

---

## 使用说明

建议按阶段练习：

### 阶段一：DDL + DML（`mysql_01.sql`）

1. 分段执行库操作：`SHOW` / `USE` / `CREATE DATABASE` / `DROP DATABASE`
2. 再练习建表、约束、`ALTER`、`DROP`，理解 `tb_user` / `tb_emp` 字段含义
3. 确保已执行完整 `CREATE TABLE tb_emp` 后，再练习 `INSERT` / `UPDATE` / `DELETE`

### 阶段二：DQL 练习（`1.`～`6.` 编号脚本）

1. 先执行 `1. DQL-数据准备.sql`（或 `mysql_02.sql` 开头的建表 + 插入），准备好 `tb_emp` 与测试数据
2. 按序号打开 `2.`～`6.`，根据注释自行写出查询语句并在客户端验证
3. 需要对照时，打开 `mysql_02.sql` 查看基础 / 条件 / 分组部分的参考写法
4. 需要查类型长度与取值范围时，对照 `MySQL数据类型.xlsx`

### 阶段三：多表与关联（`mysql_03` / `mysql_04` 与命名脚本）

1. 阅读并分段执行 `mysql_03.sql`、`mysql_04.sql`，理解部门 / 员工等多表结构与测试数据
2. 练习 `dept & emp.sql`、`student & course.sql`、`user & card.sql` 中的关联场景
3. 对照课程笔记 `数据库-MySQL-01.md`、`数据库-MySQL-02.md`、`数据库-MySQL-04.md`（配图在 `assets/`）

注意：

- `CREATE` / `DROP` / `INSERT` / `UPDATE` / `DELETE` 会改动本机数据，练习库建议使用独立库名（如 `db01`），勿在生产库执行
- `mysql_01.sql` 中部分 DDL 依赖已存在的表（如早期的 `ALTER tb_emp`）；若报错，先执行完整 `CREATE TABLE tb_emp` 再建改表与增删改练习
- 同一脚本内可能有多段互斥示例（如重复 `CREATE TABLE tb_user`），按注释分段执行即可
- `username` 有唯一约束，重复执行同名 `INSERT` 会失败，可改用户名或先清空练习数据
- DQL 编号脚本多为「题目注释 + 空白」，需自行填写；`mysql_02.sql` 为参考答案，勿与练习题混淆

---

## 与课程模块的关系

| 阶段 | 说明 |
|------|------|
| 当前 | 纯 SQL：DDL + DML + DQL + 多表，为后续 JDBC、MyBatis 做准备 |
| 后续（课程） | 在 Spring Boot 中接入数据源与持久层，替换当前 `emp.xml` 等内存/文件方案 |

---

## 相关链接

- 上级模块：[DJavaWeb/README.md](../../../../../../README.md)
- MyBatis 入门脚本副本：同目录 `mybatis.sql`（与 `springboot-mybatis-quickstart` 共用）
- 全仓库文档索引：[docs/README.md](../../../../../../../docs/README.md)
