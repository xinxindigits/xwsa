<template>
  <Drawer title="会话管理" v-model="showRecord" width="100">
    <div class="wrapper">
      <div class="list-box">
        <Card class="list-box-content">
          <CellGroup>
            <Cell
              :title="n | name"
              v-for="(n, index) in list"
              :key="index"
              @click.native="selectChat(n)"
            />
            <p v-if="list.length == 0" style="text-align:center">-无会话-</p>
          </CellGroup>
        </Card>
      </div>
      <div class="content">
        <div class="msg-wrapper">
          <chat-list :list="msg_list"></chat-list>
        </div>
        <div style="padding-top:10px;">
          <Page
            :disabled="!msg_list.length > 0"
            :total="total"
            :current="page"
            :page-size="pageSize"
            show-elevator
            transfer
            style="float:right"
            @on-change="changePage"
          ></Page>
        </div>
      </div>
    </div>
  </Drawer>
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
  name: "msg-record",
  components: {
    ChatList
  },
  props: {
    value: Boolean,
    userId: String
  },
  filters: {
    name: function(n) {
      return getVal(n, ["userName", "roomName"]);
    }
  },
  data() {
    return {
      showRecord: false,
      total: 0,
      page: 1,
      pageSize: 50,
      list: [],
      msg_list: [],
      current_chat: {}
    };
  },
  methods: {
    selectChat(n) {
      this.resetPageComp();
      this.current_chat = n;
      this.getDetail();
    },
    resetPageComp() {
      this.page = 1;
      this.total = 0;
    },
    getDetail() {
      let n = this.current_chat;
      if (!(n.type === 0 || n.type === 1)) {
        this.$Message.error("未知会话类型！");
        return;
      }
      let id, api, params;
      if (n.type === 0) {
        id = n.userId;
        api = getMsgDetailByUserId;
        params = {
          userId: this.userId,
          userIdTwo: id,
          pageSize: this.pageSize,
          pageIndex: this.page
        };
      } else if (n.type === 1) {
        id = n.roomId;
        api = getMsgDetailByRoomId;
        params = { roomId: id, pageSize: this.pageSize, pageIndex: this.page };
      }
      api(params).then(({ data }) => {
        this.msg_list = data.items.map(n => {
          return {
            name: n.fromUserName,
            content: n.content,
            time: n.msgTime,
            msgType: n.msgType,
            avatar: n.avatar
          };
        });
        this.total = data.total;
      });
    },
    getMsgList(userId) {
      this.list = [];
      this.msg_list = [];
      this.resetPageComp();
      getUserInMsgList({ userId }).then(({ data }) => {
        this.list = data;
      });
    },
    changePage(page) {
      this.page = page;
      this.getDetail();
    }
  },
  watch: {
    value(newValue) {
      this.showRecord = newValue;
    },
    showRecord(newValue) {
      this.$emit("input", newValue);
    }
  }
};
</script>

<style lang="less" scoped>
.wrapper {
  display: flex;
  height: 100%;
  .content {
    flex: 1;
    display: flex;
    flex-direction: column;
  }
}
.list-box {
  display: flex;
  flex-direction: column;
  width: 300px;
  height: 100%;
  margin-right: 10px;
  &-content {
    height: 100%;
    overflow-y: scroll;
  }
}
.msg-wrapper {
  overflow: hidden;
  position: relative;
  height: 100%;
  flex: 1;
  border: 1px solid #dcdee2;
  border-color: #e8eaec;
  padding: 10px 10px 15px;
  background: #fff;
  border-radius: 4px;
  font-size: 14px;
  transition: all 0.2s ease-in-out;
}
</style>
