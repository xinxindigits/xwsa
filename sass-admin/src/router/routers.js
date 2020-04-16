import Main from "@/components/layout/main.vue";
export default [
  {
    path: "/login",
    name: "login",
    meta: {
      title: "登录",
      hideInMenu: true
    },
    component: () => import("@/views/login/login.vue")
  },
  {
    path: "/",
    name: "_home",
    redirect: "/home",
    component: Main,
    meta: {
      hideInMenu: true,
      notCache: true
    },
    children: [
      {
        path: "/home",
        name: "home",
        meta: {
          hideInMenu: true,
          title: "首页",
          notCache: true,
          icon: "md-home"
        },
        component: () => import("@/views/home")
      }
    ]
  },
  {
    path: "/authority",
    name: "components",
    meta: {
      icon: "ios-hammer",
      title: "权限管理"
    },
    component: Main,
    children: [
      {
        path: "role",
        name: "role",
        meta: {
          title: "角色管理"
        },
        component: () => import("@/views/authority/role/role.vue")
      },
      {
        path: "menuscope",
        name: "menuscope",
        meta: {
          title: "数据权限"
        },
        component: () => import("@/views/authority/menuscope/menuscope.vue")
      },
      {
        path: "apiscope",
        name: "apiscope",
        meta: {
          title: "接口权限"
        },
        component: () => import("@/views/authority/apiscope/apiscope.vue")
      }
    ]
  },
  {
    path: "/system",
    name: "system",
    meta: {
      icon: "ios-hammer",
      title: "系统管理"
    },
    component: Main,
    children: [
      {
        path: "user",
        name: "user",
        meta: {
          title: "用户管理"
        },
        component: () => import("@/views/system/user/user.vue")
      },
      {
        path: "org",
        name: "org",
        meta: {
          title: "机构管理"
        },
        component: () => import("@/views/system/org/org.vue")
      },
      {
        path: "post",
        name: "post",
        meta: {
          title: "岗位"
        },
        component: () => import("@/views/system/post/post.vue")
      }
    ]
  }
];
