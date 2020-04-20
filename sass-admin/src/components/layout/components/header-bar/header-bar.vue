<template>
  <Menu mode="horizontal" class="header-bar" width="100%">
    <sider-trigger
      :collapsed="collapsed"
      icon="md-menu"
      @on-change="handleCollpasedChange"
      style="float:left"
    ></sider-trigger>

    <div class="header-bar-nav">
      <slot name="nav"></slot>
      <div class="custom-content-con">
        <slot></slot>
      </div>
    </div>
  </Menu>
</template>
<script>
import siderTrigger from "./sider-trigger";
import bread_crumb from "../custom-bread-crumb/mixin";
import "./header-bar.less";
export default {
  name: "HeaderBar",
  mixins: [bread_crumb],
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
