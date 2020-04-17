import { login, getUserInfo, logout } from "@/api/user";
export default {
  state: {
    account: "",
    userName: "",
    avatarImgPath: "",
    access: "super_admin",
    token: "",
    hasGetInfo: false
  },
  mutations: {
    setAvatar(state, avatarPath) {
      state.avatarImgPath = avatarPath;
    },
    setUserName(state, id) {
      state.userName = id;
    },
    setAccount(state, name) {
      state.account = name;
    },
    setAccess(state, access) {
      state.access = access;
    },
    setToken(state, token) {
      state.token = token;
    },
    setHasGetInfo(state, status) {
      state.hasGetInfo = status;
    }
  },
  actions: {
    // 登录
    handleLogin({ commit }, { account, password }) {
      account = account.trim();
      return new Promise((resolve, reject) => {
        login({
          account,
          password
        })
          .then(res => {
            const data = res.data;
            commit("setToken", data.token);
            commit("setAccount", data.account);
            resolve();
          })
          .catch(err => {
            reject(err);
          });
      });
    },
    // 获取用户相关信息
    getUserInfo({ state, commit }) {
      return new Promise((resolve, reject) => {
        getUserInfo(state.account)
          .then(res => {
            const data = res.data;
            commit("setUserName", data.name || "");
            commit("setHasGetInfo", true);
            resolve(data);
          })
          .catch(err => {
            reject(err);
          });
      });
    },
    handleLogOut({ commit }) {
      return new Promise((resolve, reject) => {
        logout()
          .then(() => {
            commit("setToken", "");
            commit("setUserName", "");
            // commit('setAccess', [])
            resolve();
          })
          .catch(err => {
            reject(err);
          });
      });
    }
  }
};
