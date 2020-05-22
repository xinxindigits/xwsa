import axios from "@/libs/http";

export const getOrganizationList = data => {
  return axios.request({
    url: "organization/list",
    data,
    method: "post"
  });
};

export const queryOrganization = ({ code }) => {
  return axios.request({
    url: "organization/query/" + code,
    method: "get"
  });
};

export const delOrganization = ({ ids }) => {
  let data = { ids };
  return axios.request({
    url: "organization/delete",
    data,
    method: "post"
  });
};
export const addOrganization = ({
  code,
  name,
  corpId,
  state,
  privateKey,
  addressListSecret,
  customerContactSecret,
  chatRecordSecret,
  remark,
  parentId,
  orgType
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
    parentId,
    orgType
  };
  return axios.request({
    url: "organization/create",
    data,
    method: "post"
  });
};
export const updateOrganization = ({
  code,
  name,
  corpId,
  state,
  privateKey,
  addressListSecret,
  customerContactSecret,
  chatRecordSecret,
  remark,
  parentId,
  orgType
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
    parentId,
    orgType
  };
  return axios.request({
    url: "organization/update",
    data,
    method: "post"
  });
};
export const getAllOrganizationTree = () => {
  return axios.request({
    url: "organization/routes",
    method: "get"
  });
};
