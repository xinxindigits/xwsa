import { getBreadCrumbList, getHomeRoute, getMenuByRouter } from "@/libs/util";
import { formatMenu } from "@/router/routers";
import { getMenuInfo } from "@/api/user";
import routers from "@/router/routers";
import config from "@/config";
const { homeName } = config;
export default {
  state: {
    homeRoute: {},
    breadCrumbList: [],
    hasGetRouter: false,
    routers: []
  },
  getters: {
    menuList: (state, getters, rootState) =>
      getMenuByRouter(routers.concat(state.routers), rootState.user.access)
  },
  mutations: {
    setHomeRoute(state, routes) {
      state.homeRoute = getHomeRoute(routes, homeName);
    },
    setBreadCrumb(state, route) {
      state.breadCrumbList = getBreadCrumbList(route, state.homeRoute);
    },
    //从后台获取路由数据
    setRouters(state, routers) {
      state.routers = routers;
    },
    setHasGetRouter(state, status) {
      state.hasGetRouter = status;
    }
  },
  actions: {
    getMenuInfo({ commit }) {
      return new Promise((resolve, reject) => {
        getMenuInfo()
          .then(res => {
            let routers = formatMenu(res.data);
            commit("setRouters", routers);
            commit("setHasGetRouter", true);
            resolve(routers);
          })
          .catch(reject);
      });
    }
  }
};
