App({
  globalData: {
    userInfo: null,
    token: null,
    baseUrl: 'http://localhost:8080/api/miniapp'
  },

  onLaunch() {
    // 检查登录状态
    const token = wx.getStorageSync('token')
    if (token) {
      this.globalData.token = token
    }
  },

  // 微信登录
  wxLogin() {
    return new Promise((resolve, reject) => {
      wx.login({
        success: (res) => {
          console.log('wx.login success:', res)
          if (res.code) {
            // 发送 res.code 到后台换取 openId, sessionKey, unionId
            wx.request({
              url: this.globalData.baseUrl + '/user/login',
              method: 'POST',
              data: {
                code: res.code
              },
              header: {
                'content-type': 'application/json'
              },
              success: (result) => {
                console.log('login request success:', result)
                if (result.data && result.data.code === 200) {
                  const token = result.data.data.token
                  const userId = result.data.data.userId
                  wx.setStorageSync('token', token)
                  wx.setStorageSync('userId', userId)
                  this.globalData.token = token
                  resolve(result.data.data)
                } else {
                  reject(result.data?.message || '登录失败')
                }
              },
              fail: (err) => {
                console.error('login request fail:', err)
                reject(err)
              }
            })
          } else {
            reject('登录失败：' + res.errMsg)
          }
        },
        fail: (err) => {
          console.error('wx.login fail:', err)
          reject(err)
        }
      })
    })
  },

  // 获取用户信息
  getUserInfo() {
    return new Promise((resolve, reject) => {
      wx.getUserProfile({
        desc: '用于完善用户资料',
        success: (res) => {
          this.globalData.userInfo = res.userInfo
          wx.setStorageSync('userInfo', res.userInfo)
          resolve(res.userInfo)
        },
        fail: reject
      })
    })
  }
})
