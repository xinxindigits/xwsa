import axios from "@/libs/api.request";
export const getMemberList = ({ pageIndex, pageSize }) => {
  let data = { pageIndex, pageSize };
  return axios.request({
    url: "wechat/member/list",
    method: "post",
    data
  });
};
//员工详情
export const getMemberDetail = ({ id }) => {
  return axios.request({
    url: "/wechat/member/detail/" + id,
    method: "get"
  });
};
