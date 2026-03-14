Page({
  data: {
    articles: []
  },

  onLoad() {
    // 模拟历史数据
    this.setData({
      articles: []
    })
  },

  onArticleTap(e) {
    const { id } = e.currentTarget.dataset
    wx.navigateTo({
      url: `/pages/detail/detail?id=${id}`
    })
  }
})
