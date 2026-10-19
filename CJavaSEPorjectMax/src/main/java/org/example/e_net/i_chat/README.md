# 综合项目实战：局域网即时通讯

局域网内的群聊沟通软件（Swing + TCP Socket）。本文说明需求、技术选型、架构与运行方式；完整实现见源码，不再内嵌大段代码。

上级模块说明见 [CJavaSEPorjectMax/README.md](../../../../../../../README.md)。

---

## 需求

1. 登录界面：用户只需输入聊天昵称即可进入
2. 群聊窗口：展示在线人数、消息展示区、消息输入框、发送按钮
3. 支持群聊；在线人数实时更新；达到即时通讯效果

---

## 技术选型

1. GUI：Swing
2. 网络编程：TCP `ServerSocket` / `Socket` + 独立读写线程
3. 面向对象设计
4. 常用 API（`DataInputStream` / `DataOutputStream` 等）

默认端口：`6666`（见客户端 / 服务端 `Constant`）。

---

## 架构概览

![chat.png](../../../../../resources/images/chat.png)

**服务端职责**

- 接收客户端连接
- 接收登录消息（昵称）与群聊消息
- 用集合保存全部在线 Socket，便于转发
- 登录成功或有人下线时，向全部客户端推送在线人数列表
- 收到群聊消息后转发给全部在线客户端

**客户端职责**

- 登录后建立 Socket，发送登录消息
- 独立线程读取服务端推送（在线列表 / 群聊）
- 发送按钮将群聊内容发往服务端

---

## 消息协议约定

客户端先发送一个 `int` 类型编号，再发送后续数据：

| 类型 | 含义 | 后续数据 |
|------|------|----------|
| `1` | 登录 | 昵称（`UTF`） |
| `2` | 群聊 | 消息内容（`UTF`） |
| `3` | 私聊（预留） | — |

服务端下行同样用类型区分：

| 类型 | 含义 |
|------|------|
| `1` | 在线人数列表（先发人数，再逐个发昵称） |
| `2` | 群聊消息文本（含昵称与时间戳拼接） |

---

## 源码位置

| 角色 | 类 | 路径要点 |
|------|----|----------|
| 客户端启动 | `App` | `org.example.e_net.i_chat.com.example.App` |
| 登录界面 | `ChatEntryFrame` | `...i_chat.com.example.ui.ChatEntryFrame` |
| 聊天界面 | `ClientChatFrame` | `...i_chat.com.example.ui.ClientChatFrame` |
| 客户端读线程 | `ClientReaderThread` | `...i_chat.com.example.ui.ClientReaderThread` |
| 服务端启动 | `Server` | `org.example.e_net.j_chat_server.com.example.Server` |
| 服务端读线程 | `ServerReaderThread` | `...j_chat_server.com.example.ServerReaderThread` |
| 端口常量 | `Constant` | 客户端与服务端各有一份，端口需一致 |

实现思路摘要：

1. 服务端 `accept` 后交给 `ServerReaderThread`，用 `Map<Socket, String>` 记录在线用户
2. 客户端登录成功后关闭登录窗，打开聊天窗，并启动读线程监听服务端消息
3. 发送群聊：客户端发 `2` + 文本；服务端拼装「昵称 + 时间 + 内容」后广播

---

## 运行步骤

1. 先运行服务端：`org.example.e_net.j_chat_server.com.example.Server`
2. 再运行客户端：`org.example.e_net.i_chat.com.example.App`
3. 输入昵称进入；可再启多个客户端验证在线列表与群聊

---

## 相关链接

- 上级模块：[CJavaSEPorjectMax/README.md](../../../../../../../README.md)
- 全仓库文档索引：[docs/README.md](../../../../../../../../docs/README.md)
