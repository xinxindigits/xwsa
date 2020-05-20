import axios from "@/libs/api.request";
export const getUserList = data => {
  return axios.request({
    url: "user/list",
    data,
    method: "post"
  });
};
export const addUser = ({
  account,
  extension,
  name,
  gender,
  password,
  roles,
  orgCode
}) => {
  let data = { account, extension, name, gender, password, roles, orgCode };
  return axios.request({
    url: "user/create",
    data,
    method: "post"
  });
};
export const updateUser = ({
  account,
  name,
  gender,
  extension,
  roles,
  orgCode
}) => {
  let data = { account, name, gender, extension, roles, orgCode };
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
export const getUserDetail = ({ account }) => {
  return axios.request({
    url: "user/query/" + account,
    method: "get"
  });
};
export const grantUserRoles = ({
  userAccount,
  userName,
  userRoles,
  extension
}) => {
  return axios.request({
    url: "user/grant",
    method: "post",
    data: { userAccount, userName, userRoles, extension }
  });
};
//重置密码
export const resetUserPwd = ({ account, oldPassword, newPassword }) => {
  return axios.request({
    url: "user/restpwd",
    method: "post",
    data: { account, oldPassword, newPassword }
  });
};
//修改个人密码
export const changeUserPwd = ({ account, oldPassword, newPassword }) => {
  return axios.request({
    url: "user/password/modify",
    method: "post",
    data: { account, oldPassword, newPassword }
  });
};
