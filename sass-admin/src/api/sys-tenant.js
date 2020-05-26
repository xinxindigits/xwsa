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
  remark
}) => {
  let data = {
    name,
    corpId,
    state,
    privateKey,
    addressListSecret,
    customerContactSecret,
    chatRecordSecret,
    remark
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
  remark
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
    remark
  };
  return axios.request({
    url: "tenant/update",
    data,
    method: "post"
  });
};
export const queryTenantConfig = ({ tenantId }) => {
  return axios.request({
    params: {
      tenantId
    },
    url: "tenant/queryConfig",
    method: "get"
  });
};
export const createTenantTask = ({
  taskType,
  cronExpression,
  status,
  countCeiling,
  timeInterval
}) => {
  let data = { taskType, cronExpression, status, countCeiling, timeInterval };
  return axios.request({
    url: "tenant/tenantConfig/create",
    data,
    method: "post"
  });
};
export const updateTenantTask = ({
  id,
  taskType,
  cronExpression,
  status,
  countCeiling,
  timeInterval
}) => {
  let data = {
    id,
    taskType,
    cronExpression,
    status,
    countCeiling,
    timeInterval
  };
  return axios.request({
    url: "tenant/tenantConfig/update",
    data,
    method: "post"
  });
};
export const executeJob = ({ taskType }) => {
  return axios.request({
    params: {
      taskType
    },
    url: "tenant/executeJob",
    method: "get"
  });
};
export const queryTaskLog = data => {
  return axios.request({
    data,
    url: "tenant/jobLog/query",
    method: "post"
  });
};
