import axios from "@/libs/api.request";

export const regist = ({ account, name, gender, password, extension }) => {
  return axios.request({
    url: "register",
    data: {
      account,
      name,
      gender,
      password,
      extension
    },
    method: "post"
  });
};
export const login = ({ account, password, verifyCode }) => {
  return axios.request({
    url: "auth",
    params: { verifyCode },
    data: {
      account,
      password
    },
    method: "post"
  });
};
export const getUserInfo = account => {
  return axios.request({
    url: "user/query/" + account,
    method: "get"
  });
};
export const getMenuInfo = () => {
  return axios.request({
    url: "menu/routes",
    method: "get"
  });
};

export const logout = () => {
  return axios.request({
    url: "logout",
    data: {},
    method: "post"
  });
};
