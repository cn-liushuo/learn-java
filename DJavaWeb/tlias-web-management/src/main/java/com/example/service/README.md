# Transaction（事务管理）

Tlias 工程中的 Spring 声明式事务（Declarative Transaction）入门示例，对应源码：

- [`DeptServiceImpl`](impl/DeptServiceImpl.java)：解散部门时开启事务
- [`DeptLogServiceImpl`](impl/DeptLogServiceImpl.java)：操作日志使用独立事务
- [`DeptLog`](../pojo/DeptLog.java) / [`DeptLogMapper`](../mapper/DeptLogMapper.java)：部门操作日志实体与持久化

上级说明见 [tlias-web-management/README.md](../../../../../../README.md)。事务调试日志见 [`application.yml`](../../../../resources/application.yml) 中的 `JdbcTransactionManager` 配置。

---

## 业务场景

删除（解散）部门时，需要保证一组数据库操作的一致性：

1. 按 `id` 删除部门（`dept` 表）
2. 按部门 `id` 删除该部门下的员工（`emp` 表）
3. **无论成功或失败**，都写入一条操作日志（`dept_log` 表）

前两步应同成同败；日志需要在业务事务回滚后仍然落库，因此日志使用独立事务。

触发接口：`DELETE /depts/{id}`（见 `DeptController.delete`）。

---

## 快速入门

Spring Boot 集成数据源后，在 Service 方法上标注 `@Transactional` 即可开启声明式事务，无需手写 `begin` / `commit` / `rollback`。

| 注解 / 属性 | 含义 | 本示例 |
|-------------|------|--------|
| `@Transactional` | 方法在事务中执行；默认仅对 RuntimeException（运行时异常）与 Error 回滚 | `DeptServiceImpl.delete` |
| `rollbackFor` | 指定还需回滚的异常类型（含受检异常） | 源码中有注释示例：`rollbackFor = Exception.class` |
| `propagation` | 事务传播行为（Propagation） | 日志方法使用 `REQUIRES_NEW` |

当前 `delete` 方法使用的是默认 `@Transactional`（未显式写 `rollbackFor`）。学习受检异常回滚时，可切换为注释中的写法。

---

## 代码结构

```text
service/
├── DeptService.java          # 部门业务接口
├── DeptLogService.java       # 部门日志业务接口
├── impl/
│   ├── DeptServiceImpl.java  # @Transactional：删除部门 + 员工
│   └── DeptLogServiceImpl.java  # REQUIRES_NEW：插入日志
pojo/DeptLog.java             # 日志实体
mapper/DeptLogMapper.java     # insert into dept_log ...
```

表结构见 [`tlias.sql`](../../../../resources/SQL/tlias.sql) 中的 `dept_log`。

---

## 执行流程

`DeptServiceImpl.delete` 的大致逻辑：

```text
开启事务（@Transactional）
  try
    → 删除部门
    → （学习用）人为异常：1/0 或 throw Exception
    → 删除该部门下员工
  finally
    → deptLogService.insert(...)   # 独立事务 REQUIRES_NEW，始终尝试写日志
异常未捕获时 → 外层事务回滚（部门 / 员工变更撤销）
```

对照源码要点：

1. `deptMapper.deleteById(id)` 与 `empMapper.deleteByDeptId(id)` 处于同一事务中。
2. 中间保留了学习用异常（当前为 `int i = 1/0;`，会抛出 `ArithmeticException`），用于观察回滚效果。
3. `finally` 中组装 `DeptLog` 并调用 `deptLogService.insert`，保证「有操作就记日志」。
4. `DeptLogServiceImpl.insert` 使用 `@Transactional(propagation = Propagation.REQUIRES_NEW)`：挂起外层事务，新开事务提交日志；外层即使回滚，日志仍会保留。

---

## 事务传播行为（Propagation）

本示例重点使用 `REQUIRES_NEW`：

| 取值 | 含义（简要） |
|------|----------------|
| `REQUIRED`（默认） | 有事务则加入，无则新建 |
| `REQUIRES_NEW` | 始终新建事务；若已有事务则先挂起，新事务独立提交/回滚 |

解散部门场景下：

- 外层 `delete`：业务数据要么全成功，要么全回滚。
- 内层 `insert`（日志）：独立提交，不被外层回滚带走。

> 注意：传播行为要生效，日志方法须通过 **另一个 Spring Bean** 调用（本示例经 `DeptLogService` 注入调用）。同类内部 `this.xxx()` 自调用不会走代理，注解可能不生效。

---

## 回滚规则（学习对照）

| 异常类型 | 默认 `@Transactional` | `rollbackFor = Exception.class` |
|----------|----------------------|----------------------------------|
| `RuntimeException`（如 `1/0`） | 回滚 | 回滚 |
| 受检 `Exception`（如 `throw new Exception(...)`） | **不回滚**（默认） | 回滚 |

源码中注释掉的 `if (true) { throw new Exception("出错啦..."); }` 可与 `1/0` 对照实验。

---

## 调试日志

`application.yml` 已打开事务管理器 Debug 日志：

```yaml
logging:
  level:
    org.springframework.jdbc.support.JdbcTransactionManager: debug
```

启动后调用删除接口，控制台可看到 Creating / Committing / Rolling back 等事务相关输出，便于确认开启、提交与回滚。

---

## 运行观察

1. 确认已执行 [`tlias.sql`](../../../../resources/SQL/tlias.sql)（含 `dept_log` 表）。
2. 启动 Tlias：在 `tlias-web-management` 目录执行 `mvn spring-boot:run`（默认端口 `8080`）。
3. 调用删除部门，例如：`DELETE http://localhost:8080/depts/1`（按实际存在的部门 id 调整）。
4. 预期（在保留 `1/0` 学习代码时）：
   - 接口失败（运行时异常导致事务回滚）；
   - `dept` / `emp` 中对应数据**未删除**；
   - `dept_log` 中**仍有一条**解散操作日志；
   - 控制台出现事务回滚相关 Debug 日志。
5. 学习结束后，删除或注释人为异常（`1/0` / `throw new Exception`），再测一次正常解散流程（部门与员工删除成功，日志照常写入）。

观察完毕后，勿把仅用于演示的异常代码当成生产逻辑提交。

---

## 相关链接

- 上级模块：[tlias-web-management/README.md](../../../../../../README.md)
- 部门接口：`DeptController`（`DELETE /depts/{id}`）
- 数据库脚本：[`tlias.sql`](../../../../resources/SQL/tlias.sql)（`dept_log`）
- 配置：[`application.yml`](../../../../resources/application.yml)（事务 Debug 日志）
- 全仓库文档索引：[docs/README.md](../../../../../../../../docs/README.md)
