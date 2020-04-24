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
          <Content class="content-wrapper">
            <keep-alive>
              <router-view />
            </keep-alive>
          </Content>
        </Layout>
      </Content>
    </Layout>
  </Layout>
</template>

<script>
import HeaderBar from "./components/header-bar";
import User from "./components/user";
import SideMenu from "./components/side-menu";
import maxLogo from "@/assets/images/logo-bg.png";
import minLogo from "@/assets/images/logo-min.png";
import { mapMutations } from "vuex";
import routers from "@/router/routers";
import bread_crumb from "./components/custom-bread-crumb/mixin";
import "./main.less";
export default {
  name: "Main",
  mixins: [bread_crumb],
  components: {
    HeaderBar,
    User,
    SideMenu
  },
  computed: {
    menuList() {
      return this.$store.getters.menuList;
    },
    account() {
      return this.$store.state.user.account;
    }
  },
  data() {
    return {
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
    ...mapMutations(["setHomeRoute"]),
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
    }
  },
  mounted() {},
  watch: {
    $route(newRoute) {
      this.$refs.sideMenu.updateOpenName(newRoute.name);
    }
  }
};
</script>

<style lang="less" scoped></style>
