<template>
  <Layout style="height: 100%" class="main">
    <Sider
      hide-trigger
      collapsible
      :width="256"
      :collapsed-width="64"
      v-model="collapsed"
      class="left-sider"
      :style="{ overflow: 'hidden' }"
    >
      <side-menu
        accordion
        ref="sideMenu"
        :active-name="$route.name"
        :collapsed="collapsed"
        :menu-list="menuList"
        @on-select="turnToPage"
      >
        <div class="logo-con">
          <img v-show="!collapsed" :src="maxLogo" key="max-logo" />
          <img v-show="collapsed" :src="minLogo" key="min-logo" />
        </div>
      </side-menu>
    </Sider>
    <Layout>
      <Header class="header-con">
        <header-bar
          :collapsed="collapsed"
          @on-coll-change="handleCollapsedChange"
        >
          <custom-bread-crumb
            show-icon
            style="margin-left: 30px;"
            :list="breadCrumbList"
            slot="nav"
          />
          <user :user-avatar="userAvatar" :account="account"></user>
        </header-bar>
      </Header>
      <Content class="main-content-con">
        <Layout class="main-layout-con">
          <div class="tag-nav-wrapper">
            <tags-nav
              :value="$route"
              @input="handleClick"
              :list="tagNavList"
              @on-close="handleCloseTag"
            />
          </div>
          <Content class="content-wrapper">
            <keep-alive :include="cacheList">
              <router-view v-if="isKeepAlive" />
            </keep-alive>
          </Content>
        </Layout>
      </Content>
    </Layout>
  </Layout>
</template>

<script>
import HeaderBar from "./components/header-bar";
import TagsNav from "./components/tags-nav";
import User from "./components/user";
import SideMenu from "./components/side-menu";
import maxLogo from "@/assets/images/logo-bg.png";
import minLogo from "@/assets/images/logo-min.png";
import { mapMutations } from "vuex";
import routers from "@/router/routers";
import bread_crumb from "./components/custom-bread-crumb/mixin";
import { getNewTagList, routeEqual } from "@/libs/tools";
import "./main.less";
export default {
  name: "Main",
  mixins: [bread_crumb],
  components: {
    HeaderBar,
    TagsNav,
    User,
    SideMenu
  },
  computed: {
    menuList() {
      return this.$store.getters.menuList;
    },
    tagNavList() {
      return this.$store.state.app.tagNavList;
    },
    cacheList() {
      const list = [
        ...(this.tagNavList.length
          ? this.tagNavList
              .filter(item => !(item.meta && item.meta.notCache))
              .map(item => item.name)
          : [])
      ];
      return list;
    },
    account() {
      return this.$store.state.user.account;
    }
  },
  data() {
    return {
      isKeepAlive: true,
      collapsed: false,
      userAvatar: minLogo,
      maxLogo,
      minLogo
    };
  },
  beforeMount() {
    this.setHomeRoute(routers);
  },
  methods: {
    ...mapMutations(["setHomeRoute", "setTagNavList", "addTag", "closeTag"]),
    handleCollapsedChange(state) {
      this.collapsed = state;
    },
    turnToPage(route) {
      let { name, params, query } = {};
      if (typeof route === "string") name = route;
      else {
        name = route.name;
        params = route.params;
        query = route.query;
      }
      if (name.indexOf("isTurnByHref_") > -1) {
        window.open(name.split("_")[1]);
        return;
      }
      this.$router.push({
        name,
        params,
        query
      });
      if (name == this.$route.name) this.reload();
    },
    handleCloseTag(res, type, route) {
      if (type !== "others") {
        if (type === "all") {
          this.turnToPage(this.$config.homeName);
        } else {
          if (routeEqual(this.$route, route)) {
            this.closeTag(route);
          }
        }
      }
      this.setTagNavList(res);
    },
    handleClick(item) {
      this.turnToPage(item);
    },
    reload() {
      this.isKeepAlive = false;
      this.$nextTick(() => (this.isKeepAlive = true));
    }
  },
  mounted() {
    this.setTagNavList();
    const { name, params, query, meta } = this.$route;
    this.addTag({
      route: { name, params, query, meta }
    });
    if (!this.tagNavList.find(item => item.name === this.$route.name)) {
      this.$router.push({
        name: this.$config.homeName
      });
    }
  },
  watch: {
    $route(newRoute) {
      const { name, query, params, meta } = newRoute;
      this.addTag({
        route: { name, query, params, meta },
        type: "push"
      });
      this.setTagNavList(getNewTagList(this.tagNavList, newRoute));
      this.$refs.sideMenu.updateOpenName(newRoute.name);
    }
  }
};
</script>
