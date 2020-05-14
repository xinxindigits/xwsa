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
        <ListItem>群号：{{ detail.roomId }}</ListItem>
      </List>
      <Divider dashed></Divider>
      <Button type="primary" @click="showRecord">查看聊天记录</Button>
      <msg-record
        v-model="isShowRecord"
        :user-id="cur_userId"
        ref="record"
      ></msg-record>
    </CellGroup>
  </div>
</template>

<script>
import MsgRecord from "@/components/msg-record/msg-record";
import { getPageIndex } from "@/api";
export default {
  name: "msg-detail",
  components: {
    MsgRecord
  },
  props: {
    items: Object
  },
  filters: {
    content: function(data) {
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
      isShowRecord: false,
      cur_userId: "",
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
  methods: {
    showRecord() {
      let { fromUserId, toUserId, roomId, id } = this.detail;
      if (roomId) {
        toUserId = "";
      } else {
        toUserId = toUserId.substring(1, toUserId.length - 1);
      }
      this.hdlShowRecord({ id, fromUserId, toUserId, roomId });
    },
    hdlShowRecord(n) {
      console.log(n);
      let params = {
        id: n.id,
        pageSize: 50,
        roomId: n.roomId,
        userId: n.fromUserId,
        userIdTwo: n.toUserId,
        type: n.roomId ? 1 : 0
      };
      getPageIndex(params).then(({ data }) => {
        console.log(data);
        this.cur_userId = n.fromUserId;
        this.$refs.record.init(this.cur_userId, {
          ...params,
          pageIndex: data.pageIndex
        });
        this.isShowRecord = true;
      });
    }
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
