import request from '@/utils/request'

export const getTagList = (params) => {
  return request({
    url: '/admin/tag/list',
    method: 'get',
    params
  })
}

export const getTagDetail = (id) => {
  return request({
    url: `/admin/tag/detail/${id}`,
    method: 'get'
  })
}

export const addTag = (data) => {
  return request({
    url: '/admin/tag/add',
    method: 'post',
    data
  })
}

export const updateTag = (data) => {
  return request({
    url: '/admin/tag/update',
    method: 'put',
    data
  })
}

export const deleteTag = (id) => {
  return request({
    url: `/admin/tag/delete/${id}`,
    method: 'delete'
  })
}
