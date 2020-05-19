import axios from "axios";
import store from "@/store";
import { Message, LoadingBar } from "view-design";
const addErrorLog = errorInfo => {
  const {
    statusText,
    status,
    request: { responseURL }
  } = errorInfo;
  let info = {
    type: "ajax",
    code: status,
    mes: statusText,
    url: responseURL
  };
  if (!responseURL.includes("save_error_logger"))
    store.dispatch("addErrorLog", info);
};

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
            console.log("refresh");
            store.commit("setToken", res.headers[arr[0]]);
            console.log("token updated");
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
              store.dispatch("handleLogout").then(() => {
                this.$router.push({
                  name: "login"
                });
              });
            }
          });
          store.commit("setToken", "");
        }
        if (!errorInfo) {
          const {
            request: { statusText, status },
            config
          } = JSON.parse(JSON.stringify(error));
          errorInfo = {
            statusText,
            status,
            request: { responseURL: config.url }
          };
        }
        addErrorLog(errorInfo);
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
