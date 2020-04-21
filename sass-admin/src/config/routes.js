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
    // component: Main
  },
  ["/org/list"]: {
    name: "org_list",
    meta: {
      title: "部门管理"
    },
    component: () => import("@/views/system/org/org.vue")
  },
  ["/staff/list"]: {
    name: "staff_list",
    meta: {
      title: "员工管理"
    },
    component: () => import("@/views/system/org/org.vue")
  },
  ["/message/list"]: {
    name: "message_list",
    meta: {
      title: "数据管理"
    },
    component: () => import("@/views/system/message/message.vue")
  },
  ["/organization/create"]: {
    name: "organization_create",
    meta: {
      title: "组织机构创建"
    },
    component: () => import("@/views/system/organization/create.vue")
  },
  ["/role/list"]: {
    name: "role_list",
    meta: {
      title: "角色管理"
    },
    component: () => import("@/views/system/role/role.vue")
  },
  ["/role/create"]: {
    name: "role_create",
    meta: {
      title: "角色创建"
    },
    component: () => import("@/views/system/role/role.vue")
  },
  ["/role/query"]: {
    name: "role_query",
    meta: {
      title: "角色查询"
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
  ["/resource/create"]: {
    name: "resource_create",
    meta: {
      title: "新增资源权限"
    },
    component: () => import("@/views/system/resource/resource.vue")
  },
  ["/resource/update"]: {
    name: "resource_update",
    meta: {
      title: "更新资源权限"
    },
    component: () => import("@/views/system/resource/resource.vue")
  },
  ["/resource/delete"]: {
    name: "resource_delete",
    meta: {
      title: "删除资源权限"
    },
    component: () => import("@/views/system/resource/resource.vue")
  }
};
export default routes_config;
