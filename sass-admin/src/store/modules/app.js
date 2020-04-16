import { getBreadCrumbList, getHomeRoute, getMenuByRouter } from "@/libs/util";
import routers from "@/router/routers";
import config from "@/config";
const { homeName } = config;
export default {
  state: {
    homeRoute: {},
    breadCrumbList: []
  },
  getters: {
    menuList: (state, getters, rootState) =>
      getMenuByRouter(routers, rootState.user.access)
  },
  mutations: {
    setHomeRoute(state, routes) {
      state.homeRoute = getHomeRoute(routes, homeName);
    },
    setBreadCrumb(state, route) {
      state.breadCrumbList = getBreadCrumbList(route, state.homeRoute);
    }
  }
};
