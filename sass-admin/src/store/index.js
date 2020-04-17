import Vue from "vue";
import Vuex from "vuex";
import app from "./modules/app";
import user from "./modules/user";
import getters from "./getters";
import VuexPersistence from "vuex-persist";
Vue.use(Vuex);
const vuexLocal = new VuexPersistence({
  key: "sass",
  storage: window.sessionStorage,
  reducer: state => ({ user: state.user })
});
export default new Vuex.Store({
  state: {},
  getters,
  mutations: {},
  actions: {},
  modules: { app, user },
  plugins: [vuexLocal.plugin]
});
