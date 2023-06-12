import request from '@/utils/request'

// 查询客服列表列表
export function listService(query) {
  return request({
    url: '/business/service/list',
    method: 'get',
    params: query
  })
}

// 查询客服列表详细
export function getService(id) {
  return request({
    url: '/business/service/' + id,
    method: 'get'
  })
}

// 新增客服列表
export function addService(data) {
  return request({
    url: '/business/service',
    method: 'post',
    data: data
  })
}

// 修改客服列表
export function updateService(data) {
  return request({
    url: '/business/service',
    method: 'put',
    data: data
  })
}

// 删除客服列表
export function delService(id) {
  return request({
    url: '/business/service/' + id,
    method: 'delete'
  })
}
