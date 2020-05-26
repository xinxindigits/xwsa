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
export const getUserList = data => {
  return axios.request({
    url: "user/list",
    data,
    method: "post"
  });
};
export const addUser = ({
  account,
  extension,
  name,
  gender,
  password,
  roles,
  orgCode
}) => {
  let data = { account, extension, name, gender, password, roles, orgCode };
  return axios.request({
    url: "user/create",
    data,
    method: "post"
  });
};
export const updateUser = ({
  account,
  name,
  gender,
  extension,
  roles,
  orgCode
}) => {
  let data = { account, name, gender, extension, roles, orgCode };
  return axios.request({
    url: "user/update",
    data,
    method: "post"
  });
};
export const deleteUser = ({ accounts }) => {
  return axios.request({
    url: "user/delete",
    method: "post",
    data: { accounts }
  });
};
export const getUserDetail = ({ account }) => {
  return axios.request({
    url: "user/query/" + account,
    method: "get"
  });
};
export const grantUserRoles = ({
  userAccount,
  userName,
  userRoles,
  extension
}) => {
  return axios.request({
    url: "user/grant",
    method: "post",
    data: { userAccount, userName, userRoles, extension }
  });
};
//重置密码
export const resetUserPwd = ({ account, oldPassword, newPassword }) => {
  return axios.request({
    url: "user/restpwd",
    method: "post",
    data: { account, oldPassword, newPassword }
  });
};
//修改个人密码
export const changeUserPwd = ({ account, oldPassword, newPassword }) => {
  return axios.request({
    url: "user/password/modify",
    method: "post",
    data: { account, oldPassword, newPassword }
  });
};
