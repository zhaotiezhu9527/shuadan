import request from '@/utils/request'

// 查询每日订单量列表
export function listCount(query) {
  return request({
    url: '/business/count/list',
    method: 'get',
    params: query
  })
}

// 查询每日订单量详细
export function getCount(id) {
  return request({
    url: '/business/count/' + id,
    method: 'get'
  })
}

// 新增每日订单量
export function addCount(data) {
  return request({
    url: '/business/count',
    method: 'post',
    data: data
  })
}

// 修改每日订单量
export function updateCount(data) {
  return request({
    url: '/business/count',
    method: 'put',
    data: data
  })
}

// 删除每日订单量
export function delCount(id) {
  return request({
    url: '/business/count/' + id,
    method: 'delete'
  })
}
