<template>
  <Menu mode="horizontal" class="header-bar" width="100%">
    <sider-trigger
      :collapsed="collapsed"
      icon="md-menu"
      @on-change="handleCollpasedChange"
      style="float:left"
    ></sider-trigger>

    <div class="header-bar-nav">
      <MenuItem name="home" to="home">
        <Icon type="ios-home" size="21" style="margin-right:0" />
        首页
      </MenuItem>
      <MenuItem name="mine">
        我的工作
      </MenuItem>
      <MenuItem name="monitor">
        监控流程
      </MenuItem>
      <MenuItem name="sysconfig">
        系统配置
      </MenuItem>
    </div>
    <div class="custom-content-con">
      <slot></slot>
    </div>
  </Menu>
</template>
<script>
import siderTrigger from "./sider-trigger";
import "./header-bar.less";
export default {
  name: "HeaderBar",
  components: {
    siderTrigger
  },
  props: {
    collapsed: Boolean
  },
  computed: {},
  methods: {
    handleCollpasedChange(state) {
      this.$emit("on-coll-change", state);
    }
  }
};
</script>
