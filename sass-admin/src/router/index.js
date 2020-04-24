import Vue from "vue";
import VueRouter from "vue-router";
import store from "@/store";
import ViewUI from "view-design";
import { setTitle } from "@/libs/util";
import routes from "./routers";
// import { loadMenu } from "./routers";
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
      store.state.app.hasGetRouter &&
      store.state.app.routers &&
      store.state.app.routers.length > 0
    ) {
      console.log("已经加载过了路由");
    } else {
      //加载路由
      console.log("开始加载路由权限...");
      store
        .dispatch("getMenuInfo")
        .then(routers => {
          //此处routers已经是按照权限过滤后的路由了，没权限的，不加入路由，无法访问
          //路由重置一下把404放最后
          const newRouter = new VueRouter({
            routes,
            mode: config.routerModel
          });
          router.matcher = newRouter.matcher;
          //把404加最后面，如果用router.push({name:'xxxx'})这种的话，404页面可能空白，用path:'/aa/bb'
          router.addRoutes(routers.concat([NOT_FOUND_ROUTER]));
        })
        .finally(() => {});
    }
  }
};
router.beforeEach((to, from, next) => {
  ViewUI.LoadingBar.start();
  const token = store.getters.token;
  if (!token && to.name !== LOGIN_PAGE_NAME) {
    next({
      name: LOGIN_PAGE_NAME
    });
  } else if (!token && to.name === LOGIN_PAGE_NAME) {
    next();
  } else if (token && to.name === LOGIN_PAGE_NAME) {
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
          console.log(4);
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
