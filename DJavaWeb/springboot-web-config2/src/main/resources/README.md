# Bean 管理与 SpringBoot 原理

本模块演示 Spring IOC 容器中 Bean 的获取、作用域、第三方 Bean 的声明方式，以及 SpringBoot 起步依赖与自动配置原理。

- Bean 管理示例：`SpringbootWebConfig2ApplicationTests`
- 自动配置示例：`AutoConfigurationTests`，依赖同目录下的 [`itheima-utils`](../../../../itheima-utils) 模块

上级说明见 [DJavaWeb/README.md](../../../../README.md)。

## 获取 bean

先注入 IOC 容器对象 `ApplicationContext`，再通过 `getBean` 获取：

```java
@Autowired
private ApplicationContext applicationContext;
```

| 方式 | 写法 | 说明 |
| --- | --- | --- |
| 根据名称 | `getBean("deptController")` | 返回 `Object`，需要强转 |
| 根据类型 | `getBean(DeptController.class)` | 同类型 Bean 有多个时会报错 |
| 根据名称和类型 | `getBean("deptController", DeptController.class)` | 无需强转 |

- 通过 `@Component`、`@Controller`、`@Service`、`@Repository` 声明的 Bean，默认名称是**类名首字母小写**，如 `DeptController` → `deptController`。
- 默认的单例 Bean 在容器启动时就会创建，所以启动时就能看到构造方法的输出。

## bean 的作用域

通过 `@Scope` 指定作用域：

| 作用域 | 说明 |
| --- | --- |
| `singleton` | 容器内同名 Bean 只有一个实例（默认） |
| `prototype` | 每次获取都创建新实例（多例） |
| `request` | 每个 HTTP 请求一个实例（Web 环境） |
| `session` | 每个会话一个实例（Web 环境） |
| `application` | 每个应用一个实例（Web 环境） |

本模块中 `DeptController` 配置为多例：

```java
@Scope("prototype")
@RestController
@RequestMapping("/depts")
public class DeptController { ... }
```

`testScope` 中循环获取 10 次 `DeptController`，每次打印的对象地址都不同，构造方法也执行 10 次。去掉 `@Scope("prototype")` 后，10 次拿到的是同一个对象。

- `@Lazy`：延迟初始化，单例 Bean 推迟到第一次使用时才创建。
- 实际开发中绝大多数 Bean 都是单例，无需配置 `@Scope`。

## 第三方 bean 的管理

第三方依赖中的类（如 dom4j 的 `SAXReader`）无法在源码上加 `@Component`，需要用 `@Bean` 声明。

推荐在 `config` 包下的配置类中集中声明，而不是写在启动类里：

```java
@Configuration // 配置类
public class CommonConfig {

    @Bean // 将方法返回值交给 IOC 容器管理
    public SAXReader reader(DeptService deptService) {
        System.out.println(deptService);
        return new SAXReader();
    }
}
```

- Bean 名称默认是**方法名**（这里是 `reader`），可通过 `@Bean("xxx")` 或 `@Bean(name = "xxx")` 指定。
- 方法需要依赖其他 Bean 时，直接在参数中声明，容器会根据类型自动注入（如上面的 `DeptService`）。

使用时直接注入即可，不需要再 `new`：

```java
@Autowired
private SAXReader saxReader;
```

`testThirdBean` 用注入的 `saxReader` 解析 `resources/1.xml`，输出 `Tom : 18`。`testGetBean2` 通过名称 `reader` 从容器中获取该 Bean。

### 如何选择

| 场景 | 方式 |
| --- | --- |
| 项目中自己定义的类 | `@Component` 及其衍生注解 |
| 第三方依赖中的类 | `@Configuration` + `@Bean` |

---

## SpringBoot 原理

### 起步依赖

起步依赖（如 `spring-boot-starter-web`）本质是 Maven 依赖传递：引入一个 starter，它所需的 Spring MVC、Tomcat、Jackson 等依赖会被自动引入，版本由 Spring Boot 父工程统一管理。

### 自动配置

自动配置：Spring 容器启动后，一些配置类、Bean 对象自动存入 IOC 容器，无需手动声明，从而简化开发、省去繁琐的配置。

例如 `AutoConfigurationTests.testJson` 中直接注入了 `Gson`。项目没有声明过它：`gson` 由 `aliyun-sdk-oss` 依赖传递引入，Spring Boot 检测到类路径中有 Gson 后自动配置了这个 Bean。

### 加载第三方 jar 中的 Bean

`itheima-utils` 中的 `TokenParser`（`@Component`）和 `HeaderConfig`（`@Configuration`）位于 `com.example` 包。启动类在 `com.itheima` 包，`@SpringBootApplication` 默认只扫描 `com.itheima` 及其子包，所以这些 Bean 不会生效，测试报 `NoSuchBeanDefinitionException`。

解决方案（启动类 `SpringbootWebConfig2Application` 中保留了全部写法）：

| 方案 | 写法 | 说明 |
|------|------|------|
| 组件扫描 | `@ComponentScan({"com.itheima", "com.example"})` | 需要知道第三方包名，扫描范围大，不推荐 |
| 导入普通类 | `@Import(TokenParser.class)` | 直接把类注册为 Bean |
| 导入配置类 | `@Import(HeaderConfig.class)` | 配置类中的 `@Bean` 全部生效 |
| 导入 `ImportSelector` | `@Import(MyImportSelector.class)` | `selectImports` 返回要导入的类全名数组 |
| `@EnableXxx` 注解 | `@EnableHeaderConfig` | 第三方封装好的 `@Import`，使用方最省事，**推荐** |

当前启动类使用的是 `@EnableHeaderConfig` + `@Import(TokenParser.class)`：

- `@EnableHeaderConfig` 内部通过 `MyImportSelector` 导入 `HeaderConfig`，注册 `HeaderParser`、`HeaderGenerator`
- `HeaderConfig` 没有声明 `TokenParser`，需要单独 `@Import`

### 运行自动配置示例

1. 先安装 `itheima-utils` 到本地仓库：

   ```bash
   cd DJavaWeb/itheima-utils
   mvn install
   ```

2. 在 IDEA 中 Reload Maven 项目，运行 `AutoConfigurationTests`，4 个测试分别从容器中获取 `Gson`、`TokenParser`、`HeaderParser`、`HeaderGenerator`

> `itheima-utils` 已在 `DJavaWeb/pom.xml` 的 `<modules>` 中。在 `DJavaWeb` 目录执行 `mvn install` 时会按依赖顺序一起构建，无需单独安装。
