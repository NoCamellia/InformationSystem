const api = require('../../utils/api')
const util = require('../../utils/util')

Page({
  data: {
    article: null,
    loading: true,
    isLiked: false,
    isCollected: false,
    userId: null
  },

  onLoad(options) {
    const { id } = options
    // 获取userId
    const userId = wx.getStorageSync('userId')
    this.setData({ userId })
    
    if (id) {
      this.loadArticleDetail(id)
      // 记录浏览历史
      if (userId) {
        this.addHistory(userId, id)
      }
    }
  },

  onShareAppMessage() {
    const { article } = this.data
    return {
      title: article.title,
      path: `/pages/detail/detail?id=${article.id}`,
      imageUrl: article.coverImage
    }
  },

  loadArticleDetail(id) {
    this.setData({ loading: true })
    
    api.getArticleDetail(id).then(res => {
      this.setData({
        article: res,
        loading: false
      })
      
      // 设置页面标题
      wx.setNavigationBarTitle({
        title: res.title
      })
    }).catch(err => {
      console.error('加载文章详情失败', err)
      this.setData({ loading: false })
      wx.showToast({
        title: '加载失败',
        icon: 'none'
      })
    })
  },

  addHistory(userId, articleId) {
    api.addHistory(userId, articleId).then(() => {
      console.log('浏览历史记录成功')
    }).catch(err => {
      console.error('记录浏览历史失败', err)
    })
  },

  onLikeTap() {
    const { article, isLiked } = this.data
    
    if (isLiked) {
      wx.showToast({
        title: '已经点赞过了',
        icon: 'none'
      })
      return
    }

    api.likeArticle(article.id).then(() => {
      this.setData({
        isLiked: true,
        'article.likeCount': article.likeCount + 1
      })
      wx.showToast({
        title: '点赞成功',
        icon: 'success'
      })
    }).catch(err => {
      console.error('点赞失败', err)
    })
  },

  onCollectTap() {
    const { article, isCollected, userId } = this.data
    
    if (!userId) {
      wx.showToast({
        title: '请先登录',
        icon: 'none'
      })
      return
    }
    
    if (isCollected) {
      wx.showToast({
        title: '已经收藏过了',
        icon: 'none'
      })
      return
    }

    console.log('收藏文章:', { userId, articleId: article.id })
    api.addCollect(userId, article.id).then(() => {
      this.setData({
        isCollected: true,
        'article.collectCount': article.collectCount + 1
      })
      wx.showToast({
        title: '收藏成功',
        icon: 'success'
      })
    }).catch(err => {
      console.error('收藏失败', err)
      wx.showToast({
        title: '收藏失败',
        icon: 'none'
      })
    })
  },

  onShareTap() {
    const { article } = this.data
    
    api.shareArticle(article.id).then(() => {
      this.setData({
        'article.shareCount': article.shareCount + 1
      })
    }).catch(err => {
      console.error('分享统计失败', err)
    })
  },

  formatCount: util.formatCount,
  timeAgo: util.timeAgo
})
