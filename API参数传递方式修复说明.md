# API参数传递方式修复说明

## 问题分析

### 错误信息
```
POST http://localhost:8080/api/miniapp/history/add 400 (Bad Request)
POST http://localhost:8080/api/miniapp/collect/add 400 (Bad Request)
```

### 原因
小程序的API调用使用了POST请求体（data字段）传递参数，但后端期望的是URL查询参数（query string）。

### 解决方案
将所有参数改为URL查询参数的方式传递。

---

## 修改详情

### 修改前（错误方式）
```javascript
const addCollect = (userId, articleId) => {
  return request({
    url: '/collect/add',
    method: 'POST',
    data: { userId, articleId }  // ❌ 错误：参数在请求体中
  })
}
```

### 修改后（正确方式）
```javascript
const addCollect = (userId, articleId) => {
  return request({
    url: `/collect/add?userId=${userId}&articleId=${articleId}`,  // ✅ 正确：参数在URL中
    method: 'POST'
  })
}
```

---

## 修改的API列表

| API | 修改前 | 修改后 |
|-----|--------|--------|
| 添加收藏 | `/collect/add` (POST data) | `/collect/add?userId=X&articleId=Y` (POST) |
| 取消收藏 | `/collect/remove` (DELETE data) | `/collect/remove?userId=X&articleId=Y` (DELETE) |
| 获取收藏列表 | `/collect/list` (GET data) | `/collect/list?userId=X` (GET) |
| 添加浏览历史 | `/history/add` (POST data) | `/history/add?userId=X&articleId=Y` (POST) |
| 获取浏览历史 | `/history/list` (GET data) | `/history/list?userId=X&limit=Y` (GET) |

---

## 现在需要做的事

### 第一步：重新编译小程序
1. 在微信开发者工具中保存文件
2. 或按 `Ctrl + Shift + R` 强制刷新
3. 等待编译完成

### 第二步：重新测试
1. 登录
2. 浏览文章（应该自动记录历史）
3. 收藏文章（应该成功）
4. 查看"我的收藏"（应该显示收藏的文章）
5. 查看"浏览历史"（应该显示浏览过的文章）

### 第三步：观察Console日志
应该看到类似这样的成功日志：
```
浏览历史记录成功
收藏文章: {userId: 9, articleId: 2}
收藏成功
收藏列表: [{id: 1, userId: 9, articleId: 2, createTime: "..."}]
```

### 第四步：在SQLyog中验证
```sql
-- 查看收藏记录
SELECT * FROM user_collect WHERE user_id = 9;

-- 查看浏览历史
SELECT * FROM user_history WHERE user_id = 9;
```

---

## 技术说明

### 为什么要用URL参数而不是请求体？

1. **后端设计**：后端使用 `@RequestParam` 注解接收参数，这是从URL查询参数中获取的
2. **RESTful规范**：GET请求通常使用URL参数，POST请求可以使用请求体，但这里后端期望URL参数
3. **兼容性**：某些情况下，使用URL参数更加兼容和稳定

### 后端代码示例
```java
@PostMapping("/collect/add")
public Result<Void> addCollect(@RequestParam Long userId, @RequestParam Long articleId) {
    // userId 和 articleId 从URL查询参数中获取
    // 例如：/api/miniapp/collect/add?userId=1&articleId=2
    boolean success = userCollectService.addCollect(userId, articleId);
    return success ? Result.success() : Result.error("收藏失败");
}
```

---

## 验证清单

- [ ] 重新编译小程序
- [ ] 登录成功
- [ ] 浏览文章时Console显示"浏览历史记录成功"
- [ ] SQLyog中能看到 `user_history` 表有新记录
- [ ] 收藏文章时Console显示"收藏成功"
- [ ] SQLyog中能看到 `user_collect` 表有新记录
- [ ] "我的收藏"页面显示收藏的文章
- [ ] "浏览历史"页面显示浏览过的文章

---

现在所有API调用都应该正常工作了！
