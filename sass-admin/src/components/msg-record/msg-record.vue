<template>
  <Drawer title="会话管理" v-model="showRecord" width="80">
    <div class="wrapper">
      <div class="list-box">
        <Query ref="query" @on-query="hdlQuery" @on-reset="hdlReset" />
        <Card class="list-box-content">
          <CellGroup ref="cellgroup">
            <Cell
              v-for="(n, index) in list"
              :key="index"
              :selected="!!n.selected"
              :ref="`list_${index}`"
              @click.native="selectChat(n)"
            >
              <div class="list-box-item-title">{{ n | name }}</div>
            </Cell>
            <p v-if="list.length == 0" style="text-align:center">-无会话-</p>
          </CellGroup>
        </Card>
      </div>
      <div class="content">
        <div class="msg-wrapper">
          <chat-list
            :list="msg_list"
            :is-search="isSearch"
            @get-page-index="getPage"
          ></chat-list>
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
  getMsgDetailByRoomId,
  getPageIndex
} from "@/api";
import Query from "./query";
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
    Query,
    ChatList
  },
  props: {
    value: Boolean,
    userId: String
  },
  computed: {
    isSearch() {
      return !(
        this.form.keyWord == "" &&
        this.form.startTime == "" &&
        this.form.endTime == ""
      );
    }
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
      current_chat: {},
      form: {
        keyWord: "",
        startTime: "",
        endTime: ""
      }
    };
  },
  methods: {
    init(userId, opt = {}) {
      this.$refs.query.reset();
      this.current_chat = {};
      this.form = {
        keyWord: "",
        startTime: "",
        endTime: ""
      };
      this.getMsgList(userId).then(() => {
        if (Object.keys(opt).length > 0) {
          let { roomId, type, userIdTwo } = opt;
          this.selectChat(
            { roomId, type, userId: userIdTwo },
            { pageIndex: opt.pageIndex, row: opt.row }
          );
        }
      });
    },
    selectChat(n, opt = { pageIndex: 1, row: 0 }) {
      this.resetPageComp();
      this.current_chat = n;
      this.getDetail(opt.pageIndex, opt.row);
    },
    resetPageComp() {
      this.page = 1;
      this.total = 0;
    },
    getDetail(page = 1, row) {
      this.page = page;
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
          pageIndex: this.page,
          ...this.form
        };
      } else if (n.type === 1) {
        id = n.roomId;
        api = getMsgDetailByRoomId;
        params = {
          roomId: id,
          pageSize: this.pageSize,
          pageIndex: this.page,
          ...this.form
        };
      }
      api(params).then(({ data }) => {
        this.msg_list = data.items.map((n, i) => {
          return {
            name: n.fromUserName,
            content: n.content,
            time: n.msgTime,
            msgType: n.msgType,
            avatar: n.avatar,
            id: n.id,
            highlight: row && i == row - 1
          };
        });
        this.total = data.total;
      });
    },
    getMsgList(userId, query = {}) {
      return new Promise(resolve => {
        this.list = [];
        this.msg_list = [];
        this.resetPageComp();
        getUserInMsgList({ userId, ...query }).then(({ data }) => {
          this.list = data;
          this.$emit("has-get-chat-list");
          resolve();
        });
      });
    },
    changePage(page) {
      this.page = page;
      this.getDetail();
    },
    hdlQuery(data) {
      this.form = data;
      this.getMsgList(this.userId, this.form);
    },
    hdlReset() {
      this.form = {
        keyWord: "",
        startTime: "",
        endTime: ""
      };
      this.getMsgList(this.userId);
    },
    getPage(id) {
      let n = this.current_chat;
      getPageIndex({
        id,
        pageSize: this.pageSize,
        roomId: n.roomId,
        userId: this.userId,
        userIdTwo: n.userId
      }).then(({ data }) => {
        this.form = {
          keyWord: "",
          startTime: "",
          endTime: ""
        };
        this.changePage(data.pageIndex);
      });
    },
    getChatIdByType(n) {
      if (n.type == 1) {
        return n.roomId;
      } else {
        return n.userId;
      }
    }
  },
  watch: {
    value(newValue) {
      this.showRecord = newValue;
    },
    showRecord(newValue) {
      this.$emit("input", newValue);
    },
    current_chat: {
      deep: true,
      handler(newValue) {
        let current_chat_id = this.getChatIdByType(newValue);
        this.list = this.list.map(n => {
          let list_item_id = this.getChatIdByType(n);
          return { ...n, selected: current_chat_id == list_item_id };
        });
      }
    },
    list(newValue) {
      let target = 0;
      newValue.forEach((n, i) => {
        n.selected && (target = i);
      });
      this.$refs[`list_${target}`] &&
        this.$refs[`list_${target}`].length > 0 &&
        this.$refs[`list_${target}`][0].$el.scrollIntoView();
    }
  }
};
</script>

<style lang="less" scoped>
.wrapper {
  display: flex;
  height: 100%;
  .search {
    margin-bottom: 10px;
  }
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
  &-item-title {
    display: inline-block;
    overflow: hidden;
    width: 15em;
    text-overflow: ellipsis;
    vertical-align: middle;
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
