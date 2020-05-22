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
  queryTaskLog
};
