# Bean 管理

本模块演示 Spring IOC 容器中 Bean 的获取、作用域，以及第三方 Bean 的声明方式。示例代码见 `SpringbootWebConfig2ApplicationTests`。

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
