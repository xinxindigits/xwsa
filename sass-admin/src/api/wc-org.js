import axios from "@/libs/http";

export const getOrgList = () => {
  return axios.request({
    url: "wechat/dept/list",
    method: "get"
  });
};

export const queryOrgList = ({ departmentId, departmentName, englishName }) => {
  let data = { departmentId, departmentName, englishName };
  return axios.request({
    url: "wechat/dept/query",
    method: "post",
    data
  });
};
//根据部门查成员
export const queryMemberByDeptId = ({ deptId, pageIndex, pageSize }) => {
  let data = { deptId, pageIndex, pageSize };
  return axios.request({
    url: "wechat/member/dept/query",
    method: "post",
    data
  });
};
export default {
  getOrgList,
  queryOrgList,
  queryMemberByDeptId
};
