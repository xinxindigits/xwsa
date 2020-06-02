<template>
  <div class="toggle" @click="hdlClick">
    租户选择
  </div>
</template>

<script>
export default {
  name: "toggle",
  methods: {
    hdlClick() {
      this.$emit("on-toggle-tenant", "");
    }
  }
};
</script>

<style lang="less" scoped>
.toggle {
  font-weight: bold;
  cursor: pointer;
  display: inline-block;
  margin-right: 10px;
  font-size: 16px;
  width: 120px;
  text-align: center;
}
</style>
