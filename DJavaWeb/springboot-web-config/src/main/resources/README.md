# Web 后端开发配置

本模块用于演示 Spring Boot（2.6.13）中各种配置方式的写法及其优先级，演示项为 `server.port`（服务端口）。

## 配置文件格式

Spring Boot 支持三种配置文件，位于 `src/main/resources` 下：

| 文件 | 写法示例 | 本模块演示端口 |
| --- | --- | --- |
| `application.properties` | `server.port=8081` | 8081 |
| `application.yml` | 见下方 | 8082 |
| `application.yaml` | 见下方 | 8083 |

`yml` / `yaml` 写法：

```yaml
server:
  port: 8082
```

YAML 语法要点：

- 大小写敏感。
- 用缩进表示层级关系，缩进只能用空格，不能用 Tab。
- 冒号 `:` 后必须有一个空格。
- `#` 表示注释。

> 实际开发中通常只保留一种格式，推荐使用 `application.yml`。

## 优先级

从高到低：

1. 命令行参数（`--xxx=xxx`）
2. Java 系统属性（`-Dxxx=xxx`）
3. `application.properties`
4. `application.yml`
5. `application.yaml`

高优先级的配置会覆盖低优先级中的同名配置项。

### Java 系统属性

格式为 `-D属性名=值`，写在 `java` 命令与 `-jar` 之间：

```bash
java -Dserver.port=9000 -jar springboot-web-config-0.0.1-SNAPSHOT.jar
```

IDEA 中：Run/Debug Configurations → Modify options → Add VM options，填入 `-Dserver.port=9000`。

### 命令行参数

格式为 `--属性名=值`，写在 jar 包名之后：

```bash
java -jar springboot-web-config-0.0.1-SNAPSHOT.jar --server.port=10010
```

IDEA 中：Run/Debug Configurations → Program arguments，填入 `--server.port=10010`。

两者同时指定时，命令行参数生效：

```bash
# 最终端口为 10010
java -Dserver.port=9000 -jar springboot-web-config-0.0.1-SNAPSHOT.jar --server.port=10010
```

## 打包运行

```bash
mvn clean package
java -jar target/springboot-web-config-0.0.1-SNAPSHOT.jar
```

> 注意：当前 `pom.xml` 中 `spring-boot-maven-plugin` 配置了 `<skip>true</skip>`，不会生成可执行 jar。需要用 `java -jar` 运行时，先删除该配置或改为 `false`。
