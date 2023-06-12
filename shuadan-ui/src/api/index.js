import request from '@/utils/request'

// 获取路由
export const getAllReport = () => {
  return request({
    url: '/business/user/allReport',
    method: 'get'
  })
}