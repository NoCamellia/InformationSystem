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

module.exports = {
  getBannerList,
  getArticleList,
  getArticleDetail,
  getHotArticles,
  getRecommendArticles,
  getCategoryList,
  likeArticle,
  collectArticle,
  shareArticle
}
