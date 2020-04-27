<template>
  <Modal
    v-model="curValue"
    :title="title"
    :loading="true"
    :mask-closable="false"
  >
    <Form
      ref="formObj"
      :model="formObj"
      label-position="right"
      :label-width="150"
      :rules="rules"
    >
      <FormItem label="账号" prop="account">
        <Input
          v-model="formObj.account"
          style="width: 250px"
          :disabled="type == 'update'"
        ></Input>
      </FormItem>
      <FormItem label="姓名" prop="name">
        <Input v-model="formObj.name" style="width: 250px"></Input>
      </FormItem>
      <FormItem label="性别" prop="gender">
        <Select v-model="formObj.gender" style="width: 250px">
          <Option
            v-for="item in statusEnum"
            :value="item.gender"
            :key="item.gender"
          >
            {{ item.extension }}
          </Option>
        </Select>
      </FormItem>
      <FormItem label="密码" prop="password" v-if="type == 'create'">
        <Input
          v-model="formObj.password"
          style="width: 250px"
          type="password"
          password
          maxlength="16"
        ></Input>
      </FormItem>
      <FormItem label="角色描述" prop="extension">
        <Input
          v-model="formObj.extension"
          type="textarea"
          :maxlength="50"
          style="width: 250px"
          :show-word-limit="true"
          :rows="5"
        ></Input>
      </FormItem>
    </Form>
    <div slot="footer">
      <Button type="primary" @click="hdlSubmit('formObj')">确认</Button>
      <Button style="margin-left: 8px" @click="hdlCancel">返回</Button>
    </div>
  </Modal>
</template>

<script>
import { addUser, updateUser } from "@/api/data_user";
const _config = {
  create: {
    title: "新增用户",
    submit: addUser
  },
  update: {
    title: "更新用户",
    submit: updateUser
  }
};
export default {
  name: "role-update",
  props: {
    value: Boolean,
    type: {
      validator: function(value) {
        return ["create", "update"].indexOf(value) !== -1;
      }
    }
  },
  computed: {
    title() {
      return _config[this.type].title;
    }
  },
  data() {
    return {
      curValue: false,
      statusEnum: [
        { gender: 1, extension: "男" },
        { gender: 2, extension: "女" }
      ],
      formObj: {
        account: "",
        name: "",
        password: "",
        gender: "",
        extension: ""
      },
      rules: {
        account: [{ required: true, message: "账号不能为空", trigger: "blur" }],
        name: [{ required: true, message: "姓名不能为空", trigger: "blur" }],
        gender: [
          {
            required: true,
            message: "请选择性别",
            trigger: "blur",
            type: "number"
          }
        ]
      }
    };
  },
  methods: {
    setData(obj) {
      this.formObj.account = obj.account;
      this.formObj.name = obj.name;
      this.formObj.gender = obj.gender;
      this.formObj.extension = obj.extension;
    },
    hdlSubmit(name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          _config[this.type].submit(this.formObj).then(() => {
            this.curValue = false;
            this.$emit("user-modified", this.type);
          });
        }
      });
    },
    hdlCancel() {
      this.$emit("on-cancel");
    }
  },
  watch: {
    value: {
      handler(newValue) {
        this.curValue = newValue;
      },
      immediate: true
    },
    curValue(newValue) {
      this.$emit("input", newValue);
    }
  }
};
</script>
