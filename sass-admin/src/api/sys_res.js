/*
 *
 * Copyright 2020 www.xinxindigits.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"),to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice
 * shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * Redistribution and selling copies of the software are prohibited, only if the authorization from xinxin digits
 * was obtained.Neither the name of the xinxin digits; nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 */

import axios from "@/libs/http";
//读取所有权限值列表
export const getGrantList = () => {
  return axios.request({
    url: "resource/grants/list",
    method: "get"
  });
};
//获取权限树
export const getGrantTree = ({ roleCode }) => {
  return axios.request({
    url: "resource/tree",
    params: {
      roleCode
    },
    method: "get"
  });
};
//资源列表
export const getResourceList = ({
  pageSize,
  pageIndex,
  code,
  resourceType,
  name,
  url,
  root
}) => {
  let data = {
    pageSize,
    pageIndex,
    code,
    resourceType,
    name,
    url,
    root
  };
  return axios.request({
    url: "resource/list",
    method: "post",
    data
  });
};

//获取菜单权限树
export const getResourceMenuTree = () => {
  return axios.request({
    url: "resource/menu/tree",
    method: "get"
  });
};
//获取
export const getResourceQueryTree = (
  { code, parentId, resourceType },
  { silent = false }
) => {
  let data = { code, parentId, resourceType };
  return axios.request({
    url: "resource/query/tree",
    method: "post",
    data,
    silent
  });
};
//资源删除
export const deleteResource = ({ id }) => {
  return axios.request({
    url: "resource/delete",
    method: "delete",
    params: { id }
  });
};
//新增资源
export const createResource = ({
  code,
  parentId,
  resourceType,
  name,
  authority,
  url,
  root = false,
  extension
}) => {
  let data = {
    code,
    parentId,
    resourceType,
    name,
    authority,
    url,
    root,
    extension
  };
  return axios.request({
    url: "resource/create",
    method: "post",
    data
  });
};
//更新资源
export const updateResource = ({
  code,
  parentId,
  resourceType,
  name,
  authority,
  url,
  root = false,
  extension,
  id
}) => {
  let data = {
    id,
    code,
    parentId,
    resourceType,
    name,
    authority,
    url,
    root,
    extension
  };
  return axios.request({
    url: "resource/update",
    method: "post",
    data
  });
};
