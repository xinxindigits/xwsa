import Main from "../components/layout";
import { hasChild } from "@/libs/util";
import { forEach } from "@/libs/util";
import dynamicRouters from "./dynamic_routers";

// 格式化菜单
export const formatMenu = list => {
  let res = [];
  forEach(list, item => {
    let dyRouters = dynamicRouters[item.url];
    if (!dyRouters) {
      console.warn("no route config", item.url);
      let obj = {
        name: "not_found_" + item.code,
        meta: { title: item.text }
      };
      if (item.parentId == "0") {
        obj.component = Main;
        obj.meta.showAlways = true;
        obj.meta.icon = "ios-hammer";
      } else {
        obj.path = item.url.replace("/", "");
      }
      if (hasChild(item)) {
        obj.children = formatMenu(item.children);
      }
      res.push(obj);
    } else {
      let obj = {
        ...dyRouters
      };
      obj.meta.title = item.text;
      if (item.parentId == "0") {
        obj.component = Main;
      }
      if (hasChild(item)) {
        obj.children = formatMenu(item.children);
      }
      res.push(obj);
    }
  });
  return res;
};
//基础路由
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
      },
      {
        path: "/resetPwd",
        name: "resetPwd",
        meta: {
          hideInMenu: true,
          title: "修改密码",
          notCache: true,
          icon: "md-person"
        },
        component: () => import("@/views/profile/resetPwd.vue")
      }
    ]
  }
];
