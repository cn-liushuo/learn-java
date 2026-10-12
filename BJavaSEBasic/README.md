# BJavaSEBasic

Java SE 基础练习模块（Maven / JDK 8）：语法、面向对象、Swing GUI，以及员工管理系统、石头迷宫两个小项目。

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
| Lombok | `1.18.46` | provided，简化实体样板代码 |

---

## 安装与运行

```bash
cd BJavaSEBasic
mvn compile
```

- 基础示例：按下方包路径打开对应类，运行 `main`
- 员工管理系统：`org.example.i_employee_sys.App`
- 石头迷宫：`org.example.j_stone_maze.App`（方向键移动，挑战最少步数）

---

## 包学习路径

源码根包：`org.example`

| 包名 | 主题 |
|------|------|
| `a_helloword_app` | HelloWorld、快捷键、注释、字面量、变量 |
| `b_basic_app` | 方法、类型、Scanner、运算符 |
| `c_flow_control` | 分支、循环 |
| `d_array` | 数组 |
| `e_oop` ~ `h_oop` | OOP（封装 / 继承 / 多态 / 接口 / 内部类 / Lambda）、GUI |
| `i_employee_sys` | 员工管理系统 |
| `j_stone_maze` | 石头迷宫 |

建议按包名字母顺序学习：先语法与流程控制，再 OOP，最后做两个综合小项目。

---

## 使用说明

1. IDE 打开本模块，等待依赖下载完成
2. 从 `a_helloword_app` 起按包浏览，逐个运行示例类
3. 完成 OOP 后再运行 `i_employee_sys`、`j_stone_maze` 巩固

---

## 相关链接

- 进阶模块：[CJavaSEPorjectMax](../CJavaSEPorjectMax/README.md)
