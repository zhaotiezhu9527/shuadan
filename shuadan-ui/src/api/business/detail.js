import request from '@/utils/request'

// 查询预派送详情列表列表
export function listDetail(query) {
  return request({
    url: '/business/detail/list',
    method: 'get',
    params: query
  })
}

// 查询预派送详情列表详细
export function getDetail(id) {
  return request({
    url: '/business/detail/' + id,
    method: 'get'
  })
}

// 新增预派送详情列表
export function addDetail(data) {
  return request({
    url: '/business/detail',
    method: 'post',
    data: data
  })
}

// 修改预派送详情列表
export function updateDetail(data) {
  return request({
    url: '/business/detail',
    method: 'put',
    data: data
  })
}

// 删除预派送详情列表
export function delDetail(id) {
  return request({
    url: '/business/detail/' + id,
    method: 'delete'
  })
}
