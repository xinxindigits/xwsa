import axios from "@/libs/api.request";

export const getAllRoles = () => {
  return axios.request({
    url: "role/routes",
    method: "get"
  });
};
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
