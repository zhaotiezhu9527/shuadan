import request from '@/utils/request'

// 查询会员列表列表
export function listUser(query) {
  return request({
    url: '/business/user/list',
    method: 'get',
    params: query
  })
}

// 查询会员列表详细
export function getUser(id) {
  return request({
    url: '/business/user/' + id,
    method: 'get'
  })
}

// 新增会员列表
export function addUser(data) {
  return request({
    url: '/business/user',
    method: 'post',
    data: data
  })
}

// 修改会员列表
export function updateUser(data) {
  return request({
    url: '/business/user',
    method: 'put',
    data: data
  })
}

// 删除会员列表
export function delUser(id) {
  return request({
    url: '/business/user/' + id,
    method: 'delete'
  })
}

// 重置余额
export function resetBalance(data) {
  return request({
    url: '/business/user/resetBalance',
    method: 'post',
    data: data
  })
}

// 增减余额
export function balanceUser(data) {
  return request({
    url: '/business/user/optMoney',
    method: 'post',
    data: data
  })
}

// 查询会员列表详情
export function nodeUser(query) {
  return request({
    url: '/business/user/node',
    method: 'get',
    params: query
  })
}

// 设置单数
export function setTodayCount(data) {
  return request({
    url: '/business/user/setTodayCount',
    method: 'post',
    data: data
  })
}
