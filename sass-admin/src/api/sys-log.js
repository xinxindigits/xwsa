import axios from "@/libs/http";

export const getLogList = ({ pageIndex, pageSize }) => {
  let data = {
    pageIndex,
    pageSize
  };
  return axios.request({
    url: "oplog/list",
    method: "post",
    data
  });
};
export const queryLogList = ({ pageIndex, pageSize, account }) => {
  let data = {
    pageIndex,
    pageSize,
    account
  };
  return axios.request({
    url: "oplog/query",
    method: "post",
    data
  });
};

export const getLogDetail = ({ id }) => {
  return axios.request({
    url: `oplog/detail/${id}`,
    method: "get"
  });
};
