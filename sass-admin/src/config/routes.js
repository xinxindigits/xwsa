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
    name: "user_list",
    meta: {
      title: "用户查询功能"
    },
    component: () => import("@/views/system/user/user.vue")
  },
  ["/user/query"]: {
    name: "user_query",
    meta: {
      title: "用户查询功能"
    },
    component: () => import("@/views/system/user/user.vue")
  },
  ["/weixin"]: {
    path: "/wexin",
    name: "weixin",
    meta: {
      icon: "ios-hammer",
      title: "企业微信管理",
      showAlways: true
    }
  },
  ["/org/list"]: {
    name: "org_list",
    meta: {
      title: "部门管理"
    },
    component: () => import("@/views/wechat/org/org.vue")
  },
  ["/staff/list"]: {
    name: "staff_list",
    meta: {
      title: "员工管理"
    },
    component: () => import("@/views/wechat/org/org.vue")
  },
  ["/message/list"]: {
    name: "message_list",
    meta: {
      title: "数据管理"
    },
    component: () => import("@/views/wechat/message/message.vue")
  },
  ["/role/list"]: {
    name: "role_list",
    meta: {
      title: "角色管理"
    },
    component: () => import("@/views/system/role/role.vue")
  },
  ["/resource/list"]: {
    name: "resource_list",
    meta: {
      title: "权限资源管理"
    },
    component: () => import("@/views/system/resource/resource.vue")
  },
  ["/organization/list"]: {
    name: "organization_list",
    meta: {
      title: "组织结构管理"
    },
      component: () => import("@/views/system/organization/organization.vue")
    }
};
export default routes_config;
