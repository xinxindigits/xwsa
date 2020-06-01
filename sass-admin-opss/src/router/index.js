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

import Vue from "vue";
import VueRouter from "vue-router";
import store from "@/store";
import ViewUI from "view-design";
import { setTitle } from "@/libs/tools";
import routes from "./routers";
import config from "@/config";
Vue.use(VueRouter);

const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location, onResolve, onReject) {
  if (onResolve || onReject)
    return originalPush.call(this, location, onResolve, onReject);
  return originalPush.call(this, location).catch(err => err);
};
const originalReplace = VueRouter.prototype.replace;
VueRouter.prototype.replace = function repalce(location, onResolve, onReject) {
  if (onResolve || onReject)
    return originalReplace.call(this, location, onResolve, onReject);
  return originalReplace.call(this, location).catch(err => err);
};
const { homeName, loginName } = config;
const NOT_FOUND_ROUTER = {
  path: "*",
  name: "error_404",
  meta: {
    hideInMenu: true
  },
  component: () => import("@/views/error-page/404.vue")
};
const router = new VueRouter({
  routes
});
const initRouters = store => {
  if (store.state.user.hasGetInfo) {
    if (
      !(
        store.state.app.hasGetRouter &&
        store.state.app.routers &&
        store.state.app.routers.length > 0
      )
    ) {
      store
        .dispatch("getMenuInfo")
        .then(routers => {
          const newRouter = new VueRouter({
            routes,
            mode: config.routerModel
          });
          router.matcher = newRouter.matcher;
          router.addRoutes(routers.concat([NOT_FOUND_ROUTER]));
        })
        .finally(() => {});
    }
  }
};
router.beforeEach((to, from, next) => {
  console.log(to.name);
  ViewUI.LoadingBar.start();
  const { token, xTenant } = store.getters;
  if (!token && to.name !== loginName) {
    next({
      name: loginName
    });
  } else if (!token && to.name === loginName) {
    next();
  } else if (!xTenant && to.name === loginName) {
    next({
      name: "manage-tenant"
    });
  } else if (token && to.name === loginName) {
    next({
      name: homeName
    });
  } else {
    if (store.state.user.hasGetInfo) {
      initRouters(store);
      next();
    } else {
      store
        .dispatch("getUserInfo")
        .then(() => {
          initRouters(store);
          next();
        })
        .catch(() => {
          store.commit("setToken", "");
          next({
            name: "login"
          });
        });
    }
  }
});
router.afterEach(to => {
  setTitle(to, router.app);
  ViewUI.LoadingBar.finish();
  window.scrollTo(0, 0);
});
export default router;
