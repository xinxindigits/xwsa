<template>
  <div>
    <List>
      <ListItem>
        <ListItemMeta :title="detail.fromUserName" :description="description">
        </ListItemMeta>
      </ListItem>
    </List>
    <CellGroup>
      <Divider dashed></Divider>
      <List :split="false">
        <ListItem>内容:</ListItem>
        <ListItem>{{ detail | content }}</ListItem>
      </List>
      <Divider dashed></Divider>
      <List :split="false">
        <ListItem>消息编号：{{ detail.msgId }}</ListItem>
        <ListItem>发送时间：{{ detail.msgTime | timeFilter }}</ListItem>
        <ListItem>消息类型：{{ $mapd("msgType", detail.msgType) }}</ListItem>
        <ListItem>接收人：{{ detail.toUserId }}</ListItem>
        <ListItem>房间号：{{ detail.roomId }}</ListItem>
      </List>
      <Divider dashed></Divider>
    </CellGroup>
  </div>
</template>

<script>
export default {
  name: "msg-detail",
  props: {
    items: Object
  },
  filters: {
    content: function(data) {
      console.log(data);
      return data.msgType == "text" ? data.content : `不支持展示的消息类型`;
    }
  },
  computed: {
    description() {
      return `账号：${this.detail.fromUserId}`;
    }
  },
  data() {
    return {
      detail: {
        fromUserName: "",
        fromUserId: "",
        content: "",
        msgId: "",
        msgTime: "",
        toUserId: "[]",
        roomId: "",
        userId: ""
      }
    };
  },
  watch: {
    items: {
      immediate: true,
      deep: true,
      handler(newValue) {
        this.detail = newValue;
      }
    }
  }
};
</script>
