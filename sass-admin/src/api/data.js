import axios from "@/libs/api.request";

export const getRoleList = data => {
  return axios.request({
    url: "role/list",
    data,
    method: "post"
  });
};
