<template>
  <div class="chat-list">
    <div v-if="currentValue.length == 0">
      <div style="text-align:center">-无会话记录-</div>
    </div>
    <div class="chat-list-items" v-else>
      <div
        class="chat-list-item"
        v-for="(item, index) in currentValue"
        :key="index"
      >
        <div shape="square" class="chat-list-item-avatar">
          <Avatar shape="square" icon="ios-person" :src="item.avatar" />
        </div>
        <div
          class="chat-list-item-content"
          :class="[item.highlight ? 'highlight' : '']"
        >
          <div class="chat-list-item-title">
            <span>{{ item.name }}</span>
            <span class="chat-list-item-time">{{
              item.time | timeFilter
            }}</span>
            <span class="check" v-if="isSearch" @click="hdlClick(item.id)"
              >查看前后消息</span
            >
          </div>
          <div class="chat-list-item-message" v-if="item.msgType == 'text'">
            {{ item.content }}
          </div>
          <div class="chat-list-item-message" v-else>
            [不支持的消息类型：{{ $mapd("msgType", item.msgType) }}]
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: "chat-list",
  props: {
    list: {
      type: Array
    },
    isSearch: { type: Boolean, default: false }
  },
  data() {
    return {
      currentValue: []
    };
  },
  methods: {
    hdlClick(id) {
      this.$emit("get-page-index", id);
    }
  },
  watch: {
    list: {
      immediate: true,
      deep: true,
      handler(newValue) {
        newValue.forEach((n, i) => {
          if (n.avatar) {
            let newImg = new Image();
            newImg.src = n.avatar;
            newImg.onerror = () => {
              newValue[i].avatar = "";
            };
          }
        });
        this.currentValue = newValue;
        document.getElementsByClassName("highlight").length > 0 &&
          document.getElementsByClassName("highlight")[0].scrollIntoView();
      }
    }
  }
};
</script>
<style lang="less" scoped>
.chat-list {
  width: 100%;
  height: 100%;
  overflow: hidden;
  &-content {
    position: relative;
    display: flex;
    flex-direction: column;
    height: 100%;
    overflow-y: scroll;
    &-page {
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 80px;
    }
  }
  .chat-list-items {
    flex: 1;
    height: 100%;
    overflow-y: scroll;
  }
  &-item:hover {
    .check {
      display: inline-block;
      cursor: pointer;
      color: #2d8cf0;
    }
  }
  &-item {
    display: flex;
    padding: 12px 0;
    align-items: center;
    font-size: 0;
    &-avatar {
      margin-right: 12px;
    }
    &-content {
      flex: 1;
      &.highlight {
        color: #2d8cf0;
      }
    }
    &-title {
      font-size: 12px;
      color: rgba(0, 0, 0, 0.45);
    }
    &-time {
      margin-left: 12px;
    }
    &-message {
      font-size: 14px;
      line-height: 22px;
    }
    .check {
      display: none;
      padding-left: 10px;
    }
  }
}
</style>
