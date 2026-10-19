# Filter（过滤器）

Tlias 工程中的 Servlet Filter 入门示例，对应源码 [`DemoFilter`](DemoFilter.java)、[`AbcFilter`](AbcFilter.java)。

上级说明见 [tlias-web-management/README.md](../../../../../../README.md)。启用注解式 Filter 需在启动类上保留 `@ServletComponentScan`（见 `TliasWebManagementApplication`）。

---

## 快速入门

`javax.servlet.Filter` 接口常用三个方法：

| 方法 | 调用时机 | 本示例 |
|------|----------|--------|
| `init` | 容器启动时调用一次 | 仅 `DemoFilter` 打印初始化日志 |
| `doFilter` | 每次匹配到的请求都会调用 | 两个 Filter 均有「放行前 / 放行后」日志 |
| `destroy` | 容器销毁时调用一次 | 仅 `DemoFilter` 打印销毁日志 |

注册方式：类上加 `@WebFilter(urlPatterns = "...")`，并由 `@ServletComponentScan` 扫描进 Spring Boot。

当前两个类都拦截 `/*`，请求会进入过滤器链后再到达 Controller。

---

## 执行流程

一次 HTTP 请求的大致顺序：

```text
客户端请求
  → Filter（放行前逻辑）
  → filterChain.doFilter(...)
  → 后续 Filter / Controller
  → 原路返回
  → Filter（放行后逻辑）
```

对照 `DemoFilter.doFilter`：

1. 打印「放行前的逻辑」
2. 调用 `filterChain.doFilter(request, response)` 放行
3. 下游处理结束后，再打印「放行后的逻辑」

未调用 `filterChain.doFilter` 时，请求不会继续往后走（可用于鉴权拦截等场景；本示例始终放行）。

---

## 拦截路径

`@WebFilter` 的 `urlPatterns` 决定拦截范围。`DemoFilter` 源码中保留了三种写法（注释切换即可）：

| 写法 | 含义 |
|------|------|
| `/*` | 拦截所有路径（**当前生效**） |
| `/login` | 只拦截登录路径 |
| `/depts/*` | 拦截 `/depts` 下的子路径 |

`AbcFilter` 当前同样为 `/*`。

---

## 过滤器链

一个 Web 应用里可配置多个 Filter，它们按顺序组成过滤器链。

**注解配置时的顺序**：按过滤器**类名字符串的自然排序**。本包中：

- `AbcFilter` 排在 `DemoFilter` 之前

因此访问任意接口（如 `GET /depts`）时，控制台预期顺序为：

```text
Abc 拦截到了请求...放行前的逻辑
Demo 拦截到了请求...放行前的逻辑
（Controller 处理）
Demo 拦截到了请求...放行后的逻辑
Abc 拦截到了请求...放行后的逻辑
```

（若刚启动过应用，还可能先看到 `Demo init 初始化方法执行了`。）

---

## 运行观察

1. 启动 Tlias：在 `tlias-web-management` 目录执行 `mvn spring-boot:run`（默认端口 `8080`）
2. 请求任意业务接口，例如：`GET http://localhost:8080/depts`
3. 查看控制台是否出现上述 Abc → Demo 的放行前 / 放行后日志

学习时若要临时关闭某个 Filter：注释掉该类上的 `@WebFilter` 后重启即可。观察完毕后恢复注解，避免把学习用改动当成业务必改项提交。

---

## 相关链接

- 上级模块：[tlias-web-management/README.md](../../../../../../README.md)
- 启动类：`com.example.TliasWebManagementApplication`（含 `@ServletComponentScan`）
- 全仓库文档索引：[docs/README.md](../../../../../../../../docs/README.md)
