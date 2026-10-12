# CJavaSEPorjectMax

Java SE 进阶练习模块（Maven / JDK 8）：异常、泛型、集合、Map、Stream、文件 IO、多线程、UDP/TCP、局域网即时通讯，以及 JUnit、反射、注解、动态代理。

配套课程：[黑马程序员 2024 Java 基础](https://www.bilibili.com/video/BV1gb42177hm/?spm_id_from=333.788.video.desc.click)

仓库总览见根目录 [README.md](../README.md)。

---

## 环境要求

1. **JDK 8**
2. **Maven 3.6.1+**
3. IDE：IntelliJ IDEA / Eclipse（使用 Lombok 需开启注解处理）

主要依赖：

| 依赖 | 版本 | 说明 |
|------|------|------|
| Lombok | `1.18.46` | provided |
| commons-io | `2.11.0` | 文件 IO 工具 |
| JUnit | `4.13.2` | 单元测试 |

---

## 安装与运行

```bash
cd CJavaSEPorjectMax
mvn compile
```

按包浏览运行示例类的 `main`。综合项目「局域网即时通讯」见下方说明。

---

## 包学习路径

源码根包：`org.example`

| 包名 | 主题 |
|------|------|
| `a_collection` | 异常、泛型、Collection / List |
| `b_collection_map_stream` | HashSet、Map、Stream |
| `c_file_io` | 文件与各类 IO、commons-io |
| `d_thread` | 线程、同步、Lock、线程池 |
| `e_net` | UDP/TCP、API、聊天室 |
| `e_net.k_junit_reflect_annotation_proxy` | JUnit、反射、注解、动态代理 |

`e_net` 子包概览：

| 子包 | 主题 |
|------|------|
| `a_inetaddress` ~ `h_api` | InetAddress、UDP、TCP 分阶段练习 |
| `i_chat` | 聊天客户端（Swing） |
| `j_chat_server` | 聊天服务端 |
| `k_junit_reflect_annotation_proxy` | 测试 / 反射 / 注解 / 代理 |

---

## 局域网即时通讯（端口 `6666`）

需求、协议与架构说明见：[i_chat/README.md](src/main/java/org/example/e_net/i_chat/README.md)

运行顺序：

1. 先启服务端：`org.example.e_net.j_chat_server.com.example.Server`
2. 再启客户端：`org.example.e_net.i_chat.com.example.App`（昵称登录、在线人数、群聊）

可同时启动多个客户端验证群聊与在线列表更新。

---

## 使用说明

1. 建议学完 [BJavaSEBasic](../BJavaSEBasic/README.md) 后再进入本模块
2. 按 `a_collection` → `e_net` 顺序练习
3. 网络与聊天室完成后再看 `k_junit_reflect_annotation_proxy`

---

## 相关链接

- 下一模块：[ALeetCode](../ALeetCode/README.md)
- 聊天室实战笔记：[i_chat/README.md](src/main/java/org/example/e_net/i_chat/README.md)
