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

import Main from "../components/layout";
import { hasChild } from "@/libs/util";
import { forEach } from "@/libs/util";
import dynamicRouters from "./dynamic-routers";

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
