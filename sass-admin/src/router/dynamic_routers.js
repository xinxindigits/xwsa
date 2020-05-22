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

// dynamicRouters中key值和path要和后台的资源URI一致,name属性和对应vue文件name属性也要保持一致
let dynamicRouters = {
  ["/system"]: {
    path: "/system",
    name: "system",
    meta: {
      icon: "ios-hammer",
      title: "系统管理",
      showAlways: true
    }
  },
  ["/user/list"]: {
    path: "user/list",
    name: "sys-user",
    meta: {
      title: "用户查询功能",
      notCache: true
    },
    component: () => import("@/views/system/user/user.vue")
  },
  ["/role/list"]: {
    path: "role/list",
    name: "sys-role",
    meta: {
      title: "角色管理",
      notCache: true
    },
    component: () => import("@/views/system/role/role.vue")
  },
  ["/resource/list"]: {
    path: "resource/list",
    name: "sys-resource",
    meta: {
      title: "权限资源管理",
      notCache: true
    },
    component: () => import("@/views/system/resource/resource.vue")
  },
  ["/tenant/list"]: {
    path: "tenant/list",
    name: "sys-tenant",
    meta: {
      title: "租户管理",
      notCache: true
    },
    component: () => import("@/views/system/tenant/tenant.vue")
  },
  ["/organization/list"]: {
    path: "organization/list",
    name: "sys-organization",
    meta: {
      title: "租户管理",
      notCache: true
    },
    component: () => import("@/views/system/organization/organization.vue")
  },
  ["/tags/list"]: {
    path: "tags/list",
    name: "sys-tag",
    meta: {
      title: "标签管理",
      notCache: true
    },
    component: () => import("@/views/system/tag/tag.vue")
  },
  ["/ops/log/list"]: {
    path: "ops/log/list",
    name: "sys-log",
    meta: {
      title: "操作日志管理",
      notCache: true
    },
    component: () => import("@/views/system/log/log.vue")
  },
  ["/tenant/queryConfig"]: {
    path: "tenant/queryConfig",
    name: "sys-task",
    meta: {
      title: "任务配置管理",
      notCache: true
    },
    component: () => import("@/views/system/task/task.vue")
  },
  ["/wechat"]: {
    path: "/wechat",
    name: "wechat",
    meta: {
      icon: "ios-hammer",
      title: "企业微信管理",
      showAlways: true,
      notCache: true
    }
  },
  ["/wechat/dept/list"]: {
    path: "/wechat/dept/list",
    name: "wc-org",
    meta: {
      title: "部门管理",
      notCache: true
    },
    component: () => import("@/views/wechat/org/org.vue")
  },
  ["/wechat/staff/list"]: {
    path: "/wechat/staff/list",
    name: "wc-staff",
    meta: {
      title: "员工管理",
      notCache: true
    },
    component: () => import("@/views/wechat/staff/staff.vue")
  },
  ["/wechat/customer/list"]: {
    path: "/wechat/customer/list",
    name: "wc-customer",
    meta: {
      title: "客户管理",
      notCache: true
    },
    component: () => import("@/views/wechat/customer/customer.vue")
  },
  ["/wechat/message/list"]: {
    path: "/wechat/message/list",
    name: "wc-message",
    meta: {
      title: "数据管理",
      notCache: true
    },
    component: () => import("@/views/wechat/message/message.vue")
  }
};
export default dynamicRouters;
