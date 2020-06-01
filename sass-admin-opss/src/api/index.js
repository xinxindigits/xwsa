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
import {
  mngGetuthsTree,
  mngGetTenantRoutes,
  mngCreateAuth,
  mngUpdateAuth
} from "./manage";
import { regist, login, getUserInfo, getMenuInfo, logout } from "./user";
import {
  getGrantList,
  getGrantTree,
  getResourceList,
  getResourceMenuTree,
  getResourceQueryTree,
  deleteResource,
  createResource,
  updateResource
} from "./sys-res";
import {
  getRoleList,
  addRole,
  updateRole,
  delRole,
  grantRole,
  getAllRoles
} from "./sys-role";
import {
  getTagList,
  queryTagList,
  createTag,
  updateTag,
  deleteTag,
  setTagByKeyId,
  getAllTags
} from "./sys-tag";
import {
  getOrganizationList,
  queryOrganization,
  delOrganization,
  addOrganization,
  updateOrganization,
  getAllOrganizationTree
} from "./sys-organization";
import {
  getTenantList,
  queryTenant,
  delTenant,
  addTenant,
  updateTenant,
  queryTenantConfig,
  createTenantTask,
  executeJob,
  updateTenantTask,
  queryTaskLog
} from "./sys-tenant";
import { getLogList, queryLogList, getLogDetail } from "./sys-log";
import {
  getUserList,
  getUserDetail,
  addUser,
  updateUser,
  deleteUser,
  grantUserRoles,
  resetUserPwd,
  changeUserPwd
} from "./sys-user";
import { getOrgList, queryOrgList, queryMemberByDeptId } from "./wc-org";
import { getMemberDetail, getMemberList, queryMember } from "./wc-mem";
import {
  queryMsgList,
  getMsgByMsgId,
  getUserInMsgList,
  getMsgDetailByUserId,
  getMsgDetailByRoomId,
  getPageIndex
} from "./wc-msg";
import {
  getCustomerList,
  queryCustomerList,
  getCustomerDetail
} from "./wc-cus";
export {
  regist,
  login,
  getUserInfo,
  getMenuInfo,
  logout,
  getGrantList,
  getGrantTree,
  getResourceList,
  getResourceMenuTree,
  getResourceQueryTree,
  deleteResource,
  createResource,
  updateResource,
  getAllRoles,
  getRoleList,
  addRole,
  updateRole,
  delRole,
  grantRole,
  getTagList,
  queryTagList,
  createTag,
  updateTag,
  deleteTag,
  setTagByKeyId,
  getAllTags,
  getLogList,
  queryLogList,
  getLogDetail,
  getUserList,
  getUserDetail,
  addUser,
  updateUser,
  deleteUser,
  resetUserPwd,
  changeUserPwd,
  grantUserRoles,
  getOrgList,
  queryOrgList,
  queryMemberByDeptId,
  getMemberDetail,
  getMemberList,
  queryMember,
  queryMsgList,
  getMsgByMsgId,
  getUserInMsgList,
  getMsgDetailByUserId,
  getMsgDetailByRoomId,
  getPageIndex,
  getCustomerList,
  queryCustomerList,
  getCustomerDetail,
  getOrganizationList,
  queryOrganization,
  delOrganization,
  addOrganization,
  updateOrganization,
  getAllOrganizationTree,
  getTenantList,
  queryTenant,
  delTenant,
  addTenant,
  updateTenant,
  queryTenantConfig,
  createTenantTask,
  executeJob,
  updateTenantTask,
  queryTaskLog,
  mngGetuthsTree,
  mngGetTenantRoutes,
  mngCreateAuth,
  mngUpdateAuth
};
