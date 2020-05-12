import axios from "@/libs/api.request";

export const login = ({ account, password, verifyCode }) => {
  const data = {
    account,
    password
  };
  return axios.request({
    url: "auth",
    params: { verifyCode },
    data,
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
export const getKaptcha = () => {
  return axios.request({
    url: "kaptcha?" + Math.random(),
    method: "get"
  });
};
