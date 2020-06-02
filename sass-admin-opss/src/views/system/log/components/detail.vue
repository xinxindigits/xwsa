<template>
  <div>
    <List :split="false">
      <ListItem>账号：{{ items.account }}</ListItem>
      <ListItem>租户ID：{{ items.tenantId }}</ListItem>
      <ListItem>操作：{{ items.operation }}</ListItem>
      <ListItem>时间：{{ items.gmtCreated | timeFilter }}</ListItem>
      <ListItem>操作设备：{{ items.ua }}</ListItem>
      <ListItem>请求方法：{{ items.method }}</ListItem>
      <ListItem>请求参数：{{ items.params }}</ListItem>
    </List>
    <Divider dashed></Divider>
  </div>
</template>

<script>
export default {
  name: "msg-detail",
  props: {
    items: Object
  }
};
</script>
