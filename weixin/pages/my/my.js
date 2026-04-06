const app = getApp()
const api = require('../../utils/api')

Page({
  data: {
    userInfo: null,
    hasUserInfo: false,
    stats: {
      collectCount: 0,
      historyCount: 0
    }
  },

  onLoad() {
    this.loadUserInfo()
  },

  onShow() {
    this.loadUserInfo()
    this.loadStats()
  },

  loadUserInfo() {
    const userInfo = wx.getStorageSync('userInfo')
    const userId = wx.getStorageSync('userId')
    if (userInfo) {
      this.setData({
        userInfo,
        hasUserInfo: true,
        userId
      })
    }
  },

  loadStats() {
    const userId = wx.getStorageSync('userId')
    if (!userId) {
      return
    }
    
    // 获取真实的收藏数和浏览历史数
    api.getCollectList(userId).then(res => {
      this.setData({
        'stats.collectCount': res.length || 0
      })
    }).catch(err => {
      console.error('获取收藏数失败', err)
    })
    
    api.getHistoryList(userId, 100).then(res => {
      this.setData({
        'stats.historyCount': res.length || 0
      })
    }).catch(err => {
      console.error('获取浏览历史数失败', err)
    })
  },

  onGetUserInfo() {
    console.log('开始登录...')
    wx.showLoading({
      title: '登录中...',
      mask: true
    })
    
    app.wxLogin().then(data => {
      console.log('登录成功:', data)
      wx.hideLoading()
      
      this.setData({
        userInfo: {
          nickName: data.nickname || '微信用户',
          avatarUrl: data.avatar || ''
        },
        hasUserInfo: true
      })
      
      wx.showToast({
        title: '登录成功',
        icon: 'success'
      })
    }).catch(err => {
      console.error('登录失败:', err)
      wx.hideLoading()
      wx.showToast({
        title: '登录失败: ' + (err.message || err),
        icon: 'none'
      })
    })
  },

  onCollectTap() {
    if (!this.data.hasUserInfo) {
      wx.showToast({
        title: '请先登录',
        icon: 'none'
      })
      return
    }
    wx.navigateTo({
      url: '/pages/collect/collect'
    })
  },

  onHistoryTap() {
    if (!this.data.hasUserInfo) {
      wx.showToast({
        title: '请先登录',
        icon: 'none'
      })
      return
    }
    wx.navigateTo({
      url: '/pages/history/history'
    })
  },

  onAboutTap() {
    wx.showModal({
      title: '关于我们',
      content: '信息咨询小程序 v1.0.0\n为您提供最新最热的资讯内容',
      showCancel: false
    })
  }
})
