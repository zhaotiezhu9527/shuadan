import request from '@/utils/request'

// 查询公告管理列表
export function listNotice(query) {
  return request({
    url: '/business/notice/list',
    method: 'get',
    params: query
  })
}

// 查询公告管理详细
export function getNotice(id) {
  return request({
    url: '/business/notice/' + id,
    method: 'get'
  })
}

// 新增公告管理
export function addNotice(data) {
  return request({
    url: '/business/notice',
    method: 'post',
    data: data
  })
}

// 修改公告管理
export function updateNotice(data) {
  return request({
    url: '/business/notice',
    method: 'put',
    data: data
  })
}

// 删除公告管理
export function delNotice(id) {
  return request({
    url: '/business/notice/' + id,
    method: 'delete'
  })
}
