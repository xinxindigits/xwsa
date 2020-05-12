import axios from "@/libs/api.request";

export const getTagList = ({ pageIndex, pageSize }) => {
  let data = {
    pageIndex,
    pageSize
  };
  return axios.request({
    url: "tags/list",
    method: "post",
    data
  });
};
export const queryTagList = ({ pageIndex, pageSize, name }) => {
  let data = {
    pageIndex,
    pageSize,
    name
  };
  return axios.request({
    url: "tags/query",
    method: "post",
    data
  });
};

export const createTag = ({ name, code, tagType, description }) => {
  let data = {
    name,
    code,
    tagType,
    description
  };
  return axios.request({
    url: "tags/create",
    method: "post",
    data
  });
};

export const updateTag = ({ id, name, tagType, description }) => {
  let data = {
    id,
    name,
    tagType,
    description
  };
  return axios.request({
    url: "tags/update",
    method: "post",
    data
  });
};
export const deleteTag = ({ tagId }) => {
  return axios.request({
    url: "tags/delete",
    method: "delete",
    params: { tagId }
  });
};

export const setTagByKeyId = ({ keyId, keyName, description = "", tagIds }) => {
  let data = { keyId, keyName, description, tagIds };
  return axios.request({
    url: "tags/fixdata",
    method: "post",
    data
  });
};
export const getAllTags = () => {
  return axios.request({
    url: "tags/routes",
    method: "post",
    data: {}
  });
};
