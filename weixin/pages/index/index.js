const api = require('../../utils/api')
const util = require('../../utils/util')

Page({
  data: {
    banners: [],
    hotArticles: [],
    recommendArticles: [],
    loading: false,
    page: 1,
    pageSize: 10,
    hasMore: true
  },

  onLoad() {
    this.loadBanners()
    this.loadHotArticles()
    this.loadRecommendArticles()
  },

  onPullDownRefresh() {
    this.setData({
      page: 1,
      hasMore: true
    })
    Promise.all([
      this.loadBanners(),
      this.loadHotArticles(),
      this.loadRecommendArticles()
    ]).then(() => {
      wx.stopPullDownRefresh()
    })
  },

  onReachBottom() {
    if (this.data.hasMore && !this.data.loading) {
      this.loadRecommendArticles()
    }
  },

  loadBanners() {
    return api.getBannerList().then(res => {
      this.setData({
        banners: res || []
      })
    }).catch(err => {
      console.error('加载轮播图失败', err)
    })
  },

  loadHotArticles() {
    return api.getHotArticles(5).then(res => {
      this.setData({
        hotArticles: res || []
      })
    }).catch(err => {
      console.error('加载热门文章失败', err)
    })
  },

  loadRecommendArticles() {
    if (this.data.loading) return
    
    this.setData({ loading: true })
    
    return api.getRecommendArticles(this.data.pageSize).then(res => {
      const articles = res || []
      this.setData({
        recommendArticles: this.data.page === 1 ? articles : [...this.data.recommendArticles, ...articles],
        page: this.data.page + 1,
        hasMore: articles.length >= this.data.pageSize,
        loading: false
      })
    }).catch(err => {
      console.error('加载推荐文章失败', err)
      this.setData({ loading: false })
    })
  },

  onBannerTap(e) {
    const { id } = e.currentTarget.dataset
    wx.navigateTo({
      url: `/pages/detail/detail?id=${id}`
    })
  },

  onArticleTap(e) {
    const { id } = e.currentTarget.dataset
    wx.navigateTo({
      url: `/pages/detail/detail?id=${id}`
    })
  },

  onSearchTap() {
    wx.navigateTo({
      url: '/pages/search/search'
    })
  },

  formatCount: util.formatCount,
  timeAgo: util.timeAgo
})
