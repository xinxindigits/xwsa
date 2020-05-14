<template>
  <div class="login">
    <div class="login-con">
      <Card icon="log-in" title="欢迎登录" :bordered="false">
        <div class="form-con">
          <login-form ref="login" @on-success-valid="handleSubmit"></login-form>
        </div>
      </Card>
    </div>
  </div>
</template>

<script>
import LoginForm from "@/components/login-form";
import { mapActions } from "vuex";
export default {
  components: {
    LoginForm
  },
  methods: {
    ...mapActions(["handleLogin"]),
    handleSubmit({ account, password, verifyCode }) {
      this.handleLogin({ account, password, verifyCode })
        .then(() => {
          this.$router.push({
            name: this.$config.homeName
          });
        })
        .catch(() => {
          this.$refs.login.updateKaptcha();
        });
    }
  }
};
</script>
<style lang="less" scoped>
@import "./login.less";
</style>
