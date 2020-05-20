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
} from "./sys_res";
import {
  getRoleList,
  addRole,
  updateRole,
  delRole,
  grantRole,
  getAllRoles
} from "./sys_role";
import {
  getTagList,
  queryTagList,
  createTag,
  updateTag,
  deleteTag,
  setTagByKeyId,
  getAllTags
} from "./sys_tag";
import {
  getOrganizationList,
  queryOrganization,
  delOrganization,
  addOrganization,
  updateOrganization,
  getAllOrganizationTree
} from "./sys_organization";
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
} from "./sys_tenant";
import { getLogList, queryLogList, getLogDetail } from "./sys_log";
import {
  getUserList,
  getUserDetail,
  addUser,
  updateUser,
  deleteUser,
  grantUserRoles,
  resetUserPwd,
  changeUserPwd
} from "./data_user";
import { getOrgList, queryOrgList, queryMemberByDeptId } from "./wc_org";
import { getMemberDetail, getMemberList, queryMember } from "./wc_mem";
import {
  queryMsgList,
  getMsgByMsgId,
  getUserInMsgList,
  getMsgDetailByUserId,
  getMsgDetailByRoomId,
  getPageIndex
} from "./wc_msg";
import {
  getCustomerList,
  queryCustomerList,
  getCustomerDetail
} from "./wc_cus";
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
