# AOP（面向切面编程）

`springboot-aop-quickstart` 工程的 Spring AOP 入门示例。目标对象是部门业务 [`DeptServiceImpl`](../service/impl/DeptServiceImpl.java)，测试入口见 `SpringbootAopQuickstart1ApplicationTests`。

上级说明见 [DJavaWeb/README.md](../../../../../../../README.md)。AOP 在 Tlias 中的实战（操作日志）见 [tlias-web-management/README.md](../../../../../../../tlias-web-management/README.md)。

---

## 环境要求

- 依赖：`spring-boot-starter-aop`（Spring Boot `2.7.2` 父工程）、MyBatis `2.2.2`、MySQL 驱动、Lombok
- 数据库：复用 Tlias 的 `tlias` 库（`dept` 表），连接信息见 `src/main/resources/application.yml`，按本机账号修改

---

## 快速入门

AOP 可以在不修改原始方法的前提下，对方法做统一增强（如记录耗时、日志、权限）。

1. 引入 `spring-boot-starter-aop`
2. 编写切面类：类上加 `@Component` + `@Aspect`
3. 在通知方法上用切入点表达式指定要增强的方法

示例 [`TimeAspect`](TimeAspect.java)：用 `@Around` 统计方法耗时。

```java
@Around("com.itheima.aop.MyAspect1.pt()")
public Object recordTime(ProceedingJoinPoint joinPoint) throws Throwable {
    long begin = System.currentTimeMillis();
    Object result = joinPoint.proceed(); // 调用原始方法
    log.info(joinPoint.getSignature() + "方法执行耗时, {}", System.currentTimeMillis() - begin);
    return result;
}
```

核心概念：

| 概念 | 含义 |
|------|------|
| 连接点 JoinPoint | 可以被 AOP 控制的方法 |
| 通知 Advice | 抽取出来的共性功能（增强逻辑） |
| 切入点 PointCut | 匹配连接点的条件，决定哪些方法被增强 |
| 切面 Aspect | 通知 + 切入点 |
| 目标对象 Target | 被增强的原始对象 |

---

## 示例对照

| 类 | 主题 | 状态 |
|----|------|------|
| [`TimeAspect`](TimeAspect.java) | 入门：`@Around` 统计耗时，引用其他切面的 `@Pointcut` | `@Aspect` 已注释 |
| [`MyAspect1`](MyAspect1.java) | 五种通知类型 + `@Pointcut` 抽取公共表达式 | `@Aspect` 已注释 |
| [`MyAspect3`](MyAspect3.java) / [`MyAspect4`](MyAspect4.java) / [`MyAspect5`](MyAspect5.java) | 多切面执行顺序，`@Order(3)` / `@Order(1)` / `@Order(2)` | `@Aspect` 已注释 |
| [`MyAspect6`](MyAspect6.java) | `execution` 切入点表达式的各种写法 | `@Aspect` 已注释 |
| [`MyAspect7`](MyAspect7.java) + [`MyLog`](MyLog.java) | `@annotation` 切入点表达式（匹配加了 `@MyLog` 的方法） | `@Aspect` 已注释 |
| [`MyAspect8`](MyAspect8.java) | 连接点 `JoinPoint` / `ProceedingJoinPoint` 获取类名、方法名、参数、返回值 | **当前生效** |

学习某个示例时，打开该类的 `@Aspect` 注解；同时只启用一两个切面，避免日志混在一起。

---

## 通知类型

| 注解 | 执行时机 |
|------|----------|
| `@Around` | 环绕通知，目标方法前后都执行；必须调用 `proceed()` 并返回结果 |
| `@Before` | 前置通知，目标方法前执行 |
| `@After` | 后置通知，目标方法后执行，有无异常都执行 |
| `@AfterReturning` | 返回后通知，目标方法正常返回后执行，有异常不执行 |
| `@AfterThrowing` | 异常后通知，目标方法抛出异常后执行 |

