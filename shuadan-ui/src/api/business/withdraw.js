import request from '@/utils/request'

// 查询提现列表列表
export function listWithdraw(query) {
  return request({
    url: '/business/withdraw/list',
    method: 'get',
    params: query
  })
}

// 查询提现列表详细
export function getWithdraw(id) {
  return request({
    url: '/business/withdraw/' + id,
    method: 'get'
  })
}

// 新增提现列表
export function addWithdraw(data) {
  return request({
    url: '/business/withdraw',
    method: 'post',
    data: data
  })
}

// 修改提现列表
export function updateWithdraw(data) {
  return request({
    url: '/business/withdraw',
    method: 'put',
    data: data
  })
}

// 删除提现列表
export function delWithdraw(id) {
  return request({
    url: '/business/withdraw/' + id,
    method: 'delete'
  })
}

// 修改【请填写功能名称】
export function examineWithdraw(data) {
  return request({
    url: '/business/withdraw/check',
    method: 'post',
    data: data
  })
}