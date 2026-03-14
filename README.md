# 信息咨询系统

一个完整的信息咨询平台，包含微信小程序端、Vue3管理后台和SpringBoot后端服务。

## 项目结构

```
InformationSearch/
├── database/           # 数据库文件
│   ├── schema.sql     # 数据库表结构
│   ├── schema_part2.sql
│   └── test_data.sql  # 测试数据
├── server/            # SpringBoot后端
│   ├── src/
│   └── pom.xml
├── client/            # Vue3管理后台
│   ├── src/
│   ├── package.json
│   └── vite.config.js
└── weixin/            # 微信小程序
    ├── pages/
    ├── utils/
    ├── app.js
    └── app.json
```

## 技术栈

### 后端
- SpringBoot 3.2.3
- MyBatis Plus 3.5.5
- MySQL 8.0
- Redis
- JWT认证
- 微信小程序SDK

### 管理端
- Vue 3.4
- Element Plus 2.6
- Vite 5.1
- Axios
- Vue Router
- Pinia

### 小程序端
- 微信小程序原生开发
- 微信官方登录接口

## 功能特性

### 用户端（小程序）
- ✅ 微信登录
- ✅ 首页轮播图
- ✅ 文章列表（分页加载）
- ✅ 文章详情
- ✅ 文章搜索
- ✅ 分类浏览
- ✅ 点赞、收藏、分享
- ✅ 浏览历史
- ✅ 热门推荐

### 管理端（Web后台）
- ✅ 管理员登录
- ✅ 数据看板
- ✅ 文章管理（增删改查、发布）
- ✅ 富文本编辑
- ✅ 分类管理（多级分类）
- ✅ 标签管理
- ✅ 用户管理
- ✅ 权限管理
- ✅ 操作日志

## 快速开始

### 1. 数据库初始化

**方法一：使用PowerShell（Windows推荐）**

```powershell
# 进入数据库目录
cd database

# 导入数据库结构
Get-Content schema.sql | mysql -u root -p

# 导入第二部分表结构
Get-Content schema_part2.sql | mysql -u root -p

# 导入测试数据
Get-Content test_data.sql | mysql -u root -p info_consultation
```

**方法二：使用CMD命令行**

```cmd
cd database
mysql -u root -p < schema.sql
mysql -u root -p < schema_part2.sql
mysql -u root -p info_consultation < test_data.sql
```

**方法三：使用MySQL客户端工具**

1. 打开Navicat、MySQL Workbench或其他MySQL客户端
2. 连接到MySQL服务器
3. 依次执行以下SQL文件：
   - database/schema.sql
   - database/schema_part2.sql
   - database/test_data.sql

**方法四：直接使用mysql命令（推荐）**

```powershell
# 登录MySQL后执行
mysql -u root -p
# 输入密码后，在MySQL命令行中执行：
source D:/InformationSearch/database/schema.sql;
source D:/InformationSearch/database/schema_part2.sql;
source D:/InformationSearch/database/test_data.sql;
```

### 2. 启动后端服务

```bash
cd server
# 修改 application.properties 中的数据库配置
# 启动SpringBoot应用
mvn spring-boot:run
```

后端服务将运行在 http://localhost:8080

### 3. 启动管理后台

```bash
cd client
# 安装依赖
npm install
# 启动开发服务器
npm run dev
```

管理后台将运行在 http://localhost:3000

默认管理员账号：
- 用户名：admin
- 密码：123456

### 4. 配置微信小程序

1. 使用微信开发者工具打开 `weixin` 目录
2. 修改 `project.config.json` 中的 `appid`
3. 修改 `app.js` 中的 `baseUrl` 为你的后端地址
4. 在微信公众平台配置服务器域名

## 配置说明

### 后端配置 (server/src/main/resources/application.properties)

```properties
# 数据库配置
spring.datasource.url=jdbc:mysql://localhost:3306/info_consultation
spring.datasource.username=root
spring.datasource.password=123456

# Redis配置
spring.data.redis.host=localhost
spring.data.redis.port=6379

# 微信小程序配置
wx.miniapp.appid=你的小程序AppID
wx.miniapp.secret=你的小程序Secret

# 文件上传路径
file.upload.path=D:/upload/
```

### 前端配置 (client/vite.config.js)

```javascript
server: {
  port: 3000,
  proxy: {
    '/api': {
      target: 'http://localhost:8080',
      changeOrigin: true
    }
  }
}
```

### 小程序配置 (weixin/app.js)

```javascript
globalData: {
  baseUrl: 'http://localhost:8080/api/miniapp'
}
```

## API接口文档

### 小程序端接口

#### 轮播图
- GET `/api/miniapp/banner/list` - 获取轮播图列表

#### 文章
- GET `/api/miniapp/article/list` - 获取文章列表
- GET `/api/miniapp/article/detail/{id}` - 获取文章详情
- GET `/api/miniapp/article/hot` - 获取热门文章
- GET `/api/miniapp/article/recommend` - 获取推荐文章
- POST `/api/miniapp/article/like/{id}` - 点赞文章
- POST `/api/miniapp/article/collect/{id}` - 收藏文章
- POST `/api/miniapp/article/share/{id}` - 分享文章

#### 分类
- GET `/api/miniapp/category/list` - 获取分类列表
- GET `/api/miniapp/category/tree` - 获取分类树

### 管理端接口

#### 文章管理
- GET `/api/admin/article/list` - 获取文章列表
- GET `/api/admin/article/detail/{id}` - 获取文章详情
- POST `/api/admin/article/add` - 新增文章
- PUT `/api/admin/article/update` - 更新文章
- DELETE `/api/admin/article/delete/{id}` - 删除文章
- PUT `/api/admin/article/publish/{id}` - 发布文章

#### 分类管理
- GET `/api/admin/category/list` - 获取分类列表
- POST `/api/admin/category/add` - 新增分类
- PUT `/api/admin/category/update` - 更新分类
- DELETE `/api/admin/category/delete/{id}` - 删除分类

## 数据库表说明

- `user` - 用户表
- `admin` - 管理员表
- `role` - 角色表
- `permission` - 权限表
- `article` - 文章表
- `category` - 分类表
- `tag` - 标签表
- `banner` - 轮播图表
- `user_like` - 用户点赞表
- `user_collect` - 用户收藏表
- `user_history` - 浏览历史表
- `comment` - 评论表
- `visit_stats` - 访问统计表
- `operation_log` - 操作日志表

## 常见问题

### 1. PowerShell导入SQL文件报错
如果遇到 `"<"运算符是为将来使用而保留的` 错误，请使用以下命令：
```powershell
Get-Content database/schema.sql | mysql -u root -p
```

### 2. MySQL连接失败
- 检查MySQL服务是否启动
- 确认用户名和密码是否正确
- 检查端口是否为3306

### 3. 前端启动失败
- 确保Node.js版本 >= 16
- 删除node_modules后重新安装：`rm -rf node_modules && npm install`

## 注意事项

1. 确保MySQL 8.0已安装并运行
2. 确保Redis已安装并运行（可选）
3. 修改配置文件中的数据库连接信息
4. 小程序需要在微信公众平台配置合法域名
5. 生产环境请修改JWT密钥和微信小程序配置

## 开发建议

1. 后端开发建议使用IDEA
2. 前端开发建议使用VSCode
3. 小程序开发使用微信开发者工具
4. 建议先启动后端，再启动前端和小程序

## 许可证

MIT License
