# Interceptor（拦截器）

Tlias 工程中的 Spring MVC 拦截器入门示例，对应源码 [`LoginCheckInterceptor`](LoginCheckInterceptor.java)，注册配置见 [`WebConfig`](../config/WebConfig.java)。

上级说明见 [tlias-web-management/README.md](../../../../../../README.md)。

---

## 快速入门

Spring MVC 的 `HandlerInterceptor` 接口常用三个方法：

| 方法 | 调用时机 | 本示例 |
|------|----------|--------|
| `preHandle` | 目标资源方法（Controller）运行前 | 打印 `preHandle ...`，返回 `true` 表示放行 |
| `postHandle` | Controller 运行后、视图渲染前 | 打印 `postHandle ...` |
| `afterCompletion` | 视图渲染完毕、整个请求结束前，最后执行 | 打印 `afterCompletion ...` |

注册方式：写一个配置类实现 `WebMvcConfigurer`，重写 `addInterceptors(InterceptorRegistry registry)`，再用 `registry.addInterceptor(...)` 注册拦截器并配置路径（本示例见 `WebConfig`）。

与 Filter 不同，拦截器类可以声明为 `@Component`，由 Spring 容器管理，因此能直接注入其他 Bean。

> `preHandle` 返回 `false` 时请求被拦截，后续的 `postHandle`、`afterCompletion` 都不会执行。当前示例固定返回 `true`，登录校验逻辑尚未补充。

---

## 执行流程

一次请求在 Spring MVC 中的顺序（与 Filter 对照）：

```text
客户端请求
  → Filter（放行前逻辑）
  → filterChain.doFilter(...)
  → DispatcherServlet
      → Interceptor preHandle（返回 true 放行）
      → Controller（目标资源方法）
      → Interceptor postHandle
      → 视图渲染（@RestController 直接返回 JSON，无视图）
      → Interceptor afterCompletion
  → Filter（放行后逻辑）
```

关键点：Filter 在 `DispatcherServlet` 之前执行，拦截器在 `DispatcherServlet` 内部、Controller 之前执行。

同时注册多个拦截器时：`preHandle` 按注册顺序执行，`postHandle` 与 `afterCompletion` 按相反顺序执行。本示例只注册了一个拦截器。

---

## 拦截路径

`WebConfig.addInterceptors` 中的路径规则：

| 写法 | 含义 |
|------|------|
| `/**` | 拦截所有路径（**当前生效**） |
| `/*` | 拦截一级路径 |
| `/login` | 只拦截登录路径 |

当前配置为 `addPathPatterns("/**")` 加 `excludePathPatterns("/login")`：除登录接口外全部拦截。

登录接口必须放行，否则 `POST /login` 会被拦截，拿不到 JWT，后续需要令牌的请求也就无从发起。

路径匹配中 `/depts/*` 只匹配一级子路径（如 `/depts/1`），`/depts/**` 匹配多级子路径。

---

## 与 Filter 的区别

| 维度 | Filter | Interceptor |
|------|--------|-------------|
| 规范 | Servlet 规范（`javax.servlet.Filter`） | Spring MVC 提供（`HandlerInterceptor`） |
| 生效位置 | `DispatcherServlet` 之前，拦截进入容器的请求 | `DispatcherServlet` 之后、Controller 之前 |
| 注册方式 | `@WebFilter` + `@ServletComponentScan`，或 `FilterRegistrationBean` | 实现 `WebMvcConfigurer#addInterceptors` |
| 能否注入 Spring Bean | 注解注册时由 Servlet 容器创建，不是 Spring Bean，不能直接 `@Autowired` | 声明为 `@Component`，由 Spring 管理，可注入依赖 |
| 典型用途 | 编码、跨域、日志、粗粒度鉴权 | 登录校验、权限、日志（能拿到 Handler 等 MVC 信息） |

本工程两种登录校验写法都保留着：Filter 版 [`LoginCheckFilter`](../Filter/LoginCheckFilter.java) 的 `@WebFilter` 已注释关闭，当前生效的是拦截器版 `LoginCheckInterceptor`。学习时二选一启用，避免同一请求被重复校验。

---

## 运行观察

1. 启动 Tlias：在 `tlias-web-management` 目录执行 `mvn spring-boot:run`（默认端口 `8080`）
2. 请求任意业务接口，例如 `GET http://localhost:8080/depts`
3. 控制台应依次出现以下拦截器日志（若 Filter 示例的 `AbcFilter`、`DemoFilter` 仍启用，前后还会夹着它们的「放行前 / 放行后」日志）：

```text
preHandle ...
postHandle ...
afterCompletion ...
```

4. 请求登录接口 `POST http://localhost:8080/login`，请求体示例（账号来自 `tlias.sql` 初始化数据）：

```json
{"username": "jinyong", "password": "123456"}
```

因为 `/login` 在放行名单中，此时不会打印上述拦截器日志，但接口会正常返回 JWT。

当前 `preHandle` 只打印日志并固定返回 `true`。后续补充登录校验时，可参考 [`JwtUtils`](../utils/JwtUtils.java) 解析请求头中的令牌；校验失败时手动把 `Result.error("NOT_LOGIN")` 写成 JSON 返回（参考 `LoginCheckFilter` 的处理方式）。

---

## 相关链接

- 上级模块：[tlias-web-management/README.md](../../../../../../README.md)
- 注册配置：[WebConfig.java](../config/WebConfig.java)
- Filter 笔记：[Filter/README.md](../Filter/README.md)
- 全仓库文档索引：[docs/README.md](../../../../../../../../docs/README.md)
