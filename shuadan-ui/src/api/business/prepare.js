import request from '@/utils/request'

// 查询预派送列表列表
export function listPrepare(query) {
  return request({
    url: '/business/prepare/list',
    method: 'get',
    params: query
  })
}

// 查询预派送列表详细
export function getPrepare(id) {
  return request({
    url: '/business/prepare/' + id,
    method: 'get'
  })
}

// 新增预派送列表
export function addPrepare(data) {
  return request({
    url: '/business/prepare',
    method: 'post',
    data: data
  })
}

// 修改预派送列表
export function updatePrepare(data) {
  return request({
    url: '/business/prepare',
    method: 'put',
    data: data
  })
}

// 删除预派送列表
export function delPrepare(id) {
  return request({
    url: '/business/prepare/' + id,
    method: 'delete'
  })
}
