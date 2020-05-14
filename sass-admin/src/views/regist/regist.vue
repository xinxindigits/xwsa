<template>
  <div class="regist">
    <div class="regist-con">
      <Card icon="log-in" title="注册" :bordered="false">
        <div class="form-con">
          <Form
            ref="form"
            :model="formObj"
            label-colon
            label-position="right"
            :rules="rules"
          >
            <FormItem prop="account">
              <Input
                v-model="formObj.account"
                placeholder="账号"
                maxlength="20"
              ></Input>
            </FormItem>
            <FormItem prop="name">
              <Input
                v-model="formObj.name"
                placeholder="姓名"
                maxlength="20"
              ></Input>
            </FormItem>
            <FormItem prop="gender">
              <Select v-model="formObj.gender" placeholder="性别">
                <template v-for="(n, index) in gender">
                  <Option
                    v-if="index > 0"
                    :value="parseInt(index)"
                    :key="index"
                    >{{ n }}</Option
                  >
                </template>
              </Select>
            </FormItem>
            <FormItem prop="password">
              <Input
                v-model="formObj.password"
                placeholder="请输入密码"
                type="password"
                maxlength="32"
                password
              ></Input>
            </FormItem>
            <FormItem prop="passwdCheck">
              <Input
                v-model="formObj.passwdCheck"
                placeholder="再次输入密码"
                maxlength="32"
                type="password"
                password
              ></Input>
            </FormItem>
            <FormItem prop="extension">
              <Input
                :rows="3"
                :autosize="false"
                type="textarea"
                v-model="formObj.extension"
                placeholder="备注"
              ></Input>
            </FormItem>
            <FormItem>
              <Button long type="primary" @click="hdlSubmit">注册</Button>
              <Button long style="margin-top:10px" @click="hdlCancel"
                >取消</Button
              >
            </FormItem>
          </Form>
        </div>
      </Card>
    </div>
  </div>
</template>

<script>
import { gender } from "@/libs/dic";
import { regist } from "@/api";
export default {
  name: "regist",
  data() {
    const validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("密码不能为空！"));
      } else {
        if (this.formObj.passwdCheck !== "") {
          this.$refs.form.validateField("passwdCheck");
        }
        callback();
      }
    };
    const validatePassCheck = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("密码不能为空！"));
      } else if (value !== this.formObj.password) {
        callback(new Error("两次密码不一致！"));
      } else {
        callback();
      }
    };
    return {
      gender,
      formObj: {
        account: "",
        name: "",
        gender: null,
        password: "",
        passwdCheck: "",
        extension: ""
      },
      rules: {
        account: [{ required: true, message: "账号不能为空", trigger: "blur" }],
        name: [{ required: true, message: "姓名不能为空", trigger: "blur" }],
        gender: [
          {
            required: true,
            message: "请选择性别",
            type: "number",
            trigger: "blur"
          }
        ],
        password: [{ validator: validatePass, trigger: "blur" }],
        passwdCheck: [{ validator: validatePassCheck, trigger: "blur" }]
      }
    };
  },
  methods: {
    hdlSubmit() {
      debugger;
      this.$refs.form.validate(valid => {
        if (valid) {
          regist(this.formObj);
        }
      });
    },
    hdlCancel() {
      window.history.length > 1 ? this.$router.go(-1) : this.$router.push("/");
    }
  }
};
</script>

<style lang="less" scoped>
.regist {
  width: 100%;
  height: 100%;
  background-image: url("../../assets/images/login-bg.png");
  background-size: cover;
  background-repeat: no-repeat;
  background-position: 50%;
  overflow: hidden;
  position: relative;
  &-con {
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    width: 300px;
    &-header {
      font-size: 16px;
      font-weight: 300;
      text-align: center;
      padding: 30px 0;
    }
    .form-con {
      padding: 10px 0 0;
    }
  }
}
</style>
