/*
 *
 * Copyright 2020 www.xinxindigits.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"),to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice
 * shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * Redistribution and selling copies of the software are prohibited, only if the authorization from xinxin digits
 * was obtained.Neither the name of the xinxin digits; nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 */

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
            commit("setTagNavList", [], { root: true });
            location.reload();
            resolve();
          })
          .catch(reject);
      });
    }
  }
};
