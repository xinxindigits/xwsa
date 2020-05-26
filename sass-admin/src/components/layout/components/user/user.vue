<template>
  <div class="user-avatar-dropdown">
    <Dropdown @on-click="handleClick">
      <Avatar :src="userAvatar" :size="24" />
      <span class="user-name">{{ account }}</span>
      <Icon :size="18" type="md-arrow-dropdown" color="transparent"></Icon>
      <DropdownMenu slot="list">
        <DropdownItem name="resetPwd">
          修改密码
        </DropdownItem>
        <DropdownItem name="logout">退出登录</DropdownItem>
      </DropdownMenu>
    </Dropdown>
  </div>
</template>

<script>
import "./user.less";
import { mapActions } from "vuex";
export default {
  name: "User",
  props: {
    userAvatar: {
      type: String,
      default: ""
    },
    account: {
      type: String,
      default: ""
    }
  },
  methods: {
    ...mapActions(["handleLogOut"]),
    logout() {
      this.handleLogOut();
    },
    resetPwd() {
      this.$router.push("/resetPwd");
    },
    handleClick(name) {
      switch (name) {
        case "logout":
          this.logout();
          break;
        case "resetPwd":
          this.resetPwd();
          break;
      }
    }
  }
};
</script>
