const app = getApp()

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
    this.loadStats()
  },

  loadUserInfo() {
    const userInfo = wx.getStorageSync('userInfo')
    if (userInfo) {
      this.setData({
        userInfo,
        hasUserInfo: true
      })
    }
  },

  loadStats() {
    // 这里可以调用API获取统计数据
    this.setData({
      stats: {
        collectCount: 12,
        historyCount: 45
      }
    })
  },

  onGetUserInfo() {
    app.getUserInfo().then(userInfo => {
      this.setData({
        userInfo,
        hasUserInfo: true
      })
    }).catch(err => {
      console.error('获取用户信息失败', err)
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
