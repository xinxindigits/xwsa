let routes_config = {
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
    name: "user_list",
    meta: {
      title: "用户查询功能"
    },
    component: () => import("@/views/system/user/user.vue")
  },
  ["/role/list"]: {
    path: "role/list",
    name: "role_list",
    meta: {
      title: "角色管理"
    },
    component: () => import("@/views/system/role/role.vue")
  },
  ["/resource/list"]: {
    path: "resource/list",
    name: "resource_list",
    meta: {
      title: "权限资源管理"
    },
    component: () => import("@/views/system/resource/resource.vue")
  },
  ["/tenant/list"]: {
    path: "tenant/list",
    name: "tenant_list",
    meta: {
      title: "租户管理"
    },
    component: () => import("@/views/system/tenant/tenant.vue")
  },
  ["/organization/list"]: {
    path: "organization/list",
    name: "organization",
    meta: {
      title: "租户管理"
    },
    component: () => import("@/views/system/organization/organization.vue")
  },
  ["/tags/list"]: {
    path: "tags/list",
    name: "tag_list",
    meta: {
      title: "标签管理"
    },
    component: () => import("@/views/system/tag/tag.vue")
  },
  ["/ops/log/list"]: {
    path: "ops/log/list",
    name: "oplog_list",
    meta: {
      title: "操作日志管理"
    },
    component: () => import("@/views/system/log/log.vue")
  },
  ["/tenant/queryConfig"]: {
    path: "tenant/queryConfig",
    name: "task_config",
    meta: {
      title: "任务配置管理"
    },
    component: () => import("@/views/system/task/task.vue")
  },
  ["/wechat"]: {
    path: "/wechat",
    name: "wechat",
    meta: {
      icon: "ios-hammer",
      title: "企业微信管理",
      showAlways: true
    }
  },
  ["/wechat/dept/list"]: {
    path: "/wechat/dept/list",
    name: "org_list",
    meta: {
      title: "部门管理"
    },
    component: () => import("@/views/wechat/org/org.vue")
  },
  ["/wechat/staff/list"]: {
    path: "/wechat/staff/list",
    name: "wc_staff",
    meta: {
      title: "员工管理"
    },
    component: () => import("@/views/wechat/staff/staff.vue")
  },
  ["/wechat/customer/list"]: {
    path: "/wechat/customer/list",
    name: "wc_customer",
    meta: {
      title: "客户管理"
    },
    component: () => import("@/views/wechat/customer/customer.vue")
  },
  ["/wechat/message/list"]: {
    path: "/wechat/message/list",
    name: "message_list",
    meta: {
      title: "数据管理"
    },
    component: () => import("@/views/wechat/message/message.vue")
  }
};
export default routes_config;
