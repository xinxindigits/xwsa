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
          <div class="captcha-wrapper">
            <img
              class="kaptcha"
              :src="cap"
              alt="点击获取"
              @click="updateKaptcha"
            />
          </div>
        </Col>
      </Row>
    </FormItem>
    <FormItem>
      <Button @click="handleSubmit" type="primary" long>登录</Button>
    </FormItem>
  </Form>
</template>
<script>
import config from "@/config";
const base =
  process.env.NODE_ENV === "development"
    ? config.baseUrl.dev
    : config.baseUrl.pro;
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
      return `${base}/kaptcha?` + this.random;
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
    updateKaptcha() {
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
img[src=""],
img:not([src]) {
  opacity: 0;
}
img:after {
  content: "\f1c5""" attr(alt);
  font-size: 12px;
  color: rgb(100, 100, 100);
  display: block;
  position: absolute;
  z-index: 2;
  top: 0;
  left: 0;
  width: calc(100% - 2px);
  height: 100%;
  background-color: #fff;
}
.captcha-wrapper {
  position: relative;
  float: left;
  width: 100%;
  height: 40px;
  border: 1px solid rgba(0, 0, 0, 0.1);
}

.kaptcha {
  vertical-align: top;
  width: 100%;
  height: 100%;
  border: none;
  background-color: white;
  font-size: 0;
}
</style>
