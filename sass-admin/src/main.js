import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import "./plugins/iview.js";
import config from "@/config";
import _ from "lodash";
import { mapDic } from "@/libs/dic";
import filters from "@/libs/filters";
import md5 from "md5";
Object.keys(filters).forEach(k => Vue.filter(k, filters[k]));
/* eslint-disable */
// if (process.env.NODE_ENV !== "production") require("@/mock");
Vue.config.productionTip = false;
Vue.prototype.$config = config;
Vue.prototype._ = _;
Vue.prototype.$mapd = mapDic;
Vue.prototype.$md5 = md5;
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
