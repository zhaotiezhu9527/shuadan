import request from '@/utils/request'

// 获取路由
export const getRouters = () => {
  return request({
    url: '/getRouters',
    method: 'get'
  })
}

//获取提示音
export const getNotice = () => {
  return request({
    url: '/business/withdraw/getNotice',
    method: 'get'
  })
}