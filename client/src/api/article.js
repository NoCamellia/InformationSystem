import request from '@/utils/request'

export const getArticleList = (params) => {
  return request({
    url: '/admin/article/list',
    method: 'get',
    params
  })
}

export const getArticleDetail = (id) => {
  return request({
    url: `/admin/article/detail/${id}`,
    method: 'get'
  })
}

export const addArticle = (data) => {
  return request({
    url: '/admin/article/add',
    method: 'post',
    data
  })
}

export const updateArticle = (data) => {
  return request({
    url: '/admin/article/update',
    method: 'put',
    data
  })
}

export const deleteArticle = (id) => {
  return request({
    url: `/admin/article/delete/${id}`,
    method: 'delete'
  })
}

export const publishArticle = (id) => {
  return request({
    url: `/admin/article/publish/${id}`,
    method: 'put'
  })
}
