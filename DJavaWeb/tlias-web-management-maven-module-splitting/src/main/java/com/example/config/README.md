# config（配置类）

Tlias 工程的 Spring 配置类目录，当前包含 [`WebConfig`](WebConfig.java) 一个配置类。

上级说明见 [tlias-web-management-maven-module-splitting/README.md](../../../../../../README.md)。

---

## WebConfig

`WebConfig` 实现 Spring MVC 的 `WebMvcConfigurer` 接口，负责把拦截器注册进 MVC 拦截器链：

| 配置项 | 当前值 | 说明 |
|--------|--------|------|
| 注册拦截器 | `LoginCheckInterceptor` | `com.example.interceptor` 包中的登录校验拦截器 |
| 拦截路径 | `/**` | 所有请求都进入拦截器 |
| 放行路径 | `/login` | 登录接口不校验令牌，否则无法获取 JWT |

对应源码中的核心配置：

```java
registry.addInterceptor(loginCheckInterceptor)
        .addPathPatterns("/**")
        .excludePathPatterns("/login");
```

`WebConfig` 只负责注册，不包含校验逻辑；拦截器的三个回调方法、执行流程与路径规则见 [interceptor/README.md](../interceptor/README.md)。Filter 版登录校验见 [LoginCheckFilter.java](../Filter/LoginCheckFilter.java)。

---

## 扩展说明

- 新增拦截器：在 `addInterceptors(InterceptorRegistry registry)` 中继续调用 `registry.addInterceptor(...)`。注册顺序决定 `preHandle` 的执行顺序（`postHandle`、`afterCompletion` 则相反）。
- 新增其他 MVC 配置（如跨域、静态资源映射、消息转换器）：在 `WebConfig` 中重写对应的 `WebMvcConfigurer` 方法；配置较多时可按主题拆成多个 `@Configuration` 类。

---

## 相关链接

- 上级模块：[tlias-web-management-maven-module-splitting/README.md](../../../../../../README.md)
- 配置类源码：[WebConfig.java](WebConfig.java)
- 拦截器笔记：[interceptor/README.md](../interceptor/README.md)
- Filter 笔记：[Filter/README.md](../Filter/README.md)
- 全仓库文档索引：[docs/README.md](../../../../../../../../docs/README.md)
