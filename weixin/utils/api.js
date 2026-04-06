const app = getApp()

const request = (options) => {
  return new Promise((resolve, reject) => {
    wx.request({
      url: app.globalData.baseUrl + options.url,
      method: options.method || 'GET',
      data: options.data || {},
      header: {
        'content-type': 'application/json',
        'Authorization': 'Bearer ' + (app.globalData.token || '')
      },
      success: (res) => {
        if (res.data.code === 200) {
          resolve(res.data.data)
        } else {
          wx.showToast({
            title: res.data.message || '请求失败',
            icon: 'none'
          })
          reject(res.data)
        }
      },
      fail: (err) => {
        wx.showToast({
          title: '网络错误',
          icon: 'none'
        })
        reject(err)
      }
    })
  })
}

// 获取轮播图
const getBannerList = () => {
  return request({
    url: '/banner/list'
  })
}

// 获取文章列表
const getArticleList = (params) => {
  return request({
    url: '/article/list',
    data: params
  })
}

// 获取文章详情
const getArticleDetail = (id) => {
  return request({
    url: `/article/detail/${id}`
  })
}

// 获取热门文章
const getHotArticles = (limit = 10) => {
  return request({
    url: '/article/hot',
    data: { limit }
  })
}

// 获取推荐文章
const getRecommendArticles = (limit = 10) => {
  return request({
    url: '/article/recommend',
    data: { limit }
  })
}

// 获取分类列表
const getCategoryList = (parentId = 0) => {
  return request({
    url: '/category/list',
    data: { parentId }
  })
}

// 点赞文章
const likeArticle = (id) => {
  return request({
    url: `/article/like/${id}`,
    method: 'POST'
  })
}

// 收藏文章
const collectArticle = (id) => {
  return request({
    url: `/article/collect/${id}`,
    method: 'POST'
  })
}

// 分享文章
const shareArticle = (id) => {
  return request({
    url: `/article/share/${id}`,
    method: 'POST'
  })
}

// 用户登录
const userLogin = (code) => {
  return request({
    url: '/user/login',
    method: 'POST',
    data: { code }
  })
}

// 获取用户信息
const getUserInfo = (userId) => {
  return request({
    url: '/user/info',
    data: { userId }
  })
}

// 获取收藏列表
const getCollectList = (userId) => {
  return request({
    url: `/collect/list?userId=${userId}`
  })
}

// 检查是否已收藏
const checkCollected = (userId, articleId) => {
  return request({
    url: `/collect/check/${userId}/${articleId}`
  })
}

// 添加收藏
const addCollect = (userId, articleId) => {
  return request({
    url: `/collect/add?userId=${userId}&articleId=${articleId}`,
    method: 'POST'
  })
}

// 取消收藏
const removeCollect = (userId, articleId) => {
  return request({
    url: `/collect/remove?userId=${userId}&articleId=${articleId}`,
    method: 'DELETE'
  })
}

// 获取浏览历史
const getHistoryList = (userId, limit = 20) => {
  return request({
    url: `/history/list?userId=${userId}&limit=${limit}`
  })
}

// 添加浏览历史
const addHistory = (userId, articleId) => {
  return request({
    url: `/history/add?userId=${userId}&articleId=${articleId}`,
    method: 'POST'
  })
}

module.exports = {
  getBannerList,
  getArticleList,
  getArticleDetail,
  getHotArticles,
  getRecommendArticles,
  getCategoryList,
  likeArticle,
  collectArticle,
  shareArticle,
  userLogin,
  getUserInfo,
  getCollectList,
  checkCollected,
  addCollect,
  removeCollect,
  getHistoryList,
  addHistory
}
