import {
  getBreadCrumbList,
  getHomeRoute,
  getMenuByRouter,
  getNextRoute,
  routeEqual,
  setTagNavListInLocalstorage,
  getTagNavListFromLocalstorage,
  getRouteTitleHandled,
  routeHasExist
} from "@/libs/tools";
import { formatMenu } from "@/router/routers";
import { getMenuInfo } from "@/api";
import router from "@/router";
import routers from "@/router/routers";
import config from "@/config";
const { homeName } = config;
const closePage = (state, route) => {
  const nextRoute = getNextRoute(state.tagNavList, route);
  state.tagNavList = state.tagNavList.filter(item => {
    return !routeEqual(item, route);
  });
  router.push(nextRoute);
};
export default {
  state: {
    homeRoute: {},
    breadCrumbList: [],
    hasGetRouter: false,
    routers: [],
    tagNavList: []
  },
  getters: {
    menuList: state => getMenuByRouter(routers.concat(state.routers))
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
    },

    setTagNavList(state, list) {
      let tagList = [];
      if (list) {
        tagList = [...list];
      } else tagList = getTagNavListFromLocalstorage() || [];
      if (tagList[0] && tagList[0].name !== homeName) tagList.shift();
      let homeTagIndex = tagList.findIndex(item => item.name === homeName);
      if (homeTagIndex > 0) {
        let homeTag = tagList.splice(homeTagIndex, 1)[0];
        tagList.unshift(homeTag);
      }
      state.tagNavList = tagList;

      setTagNavListInLocalstorage([...tagList]);
    },
    closeTag(state, route) {
      let tag = state.tagNavList.filter(item => routeEqual(item, route));
      route = tag[0] ? tag[0] : null;
      if (!route) return;
      closePage(state, route);
    },
    addTag(state, { route, type = "unshift" }) {
      let router = getRouteTitleHandled(route);
      if (!routeHasExist(state.tagNavList, router)) {
        if (type === "push") state.tagNavList.push(router);
        else {
          if (router.name === homeName) state.tagNavList.unshift(router);
          else state.tagNavList.splice(1, 0, router);
        }
        setTagNavListInLocalstorage([...state.tagNavList]);
      }
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
