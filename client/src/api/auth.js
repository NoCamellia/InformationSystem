import request from '@/utils/request'

export const adminLogin = (data) => {
  return request({
    url: '/admin/auth/login',
    method: 'post',
    data
  })
}
