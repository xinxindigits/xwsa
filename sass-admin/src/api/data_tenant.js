import axios from "@/libs/api.request";

export const getTenantList = data => {
  return axios.request({
    url: "tenant/list",
    data,
    method: "post"
  });
};

export const queryTenant = ({ code }) => {
  return axios.request({
    url: "tenant/query/" + code,
    method: "get"
  });
};

export const delTenant = ({ codes }) => {
  let data = { codes };
  return axios.request({
    url: "tenant/delete",
    data,
    method: "post"
  });
};
export const addTenant = ({
  name,
  corpId,
  state,
  privateKey,
  addressListSecret,
  customerContactSecret,
  chatRecordSecret,
  remark,
}) => {
  let data = {
    name,
    corpId,
    state,
    privateKey,
    addressListSecret,
    customerContactSecret,
    chatRecordSecret,
    remark,
  };
  return axios.request({
    url: "tenant/create",
    data,
    method: "post"
  });
};
export const updateTenant = ({
  code,
  name,
  corpId,
  state,
  privateKey,
  addressListSecret,
  customerContactSecret,
  chatRecordSecret,
  remark,
}) => {
  let data = {
    code,
    name,
    corpId,
    state,
    privateKey,
    addressListSecret,
    customerContactSecret,
    chatRecordSecret,
    remark,
  };
  return axios.request({
    url: "tenant/update",
    data,
    method: "post"
  });
};
