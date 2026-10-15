# Tlias 智能学习辅助系统 API

本文档以当前 `tlias-web-management` 源码为准，维护基线为 **2026-10-15**。统一响应结构如下：

```json
{
  "code": 1,
  "msg": "success",
  "data": null
}
```

`code=1` 表示成功，`code=0` 表示失败。当前项目没有实现登录、JWT 或权限拦截，旧版课程资料中的 `/login` 不属于当前接口。

## 1. 部门管理

### 查询全部部门

```http
GET /depts
```

成功时 `data` 为部门数组，每项包含 `id`、`name`、`createTime`、`updateTime`。

### 新增部门

```http
POST /depts
Content-Type: application/json

{"name":"教研部"}
```

### 查询单个部门

```http
GET /depts/{id}
```

### 修改部门

```http
PUT /depts
Content-Type: application/json

{"id":1,"name":"教研部"}
```

### 删除部门

```http
DELETE /depts/{id}
```

## 2. 员工管理

### 分页与条件查询

```http
GET /emps?page=1&pageSize=10&name=张&gender=1&begin=2020-01-01&end=2026-12-31
```

所有查询参数均由 Controller 提供默认值或可选：`page` 默认 `1`，`pageSize` 默认 `10`，`name`、`gender`、`begin`、`end` 可省略。日期格式为 `yyyy-MM-dd`。

成功时 `data` 结构如下：

```json
{
  "total": 1,
  "rows": [
    {
      "id": 1,
      "username": "jinyong",
      "name": "金庸",
      "gender": 1,
      "image": "https://example.com/avatar.jpg",
      "job": 2,
      "entrydate": "2015-01-01",
      "deptId": 2,
      "createTime": "2026-10-15T10:00:00",
      "updateTime": "2026-10-15T10:00:00"
    }
  ]
}
```

### 新增员工

```http
POST /emps
Content-Type: application/json

{
  "username": "linpingzhi",
  "name": "林平之",
  "gender": 1,
  "image": "https://example.com/avatar.jpg",
  "job": 1,
  "entrydate": "2026-10-15",
  "deptId": 1
}
```

### 查询单个员工

```http
GET /emps/{id}
```

### 修改员工

```http
PUT /emps
Content-Type: application/json

{
  "id": 1,
  "username": "linpingzhi",
  "name": "林平之",
  "gender": 1,
  "image": "https://example.com/avatar.jpg",
  "job": 1,
  "entrydate": "2026-10-15",
  "deptId": 1
}
```

### 批量删除员工

```http
DELETE /emps/1,2,3
```

## 3. 图片上传

```http
POST /upload
Content-Type: multipart/form-data

image=<file>
```

成功时 `data` 为 OSS 图片访问地址：

```json
{
  "code": 1,
  "msg": "success",
  "data": "https://your-bucket.oss-cn-example.aliyuncs.com/uuid.jpg"
}
```

上传功能读取 `OSS_ACCESS_KEY_ID` 和 `OSS_ACCESS_KEY_SECRET` 环境变量，并使用 `application.yml` 中的 OSS endpoint、bucket 和 region 配置。

## 4. 领域字段

| 字段 | 说明 |
|------|------|
| `gender` | `1` 男，`2` 女 |
| `job` | `1` 班主任，`2` 讲师，`3` 学工主管，`4` 教研主管，`5` 咨询师 |
| `entrydate` | 入职日期，格式 `yyyy-MM-dd` |
| `createTime` / `updateTime` | 服务端维护的时间字段 |
