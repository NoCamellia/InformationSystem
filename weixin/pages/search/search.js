Page({
  data: {
    keyword: '',
    articles: [],
    loading: false,
    searched: false
  },

  onSearch() {
    const { keyword } = this.data
    if (!keyword.trim()) {
      wx.showToast({
        title: '请输入搜索关键词',
        icon: 'none'
      })
      return
    }

    this.setData({ loading: true, searched: true })

    // 调用搜索API
    setTimeout(() => {
      this.setData({
        articles: [],
        loading: false
      })
      wx.showToast({
        title: '暂无搜索结果',
        icon: 'none'
      })
    }, 1000)
  },

  onInputChange(e) {
    this.setData({
      keyword: e.detail.value
    })
  },

  onArticleTap(e) {
    const { id } = e.currentTarget.dataset
    wx.navigateTo({
      url: `/pages/detail/detail?id=${id}`
    })
  }
})
