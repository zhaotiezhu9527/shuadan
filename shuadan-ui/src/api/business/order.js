import request from '@/utils/request'

// 查询订单列表列表
export function listOrder(query) {
  return request({
    url: '/business/order/list',
    method: 'get',
    params: query
  })
}

// 查询订单列表详细
export function getOrder(id) {
  return request({
    url: '/business/order/' + id,
    method: 'get'
  })
}

// 新增订单列表
export function addOrder(data) {
  return request({
    url: '/business/order',
    method: 'post',
    data: data
  })
}

// 修改订单列表
export function updateOrder(data) {
  return request({
    url: '/business/order',
    method: 'put',
    data: data
  })
}

// 删除订单列表
export function delOrder(id) {
  return request({
    url: '/business/order/' + id,
    method: 'delete'
  })
}
