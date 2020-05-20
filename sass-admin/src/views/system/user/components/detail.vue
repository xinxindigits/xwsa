<template>
  <Drawer title="用户详情" width="80" v-model="showDetail" scrollable transfer>
    <List :split="false">
      <ListItem>账号：{{ items.account }}</ListItem>
      <ListItem>ID：{{ items.id }}</ListItem>
      <ListItem>状态：{{ $mapd("userStatus", items.status) }}</ListItem>
      <ListItem>姓名：{{ items.name }}</ListItem>
      <ListItem>角色：{{ items.roleName }}</ListItem>
      <ListItem>所属机构：{{ items | orgName }}</ListItem>
      <ListItem>创建时间：{{ items.gmtCreated | timeFilter }}</ListItem>
      <ListItem>备注：{{ items.extension }}</ListItem>
    </List>
    <Divider dashed></Divider>
  </Drawer>
</template>

<script>
export default {
  name: "msg-detail",
  props: {
    value: Boolean,
    items: {
      type: Object,
      default: () => {
        return {
          account: "",
          extension: "",
          gmtCreated: "",
          id: "",
          name: "",
          roleName: "",
          orgs: []
        };
      }
    }
  },
  filters: {
    orgName: function(data) {
      return data.orgs && data.orgs.length > 0 ? data.orgs[0].name : "";
    }
  },
  data() {
    return {
      showDetail: false
    };
  },
  watch: {
    showDetail(newValue) {
      this.$emit("input", newValue);
    },
    value(newValue) {
      this.showDetail = newValue;
    }
  }
};
</script>
