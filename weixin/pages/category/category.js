const api = require('../../utils/api')

Page({
  data: {
    categories: [],
    currentCategory: null,
    articles: [],
    loading: false,
    page: 1,
    pageSize: 10,
    hasMore: true
  },

  onLoad() {
    this.loadCategories()
  },

  loadCategories() {
    api.getCategoryList(0).then(res => {
      this.setData({
        categories: res || []
      })
      if (res && res.length > 0) {
        this.onCategoryTap({ currentTarget: { dataset: { id: res[0].id } } })
      }
    }).catch(err => {
      console.error('加载分类失败', err)
    })
  },

  onCategoryTap(e) {
    const { id } = e.currentTarget.dataset
    this.setData({
      currentCategory: id,
      page: 1,
      articles: [],
      hasMore: true
    })
    this.loadArticles(id)
  },

  loadArticles(categoryId) {
    if (this.data.loading) return
    
    this.setData({ loading: true })
    
    api.getArticleList({
      categoryId,
      current: this.data.page,
      size: this.data.pageSize
    }).then(res => {
      const articles = res.records || []
      this.setData({
        articles: this.data.page === 1 ? articles : [...this.data.articles, ...articles],
        page: this.data.page + 1,
        hasMore: articles.length >= this.data.pageSize,
        loading: false
      })
    }).catch(err => {
      console.error('加载文章失败', err)
      this.setData({ loading: false })
    })
  },

  onReachBottom() {
    if (this.data.hasMore && !this.data.loading && this.data.currentCategory) {
      this.loadArticles(this.data.currentCategory)
    }
  },

  onArticleTap(e) {
    const { id } = e.currentTarget.dataset
    wx.navigateTo({
      url: `/pages/detail/detail?id=${id}`
    })
  }
})
