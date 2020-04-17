import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import "./plugins/iview.js";
import config from "@/config";
/* eslint-disable */
// if (process.env.NODE_ENV !== "production") require("@/mock");
Vue.config.productionTip = false;
Vue.prototype.$config = config;
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
