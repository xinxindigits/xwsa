/*
 *
 * Copyright 2020 www.xinxindigits.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"),to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice
 * shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * Redistribution and selling copies of the software are prohibited, only if the authorization from xinxin digits
 * was obtained.Neither the name of the xinxin digits; nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 */

import axios from "@/libs/http";

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
