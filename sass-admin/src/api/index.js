import {
  getTagList,
  queryTagList,
  createTag,
  updateTag,
  deleteTag,
  setTagByKeyId
} from "./sys_tag";
import {
  getOrganizationList,
  queryOrganization,
  delOrganization,
  addOrganization,
  updateOrganization
} from "./sys_organization";
import {
  getTenantList,
  queryTenant,
  delTenant,
  addTenant,
  updateTenant
} from "./sys_tenant";
import { getLogList, queryLogList, getLogDetail } from "./sys_log";
import {
  getUserList,
  getUserDetail,
  addUser,
  updateUser,
  deleteUser,
  grantUserRoles
} from "./data_user";
import { getOrgList, queryOrgList, queryMemberByDeptId } from "./wc_org";
import { getMemberDetail, getMemberList, queryMember } from "./wc_mem";
import {
  queryMsgList,
  getMsgByMsgId,
  getUserInMsgList,
  getMsgDetailByUserId,
  getMsgDetailByRoomId
} from "./wc_msg";
import {
  getCustomerList,
  queryCustomerList,
  getCustomerDetail
} from "./wc_cus";
export {
  getTagList,
  queryTagList,
  createTag,
  updateTag,
  deleteTag,
  setTagByKeyId,
  getLogList,
  queryLogList,
  getLogDetail,
  getUserList,
  getUserDetail,
  addUser,
  updateUser,
  deleteUser,
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
  getCustomerList,
  queryCustomerList,
  getCustomerDetail,
  getOrganizationList,
  queryOrganization,
  delOrganization,
  addOrganization,
  updateOrganization,
  getTenantList,
  queryTenant,
  delTenant,
  addTenant,
  updateTenant
};
