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
