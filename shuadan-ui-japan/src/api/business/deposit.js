import request from '@/utils/request'

// 查询充值列表列表
export function listDeposit(query) {
  return request({
    url: '/business/deposit/list',
    method: 'get',
    params: query
  })
}

// 查询充值列表详细
export function getDeposit(id) {
  return request({
    url: '/business/deposit/' + id,
    method: 'get'
  })
}

// 新增充值列表
export function addDeposit(data) {
  return request({
    url: '/business/deposit',
    method: 'post',
    data: data
  })
}

// 修改充值列表
export function updateDeposit(data) {
  return request({
    url: '/business/deposit',
    method: 'put',
    data: data
  })
}

// 删除充值列表
export function delDeposit(id) {
  return request({
    url: '/business/deposit/' + id,
    method: 'delete'
  })
}
