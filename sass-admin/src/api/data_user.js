import axios from "@/libs/api.request";

export const getUserList = data => {
  return axios.request({
    url: "user/list",
    data,
    method: "post"
  });
};
export const addUser = ({ account, extension, name, gender, password }) => {
  let data = { account, extension, name, gender, password };
  return axios.request({
    url: "user/create",
    data,
    method: "post"
  });
};
export const updateUser = ({ account, name, gender }) => {
  let data = { account, name, gender };
  return axios.request({
    url: "user/update",
    data,
    method: "post"
  });
};
export const deleteUser = ({ accounts }) => {
  return axios.request({
    url: "user/delete",
    method: "post",
    data: { accounts }
  });
};
