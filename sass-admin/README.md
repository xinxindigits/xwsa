# sass-admin

sass-admin是基于ViewUI的Vue后台管理系统


## 1. 如何运行

### 1.1 开发环境配置

本地运行需要node环境。运行时确保后台服务已经启动，本地运行可在vue.config.js中配置代理。 

### 1.2 开发过程

#### 1.2.1 命令

```sh
cd sass-admin
# 安装依赖
npm install
# 开发
npm run serve

# 发布
npm run build

# 代码检查
npm run lint
```



## 2.二次开发说明

### 2.1 目录结构

```
.
├── README.md ----------------------------------- 说明文档
├── babel.config.js
├── package-lock.json
├── package.json
├── public
│   ├── favicon.ico------------------------------ icon图标
│   └── index.html
├── src
│   ├── App.vue
│   ├── api--------------------------------------- 接口管理
│   ├── assets------------------------------------ 图片等资源
│   ├── components-------------------------------- 通用组件
│   ├── config------------------------------------ 业务相关配置
│   │   └── index.js------------------------------ 项目名、主页、baseUrl等全局配置
│   ├── iview-variables.less---------------------- iview主题配置
│   ├── libs
│   │   ├── http.js
│   │   ├── axios.js------------------------------ axios配置，拦截器以及异常处理
│   │   ├── dic.js-------------------------------- 存放通用枚举
│   │   ├── filters.js---------------------------- 全局的过滤器
│   │   ├── tools.js------------------------------ 业务相关工具方法
│   │   └── util.js------------------------------- 通用工具方法
│   ├── main.js----------------------------------- 程序入口
│   ├── plugins
│   │   └── iview.js------------------------------ iview全局配置
│   ├── router
│   │   ├── dynamic_routers.js-------------------- 动态路由配置
│   │   ├── index.js------------------------------ 路由初始化以及全局导航守卫 
│   │   └── routers.js---------------------------- 基础路由
│   └──store------------------------------------- vuex
│   └── views
│       ├── error-page---------------------------- 404等错误页
│       ├── home.vue------------------------------ 首页
│       ├── login--------------------------------- 登录页
│       ├── profile------------------------------- 修改密码
│       ├── system-------------------------------- 系统管理
│       └── wechat-------------------------------- 企业微信管理
└── vue.config.js--------------------------------- vue配置文件

```

### 2.2通用配置

```
//./src/config/index.js
export default {
  title: "title_prefix",//页面的title格式为`${title}-${路由meta.title}`
  baseUrl: {//api请求基础路径
    dev: "",
    pro: ""
  },
  homeName: "home",//主页vue组件的name
  loginName: "login"//登录页vue组件的name
}
```



### 2.3路由配置

#### 2.3.1可配置项

```
meta: {
  hideInMenu: (default: false) 设为true后在左侧菜单不会显示该页面选项
  showAlways: (default: false) 设为true后如果该路由只有一个子路由，在菜单中也会显示该父级菜单
  notCache: (default: false) 设为true后页面不会缓存
  icon: (default: -) 该页面在左侧菜单、面包屑和标签导航处显示的图标，如果是自定义图标，需要在图标名称前加下划线'_'
  href: 'https://xxx' (default: null) 用于跳转到外部连接
}
```

#### 2.3.2新增路由

##### 新增受权限控制的动态路由

```js
// ./src/config/routers.js
 let routes_config = {
 		//...
 		["/system/newroute"]: {//对应URI
    		path: "/wechat/newroute",
   			name: "wc-message",//要与component项指向的vue组件name属性保持一致
      	meta: {
      	title: "title",
      	notCache: true,
        beforeCloseName:""
    	},
    	component: () => import("@/views/wechat/message/message.vue")
  	}
   //...
 }
```

##### 新增固定路由

在./src/router/routers.js中加入路由配置即可

##### 其他

一级目录必须使用Main组件作为component，如果子路由不是页面，而是二级即更多级目录，需要用parentView组件





