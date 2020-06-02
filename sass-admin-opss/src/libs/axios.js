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

import axios from "axios";
import store from "@/store";
import { Message, LoadingBar } from "view-design";

class HttpRequest {
  constructor(baseUrl) {
    this.baseUrl = baseUrl;
    this.queue = {};
  }
  getInsideConfig() {
    const config = {
      baseURL: this.baseUrl,
      headers: {},
      silent: false,
      validateStatus: function(status) {
        return (status >= 200 && status < 300) || status === 460;
      }
    };
    return config;
  }
  destroy(url) {
    delete this.queue[url];
    if (!Object.keys(this.queue).length) {
      LoadingBar.finish();
    }
  }
  interceptors(instance, url) {
    // 请求拦截
    instance.interceptors.request.use(
      config => {
        if (!Object.keys(this.queue).length) {
          LoadingBar.start();
        }
        this.queue[url] = true;
        config.headers.XToken = store.state.user.token;
        config.headers.XTenant = store.state.app.xTenant
          ? store.state.app.xTenant
          : store.state.user.userTenant;
        return config;
      },
      error => {
        return Promise.reject(error);
      }
    );
    // 响应拦截
    instance.interceptors.response.use(
      res => {
        this.destroy(url);
        const { data, status, config } = res;
        if (status && status === 460) {
          let arr = Object.keys(res.headers).filter(n => {
            return n.toUpperCase() == "XTOKEN";
          });
          if (res.headers && arr.length > 0) {
            store.commit("setToken", res.headers[arr[0]]);
          }
          return data;
        } else if (data && data.code != "SUCCESS") {
          let message = data.message || "响应数据异常:无响应描述";
          !config.silent && Message.error({ content: message, duration: 3 });
          const err = new Error(message);
          err.data = data;
          err.response = res;
          throw err;
        } else {
          return data;
        }
      },
      error => {
        this.destroy(url);
        let errorInfo = error.response;
        if (!errorInfo) {
          Message.error("服务器繁忙，请稍后再试!");
        } else if (errorInfo.status >= 500) {
          Message.error("服务器繁忙，请稍后再试！");
        } else if (errorInfo.status === 404) {
          Message.error("请求地址不存在！");
        } else if (errorInfo.status === 405) {
          Message.error("请求方法不合法！");
        } else if (errorInfo.status === 403) {
          Message.error("服务器禁止访问！");
        } else if (errorInfo.status === 400) {
          Message.error("请求错误，请检查参数或请求头");
        } else if (errorInfo.status === 401) {
          Message.error({
            content: "登录信息失效",
            duration: 3,
            onClose() {
              store.dispatch("handleLogOut");
            }
          });
          store.commit("setToken", "");
        }
        return Promise.reject(error);
      }
    );
  }
  request(options) {
    const instance = axios.create();
    options = Object.assign(this.getInsideConfig(), options);
    this.interceptors(instance, options.url);
    return instance(options);
  }
}
export default HttpRequest;
