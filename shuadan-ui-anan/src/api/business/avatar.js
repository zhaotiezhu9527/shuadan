import request from '@/utils/request'

// 查询头像列表列表
export function listAvatar(query) {
  return request({
    url: '/business/avatar/list',
    method: 'get',
    params: query
  })
}

// 查询头像列表详细
export function getAvatar(id) {
  return request({
    url: '/business/avatar/' + id,
    method: 'get'
  })
}

// 新增头像列表
export function addAvatar(data) {
  return request({
    url: '/business/avatar',
    method: 'post',
    data: data
  })
}

// 修改头像列表
export function updateAvatar(data) {
  return request({
    url: '/business/avatar',
    method: 'put',
    data: data
  })
}

// 删除头像列表
export function delAvatar(id) {
  return request({
    url: '/business/avatar/' + id,
    method: 'delete'
  })
}
