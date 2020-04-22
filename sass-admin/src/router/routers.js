import Main from "../components/layout";
import { localRead, hasChild } from "@/libs/util";
import { forEach } from "@/libs/tools";
import routes_config from "@/config/routes";

// 加载菜单
export const loadMenu = () => {
  let list = [];
  let data = localRead("route");
  if (!data) {
    return list;
  }
  list = formatMenu(JSON.parse(data));
  return list;
};

// 格式化菜单
export const formatMenu = list => {
  let res = [];
  //还要加上code和path的转换
  forEach(list, item => {
    let ROUTES_CONFIG = routes_config[item.url];
    if (!ROUTES_CONFIG) {
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
        ...ROUTES_CONFIG
      };
      obj.meta.title = item.text;
      if (item.parentId == "0") {
        obj.component = Main;
      } else {
        obj.path = item.url.replace("/", "");
      }
      if (hasChild(item)) {
        obj.children = formatMenu(item.children);
      }
      res.push(obj);
    }
  });
  return res;
};

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
  }
];
