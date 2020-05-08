import axios from "@/libs/api.request";

export const getCustomerList = ({ pageIndex, pageSize }) => {
  let data = {
    pageIndex,
    pageSize
  };
  return axios.request({
    url: "wechat/customer/list",
    method: "post",
    data
  });
};
export const queryCustomerList = ({
  customerName,
  startTime,
  endTime,
  memberUserIds,
  pageIndex,
  pageSize
}) => {
  let data = {
    customerName,
    tenantId: "xinxin",
    startTime,
    endTime,
    memberUserIds,
    pageIndex,
    pageSize
  };
  return axios.request({
    url: "wechat/customer/query",
    method: "post",
    data
  });
};
export const getCustomerDetail = ({ id }) => {
  return axios.request({
    url: `/wechat/customer/detail/${id}`,
    method: "get"
  });
};
