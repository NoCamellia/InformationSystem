const api = require('../../utils/api')

Page({
  data: {
    articles: [],
    loading: true,
    userId: null
  },

  onLoad() {
    const userId = wx.getStorageSync('userId')
    this.setData({ userId })
    
    if (userId) {
      this.loadCollectList(userId)
    } else {
      wx.showToast({
        title: '请先登录',
        icon: 'none'
      })
      this.setData({ loading: false })
    }
  },

  onShow() {
    const userId = wx.getStorageSync('userId')
    if (userId) {
      this.loadCollectList(userId)
    }
  },

  loadCollectList(userId) {
    this.setData({ loading: true })
    
    api.getCollectList(userId).then(res => {
      console.log('收藏列表:', res)
      this.setData({
        articles: res || [],
        loading: false
      })
      
      if (!res || res.length === 0) {
        wx.showToast({
          title: '还没有收藏任何文章',
          icon: 'none'
        })
      }
    }).catch(err => {
      console.error('获取收藏列表失败', err)
      this.setData({ loading: false })
      wx.showToast({
        title: '加载失败',
        icon: 'none'
      })
    })
  },

  onArticleTap(e) {
    const { id } = e.currentTarget.dataset
    wx.navigateTo({
      url: `/pages/detail/detail?id=${id}`
    })
  }
})
