# 小程序图标说明

由于无法直接生成图片文件，请按照以下步骤添加TabBar图标：

## 需要的图标文件：

在 `weixin/images/` 目录下需要以下6个图标文件：

1. `home.png` - 首页图标（未选中）
2. `home-active.png` - 首页图标（选中）
3. `category.png` - 分类图标（未选中）
4. `category-active.png` - 分类图标（选中）
5. `my.png` - 我的图标（未选中）
6. `my-active.png` - 我的图标（选中）

## 图标规格：

- 尺寸：81px * 81px
- 格式：PNG
- 背景：透明
- 未选中状态：灰色 (#999999)
- 选中状态：蓝色 (#6366f1)

## 获取图标的方法：

### 方法1：使用iconfont（推荐）
1. 访问 https://www.iconfont.cn/
2. 搜索 "home"、"category"、"user" 等关键词
3. 下载PNG格式，调整颜色和尺寸

### 方法2：使用在线图标生成器
1. 访问 https://icon-icons.com/
2. 搜索需要的图标
3. 下载并调整大小

### 方法3：临时解决方案
如果暂时没有图标，可以修改 `app.json`，使用文字代替图标：

```json
"tabBar": {
  "color": "#999999",
  "selectedColor": "#6366f1",
  "backgroundColor": "#ffffff",
  "borderStyle": "black",
  "list": [
    {
      "pagePath": "pages/index/index",
      "text": "首页"
    },
    {
      "pagePath": "pages/category/category",
      "text": "分类"
    },
    {
      "pagePath": "pages/my/my",
      "text": "我的"
    }
  ]
}
```

注意：去掉 `iconPath` 和 `selectedIconPath` 字段，只保留 `text`。

## 快速解决方案：

我建议你现在先修改 `app.json`，去掉图标配置，只使用文字。这样小程序就能正常显示了。
