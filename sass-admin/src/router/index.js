import Vue from "vue";
import VueRouter from "vue-router";
import store from "@/store";
import ViewUI from "view-design";
import { setTitle } from "@/libs/util";
import routes from "./routers";
import { loadMenu } from "./routers";
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
const { homeName } = config;
const LOGIN_PAGE_NAME = "login";
const router = new VueRouter({
  routes: [...routes, ...loadMenu()]
});
router.beforeEach((to, from, next) => {
  ViewUI.LoadingBar.start();
  const token = store.getters.token;
  if (!token && to.name !== LOGIN_PAGE_NAME) {
    // 未登录且要跳转的页面不是登录页
    next({
      name: LOGIN_PAGE_NAME // 跳转到登录页
    });
  } else if (!token && to.name === LOGIN_PAGE_NAME) {
    // 未登陆且要跳转的页面是登录页
    next(); // 跳转
  } else if (token && to.name === LOGIN_PAGE_NAME) {
    // 已登录且要跳转的页面是登录页
    next({
      name: homeName // 跳转到homeName页
    });
  } else {
    if (store.state.user.hasGetInfo) {
      next();
    } else {
      store
        .dispatch("getUserInfo")
        .then(() => {
          store.dispatch("getMenuInfo").then(() => {
            next();
          });
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
