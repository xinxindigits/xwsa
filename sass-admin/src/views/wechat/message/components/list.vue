<template>
  <div class="chat-list">
    <div
      class="chat-list-item"
      v-for="(item, index) in currentValue"
      :key="index"
    >
      <div shape="square" class="chat-list-item-avatar">
        <Avatar shape="square" icon="ios-person" :src="item.avatar" />
      </div>
      <div class="chat-list-item-content">
        <div class="chat-list-item-title">
          <span>{{ item.name }}</span>
          <span class="chat-list-item-time">{{ item.time | timeFilter }}</span>
        </div>
        <div class="chat-list-item-message">
          {{ item.content }}
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
    }
  },
  data() {
    return {
      currentValue: []
    };
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
      }
    }
  }
};
</script>
<style lang="less" scoped>
.chat-list {
  width: 100%;
  height: 100%;
  padding: 10px;
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
    }
    &-title {
      font-size: 14px;
      color: rgba(0, 0, 0, 0.45);
    }
    &-time {
      margin-left: 12px;
    }
    &-message {
      font-size: 16px;
      line-height: 22px;
    }
  }
}
</style>
