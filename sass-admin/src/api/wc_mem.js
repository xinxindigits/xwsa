import axios from "@/libs/http";
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
export const queryMember = ({
  pageIndex,
  pageSize,
  deptId,
  memberName,
  mobile
}) => {
  let data = {
    pageIndex,
    pageSize,
    deptId,
    memberName,
    mobile
  };
  return axios.request({
    url: "wechat/member/query",
    method: "post",
    data
  });
};