启用 `MyAspect1` 后调用正常方法，日志顺序为：

```text
around before ...
before ...
（目标方法）
afterReturning ...
after ...
around after ...
```

目标方法抛异常时（可打开 `getById` 中的 `1/0`），`afterThrowing` 代替 `afterReturning` 执行，`around after` 不执行。

多个通知使用相同表达式时，用 `@Pointcut` 抽取到一个空方法上，再写 `@Before("pt()")` 引用。`public` 修饰的切入点可被其他切面引用（如 `TimeAspect` 引用 `MyAspect1.pt()`）。

---

## 通知顺序

多个切面匹配同一个方法时：

- 默认按切面**类名字母排序**：前置通知类名靠前的先执行，后置通知类名靠前的后执行
- 用 `@Order(数字)` 控制：前置通知数字小的先执行，后置通知数字小的后执行

`MyAspect3/4/5` 同时启用时，预期日志：

```text
before ...4   # @Order(1)
before ...5   # @Order(2)
before ...3   # @Order(3)
（目标方法）
after ...3
after ...5
after ...4
```

---

## 切入点表达式

### execution

按方法签名匹配：

```text
execution(访问修饰符? 返回值 包名.类名.?方法名(方法参数) throws 异常?)
```

- 带 `?` 的部分可省略；包名.类名不建议省略
- `*`：单个任意符号，可匹配任意返回值、包名、类名、方法名或一个参数
- `..`：多个连续任意符号，可匹配任意层级的包或任意个参数
- 多个表达式可用 `||`、`&&`、`!` 组合

`MyAspect6` 中保留了从精确到宽泛的多种写法，当前生效的是：

```java
@Pointcut("execution(* com.itheima.service.DeptService.list()) || " +
          "execution(* com.itheima.service.DeptService.delete(java.lang.Integer))")
```

> `execution(* *(..))` 会匹配所有方法，慎用。

### @annotation

按方法上的注解匹配，适合要增强的方法没有统一命名规则的场景：

1. 自定义注解 `MyLog`（`@Retention(RUNTIME)`、`@Target(METHOD)`）
2. 在目标方法上加 `@MyLog`（当前加在 `DeptServiceImpl.delete` 上）
3. 切面中写 `@Pointcut("@annotation(com.itheima.aop.MyLog)")`

---

## 连接点

| 类型 | 适用通知 | 说明 |
|------|----------|------|
| `ProceedingJoinPoint` | 仅 `@Around` | 可调用 `proceed()` 执行目标方法 |
| `JoinPoint` | 其他四种通知 | `ProceedingJoinPoint` 的父类型 |

`MyAspect8` 演示的常用 API：

| 代码 | 获取内容 |
|------|----------|
| `joinPoint.getTarget().getClass().getName()` | 目标类名 |
| `joinPoint.getSignature().getName()` | 目标方法名 |
| `joinPoint.getArgs()` | 方法参数 |
| `joinPoint.proceed()` 的返回值 | 方法返回值 |

---

## 运行观察

1. 确认本地 MySQL 已执行 Tlias 的 [`tlias.sql`](../../../../../../../tlias-web-management/src/main/resources/SQL/tlias.sql)，并修改 `application.yml` 中的账号
2. 运行 `SpringbootAopQuickstart1ApplicationTests` 中的测试：
   - `TestAopList`：调用 `list()`
   - `testAopGetById`：调用 `getById(1)`
   - `testAopDelete`：调用 `delete(10)`，会真实删除 id 为 10 的部门
3. 查看控制台中 `MyAspect8` 打印的类名、方法名、参数与返回值

---

## 相关链接

- 上级模块：[DJavaWeb/README.md](../../../../../../../README.md)
- AOP 实战（Tlias 操作日志 `@Log` + `LogAspect`）：[tlias-web-management/README.md](../../../../../../../tlias-web-management/README.md)
- 全仓库文档索引：[docs/README.md](../../../../../../../../docs/README.md)
