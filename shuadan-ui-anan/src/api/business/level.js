import request from '@/utils/request'

// 查询等级列表列表
export function listLevel(query) {
  return request({
    url: '/business/level/list',
    method: 'get',
    params: query
  })
}

// 查询等级列表详细
export function getLevel(id) {
  return request({
    url: '/business/level/' + id,
    method: 'get'
  })
}

// 新增等级列表
export function addLevel(data) {
  return request({
    url: '/business/level',
    method: 'post',
    data: data
  })
}

// 修改等级列表
export function updateLevel(data) {
  return request({
    url: '/business/level',
    method: 'put',
    data: data
  })
}

// 删除等级列表
export function delLevel(id) {
  return request({
    url: '/business/level/' + id,
    method: 'delete'
  })
}
