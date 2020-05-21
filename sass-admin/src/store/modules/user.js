import { login, getUserInfo, logout } from "@/api";
export default {
  state: {
    account: "",
    userName: "",
    avatarImgPath: "",
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
    setToken(state, token) {
      state.token = token;
    },
    setHasGetInfo(state, status) {
      state.hasGetInfo = status;
    }
  },
  actions: {
    // 登录
    handleLogin({ commit }, { account, password, verifyCode }) {
      account = account.trim();
      return new Promise((resolve, reject) => {
        login({
          account,
          password,
          verifyCode
        })
          .then(res => {
            const data = res.data;
            commit("setToken", data.token);
            commit("setAccount", data.account);
            commit("setHasGetRouter", false);
            localStorage.removeItem("route");
            localStorage.removeItem("tagNavList");
            resolve();
          })
          .catch(reject);
      });
    },
    // 获取用户相关信息
    getUserInfo({ commit }) {
      return new Promise((resolve, reject) => {
        getUserInfo()
          .then(res => {
            const data = res.data;
            commit("setUserName", data.name || "");
            commit("setHasGetInfo", true);
            resolve(data);
          })
          .catch(reject);
      });
    },

    handleLogOut({ commit }) {
      return new Promise((resolve, reject) => {
        logout()
          .then(() => {
            commit("setToken", "");
            commit("setUserName", "");
            commit("setHasGetInfo", false);
            commit("setHasGetRouter", false);
            resolve();
          })
          .catch(reject);
      });
    }
  }
};
