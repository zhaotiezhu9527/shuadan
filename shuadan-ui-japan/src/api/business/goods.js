import request from '@/utils/request'

// 查询商品列表列表
export function listGoods(query) {
  return request({
    url: '/business/goods/list',
    method: 'get',
    params: query
  })
}

// 查询商品列表详细
export function getGoods(id) {
  return request({
    url: '/business/goods/' + id,
    method: 'get'
  })
}

// 新增商品列表
export function addGoods(data) {
  return request({
    url: '/business/goods',
    method: 'post',
    data: data
  })
}

// 修改商品列表
export function updateGoods(data) {
  return request({
    url: '/business/goods',
    method: 'put',
    data: data
  })
}

// 删除商品列表
export function delGoods(id) {
  return request({
    url: '/business/goods/' + id,
    method: 'delete'
  })
}
