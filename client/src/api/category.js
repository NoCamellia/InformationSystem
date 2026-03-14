import request from '@/utils/request'

export const getCategoryList = () => {
  return request({
    url: '/admin/category/list',
    method: 'get'
  })
}

export const addCategory = (data) => {
  return request({
    url: '/admin/category/add',
    method: 'post',
    data
  })
}

export const updateCategory = (data) => {
  return request({
    url: '/admin/category/update',
    method: 'put',
    data
  })
}

export const deleteCategory = (id) => {
  return request({
    url: `/admin/category/delete/${id}`,
    method: 'delete'
  })
}
