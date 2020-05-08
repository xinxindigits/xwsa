<template>
  <div class="wrapper">
    <div class="list-box">
      <Card class="list-box-content">
        <CellGroup>
          <Cell
            :title="n | name"
            v-for="(n, index) in list"
            :key="index"
            @click.native="getDetail(n)"
          />
          <p v-if="list.length == 0" style="text-align:center">-无会话-</p>
        </CellGroup>
      </Card>
    </div>

    <Card style="flex:1" class="msg-wrapper">
      <div style="ov"></div>
      <chat-list :list="msg_list"></chat-list>
    </Card>
  </div>
</template>

<script>
import {
  getUserInMsgList,
  getMsgDetailByUserId,
  getMsgDetailByRoomId
} from "@/api";
import ChatList from "./chat-list";
const getVal = function(n, keyArray) {
  if (!n) return "";
  if (n.type === 0 || n.type === 1) {
    return n[keyArray[n.type]];
  } else {
    return "未知会话";
  }
};
export default {
  name: "user-msg",
  components: {
    ChatList
  },
  props: {
    value: Boolean,
    userId: {
      type: String,
      default: "dio"
    }
  },
  filters: {
    name: function(n) {
      return getVal(n, ["userName", "roomName"]);
    },
    id: function(n) {
      return getVal(n, ["userId", "roomId"]);
    }
  },
  data() {
    return {
      list: [],
      msg_list: []
    };
  },
  methods: {
    getDetail(n) {
      if (n.type === 0) {
        let { userId } = n;
        getMsgDetailByUserId({ userId: this.userId, userIdTwo: userId }).then(
          ({ data }) => {
            this.msg_list = data.items.map(n => {
              return {
                name: n.fromUserName,
                content: n.content,
                time: n.msgTime,
                msgType: n.msgType
              };
            });
          }
        );
      } else if (n.type === 1) {
        let { roomId } = n;
        getMsgDetailByRoomId({ roomId }).then(({ data }) => {
          this.msg_list = data.items.map(n => {
            return {
              name: n.fromUserName,
              content: n.content,
              time: n.msgTime,
              msgType: n.msgType
            };
          });
        });
      } else {
        this.$Message.error("无会话ID！");
      }
    }
  },
  mounted() {},
  watch: {
    userId(newValue) {
      this.list = [];
      this.msg_list = [];
      getUserInMsgList({ userId: newValue }).then(({ data }) => {
        this.list = data;
      });
    }
  }
};
</script>

<style lang="less" scoped>
.wrapper {
  display: flex;
  height: 100%;
}
.list-box {
  display: flex;
  flex-direction: column;
  width: 300px;
  height: 100%;
  &-content {
    height: 100%;
    overflow-y: scroll;
  }
}
.msg-wrapper {
  overflow: hidden;
  height: 100%;
}
</style>
