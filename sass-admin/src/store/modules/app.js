import {
  getBreadCrumbList,
  getHomeRoute,
  getMenuByRouter,
  localSave
} from "@/libs/util";
import { loadMenu, formatMenu } from "@/router/routers";
import { getMenuInfo } from "@/api/user";
import routers from "@/router/routers";
import config from "@/config";
const { homeName } = config;
export default {
  state: {
    homeRoute: {},
    breadCrumbList: []
  },
  getters: {
    menuList: (state, getters, rootState) => {
      return getMenuByRouter(
        [...routers, ...loadMenu()],
        rootState.user.access
      );
    }
  },
  mutations: {
    setHomeRoute(state, routes) {
      state.homeRoute = getHomeRoute(routes, homeName);
    },
    setBreadCrumb(state, route) {
      state.breadCrumbList = getBreadCrumbList(route, state.homeRoute);
    },
    updateMenuList(state, routes) {
      state.menuList = routes;
    }
  },
  actions: {
    getMenuInfo({ commit }) {
      return new Promise((resolve, reject) => {
        getMenuInfo()
          .then(res => {
            localSave("route", JSON.stringify(res.data));
            let list = [];
            list = formatMenu(res.data);
            list.push({
              path: "*",
              name: "error_404",
              meta: {
                hideInMenu: true
              },
              component: () => import("@/views/error-page/404.vue")
            });
            commit("updateMenuList", list);
            commit("setHasGetInfo", true, { root: true });
          })
          .catch(reject);
      });
    }
  }
};
