import axios from "@/libs/api.request";

export const getRoleList = data => {
  return axios.request({
    url: "role/list",
    data,
    method: "post"
  });
};
export const addRole = ({ code, extension, name, roleType }) => {
  let data = { code, extension, name, roleType };
  return axios.request({
    url: "role/create",
    data,
    method: "post"
  });
};
export const delRole = ({ roleCode }) => {
  return axios.request({
    url: "delete/" + roleCode,
    method: "delete"
  });
};
