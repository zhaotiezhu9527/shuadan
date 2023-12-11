import request from '@/utils/request'

// 查询会员列表列表
export function listUser(query) {
  return request({
    url: '/business/user/list',
    method: 'get',
    params: query
  })
}

// 增减余额
export function userLogout(data) {
  return request({
    url: '/business/user/logout',
    method: 'post',
    data: data
  })
}

