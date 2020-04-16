import axios from "@/libs/api.request";

export const login = ({ account, password }) => {
  const data = {
    account,
    password
  };
  return axios.request({
    url: "sass/auth",
    data,
    method: "post"
  });
};
export const getUserInfo = account => {
  return axios.request({
    url: "sass/user/query",
    params: {
      account
    },
    method: "get"
  });
};
export const logout = () => {
  return axios.request({
    url: "sass/logout",
    data: {},
    method: "post"
  });
};
