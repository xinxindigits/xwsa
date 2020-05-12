<template>
  <Form
    ref="loginForm"
    :model="form"
    :rules="rules"
    @keydown.enter.native="handleSubmit"
  >
    <FormItem prop="account">
      <Input v-model="form.account" placeholder="请输入用户名" size="large">
        <span slot="prepend">
          <Icon :size="16" type="ios-person"></Icon>
        </span>
      </Input>
    </FormItem>
    <FormItem prop="password">
      <Input
        type="password"
        v-model="form.password"
        placeholder="请输入密码"
        size="large"
      >
        <span slot="prepend">
          <Icon :size="14" type="md-lock"></Icon>
        </span>
      </Input>
    </FormItem>
    <FormItem prop="verifyCode">
      <Row>
        <Col span="15">
          <Input
            type="text"
            v-model="form.verifyCode"
            placeholder="验证码"
            size="large"
          ></Input>
        </Col>
        <Col span="8" offset="1">
          <img
            class="kaptcha"
            :src="cap"
            alt="点击获取"
            height="32px"
            @click="hdlClick"
          />
        </Col>
      </Row>
    </FormItem>
    <FormItem>
      <Button @click="handleSubmit" type="primary" long>登录</Button>
    </FormItem>
  </Form>
</template>
<script>
export default {
  name: "LoginForm",
  props: {
    accountRules: {
      type: Array,
      default: () => {
        return [{ required: true, message: "账号不能为空", trigger: "blur" }];
      }
    },
    passwordRules: {
      type: Array,
      default: () => {
        return [{ required: true, message: "密码不能为空", trigger: "blur" }];
      }
    },
    kaptchaRules: {
      type: Array,
      default: () => {
        return [{ required: true, message: "验证码不能为空", trigger: "blur" }];
      }
    }
  },
  data() {
    return {
      random: Math.random(),
      form: {
        account: this.$store.state.user.account,
        password: "",
        verifyCode: ""
      }
    };
  },
  computed: {
    cap() {
      return "/kaptcha?" + this.random;
    },
    rules() {
      return {
        account: this.accountRules,
        password: this.passwordRules,
        verifyCode: this.kaptchaRules
      };
    }
  },
  methods: {
    hdlClick() {
      this.random = Math.random();
    },
    handleSubmit() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.$emit("on-success-valid", {
            account: this.form.account,
            password: this.form.password,
            verifyCode: this.form.verifyCode
          });
        }
      });
    }
  }
};
</script>
<style lang="less" scoped>
.kaptcha {
  width: 100%;
  border: 1px solid #e8eaec;
  height: 40px;
  vertical-align: middle;
}
</style>
