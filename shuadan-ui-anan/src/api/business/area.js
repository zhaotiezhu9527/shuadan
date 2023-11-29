import request from '@/utils/request'

// 查询商品分类列表
export function listArea(query) {
  return request({
    url: '/business/area/list',
    method: 'get',
    params: query
  })
}

// 查询商品分类详细
export function getArea(id) {
  return request({
    url: '/business/area/' + id,
    method: 'get'
  })
}

// 新增商品分类
export function addArea(data) {
  return request({
    url: '/business/area',
    method: 'post',
    data: data
  })
}

// 修改商品分类
export function updateArea(data) {
  return request({
    url: '/business/area',
    method: 'put',
    data: data
  })
}

// 删除商品分类
export function delArea(id) {
  return request({
    url: '/business/area/' + id,
    method: 'delete'
  })
}
