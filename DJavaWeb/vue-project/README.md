# vue-project

黑马 JavaWeb 课程配套前端：Vue 2 + Element UI + Vue Router + Axios，实现 Tlias 风格的部门 / 员工管理页。

上级模块说明见 [DJavaWeb/README.md](../README.md)。

---

## 环境要求

- **Node.js LTS**（含 npm）
- 建议使用与课程相近的 Node 版本，避免过新导致 Vue CLI 兼容问题

主要依赖：Vue `2.6`、Vue Router `3`、Element UI `2`、Axios。

---

## 安装与运行

```bash
cd DJavaWeb/vue-project
npm install
npm run serve
```

其他常用命令：

| 命令 | 说明 |
|------|------|
| `npm run serve` | 开发模式，热更新 |
| `npm run build` | 生产构建 |
| `npm run lint` | ESLint 检查与修复 |

---

## 路由说明

| 路径 | 说明 |
|------|------|
| `/dept` | 部门管理（默认入口） |
| `/emp` | 员工管理 |

配置细节可参考 [Vue CLI Configuration Reference](https://cli.vuejs.org/config/)。
