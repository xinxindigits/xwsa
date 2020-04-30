import axios from "@/libs/api.request";

export const getRoleList = data => {
  return axios.request({
    url: "role/list",
    data,
    method: "post"
  });
};
//角色管理-新增角色
export const addRole = ({ code, extension, name, roleType }) => {
  let data = { code, extension, name, roleType };
  return axios.request({
    url: "role/create",
    data,
    method: "post"
  });
};
export const updateRole = ({ code, extension, name, roleType }) => {
  let data = { code, extension, name, roleType };
  return axios.request({
    url: "role/update",
    data,
    method: "post"
  });
};
//角色管理-删除角色
export const delRole = ({ roleCodes }) => {
  return axios.request({
    url: "role/delete",
    data: { roleCodes },
    method: "post"
  });
};
//角色管理-角色资源授权
export const grantRole = ({ roleCode, roleName, resources }) => {
  let data = {
    roleCode,
    roleName,
    resources
  };
  return axios.request({
    url: "role/resource/grant",
    method: "post",
    data
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
