<template>
  <div class="profile">
    <Card>
      <Row>
        <Col :span="10"
          ><Form
            ref="form"
            :model="form"
            label-position="right"
            :label-width="150"
            :rules="rules"
            label-colon
          >
            <FormItem label="原密码" prop="oldPassword">
              <Input
                v-model="form.oldPassword"
                placeholder="请输入密码"
                type="password"
                maxlength="32"
                password
              ></Input>
            </FormItem>

            <FormItem label="新密码" prop="newPassword">
              <Input
                v-model="form.newPassword"
                placeholder="输入新密码"
                maxlength="32"
                type="password"
                password
              ></Input>
            </FormItem>
            <FormItem label="再次新密码" prop="newPasswordCheck">
              <Input
                v-model="form.newPasswordCheck"
                placeholder="再次输入密码"
                maxlength="32"
                type="password"
                password
              ></Input>
            </FormItem>
            <FormItem>
              <Button @click="hdlCancel">返回</Button>
              <Button style="margin-left:8px" type="primary" @click="hdlSubmit"
                >确认</Button
              >
            </FormItem>
          </Form>
        </Col>
      </Row>
    </Card>
  </div>
</template>

<script>
import { changeUserPwd } from "@/api";
import { mapActions } from "vuex";

export default {
  name: "Profile",
  data() {
    const validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("密码不能为空！"));
      } else {
        if (this.form.newPasswordCheck !== "") {
          this.$refs.form.validateField("newPasswordCheck");
        }
        callback();
      }
    };
    const validatePassCheck = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("密码不能为空！"));
      } else if (value !== this.form.newPassword) {
        callback(new Error("两次密码不一致！"));
      } else {
        callback();
      }
    };
    return {
      checkPass: [
        { required: true, message: "密码不能为空", trigger: "blur" },
        {
          pattern: /^[a-zA-Z0-9]{4,16}$/,
          message: "请输入4～16位英文字母或数字的组合",
          trigger: "blur"
        }
      ],
      form: {
        oldPassword: "",
        newPassword: "",
        newPasswordCheck: ""
      },
      rules: {
        oldPassword: [
          { required: true, message: "原密码不能为空", trigger: "blur" }
        ],
        newPassword: [
          { required: true, message: "新密码不能为空", trigger: "blur" },
          { validator: validatePass, trigger: "blur" }
        ],
        newPasswordCheck: [
          { required: true, message: "新密码不能为空", trigger: "blur" },
          { validator: validatePassCheck, trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    ...mapActions(["handleLogOut"]),
    hdlSubmit() {
      this.$refs.form.validate(valid => {
        if (valid) {
          changeUserPwd({
            account: this.$store.state.user.account,
            oldPassword: this.$md5(this.form.oldPassword),
            newPassword: this.$md5(this.form.newPasswordCheck)
          }).then(() => {
            this.$Message.success("密码重置成功，请重新登录");
            this.handleLogOut().then(() => {
              this.$router.push({
                name: "login"
              });
            });
          });
        }
      });
    },
    hdlCancel() {
      this.$router.push({
        name: "home"
      });
    }
  }
};
</script>
