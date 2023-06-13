import request from '@/utils/request'

// 查询账变列表列表
export function listAccount(query) {
  return request({
    url: '/business/account/list',
    method: 'get',
    params: query
  })
}

// 查询账变列表详细
export function getAccount(id) {
  return request({
    url: '/business/account/' + id,
    method: 'get'
  })
}

// 新增账变列表
export function addAccount(data) {
  return request({
    url: '/business/account',
    method: 'post',
    data: data
  })
}

// 修改账变列表
export function updateAccount(data) {
  return request({
    url: '/business/account',
    method: 'put',
    data: data
  })
}

// 删除账变列表
export function delAccount(id) {
  return request({
    url: '/business/account/' + id,
    method: 'delete'
  })
}
