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
      <FormItem label="所属机构" prop="orgCode">
        <treeselect
          style="width:250px"
          v-model="formObj.orgCode"
          :multiple="false"
          :options="options"
          :defaultExpandLevel="Infinity"
          placeholder="所属机构"
          noResultsText="无匹配数据"
          searchable
        />
      </FormItem>
      <FormItem label="角色" prop="roles">
        <Select multiple v-model="formObj.roles" style="width: 250px">
          <Option v-for="item in roles" :value="item.code" :key="item.code">
            {{ item.name }}
          </Option>
        </Select>
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
      <FormItem
        label="密码"
        prop="password"
        v-if="type == 'create'"
        :rules="checkPass"
      >
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
          style="width: 250px"
          :rows="5"
        ></Input>
      </FormItem>
    </Form>
    <div slot="footer">
      <Button type="primary" @click="hdlResetPwd" :loading="isReseting"
        >重置密码</Button
      >
      <Button type="primary" @click="hdlSubmit('formObj')">确认</Button>
      <Button style="margin-left: 8px" @click="hdlCancel">返回</Button>
    </div>
  </Modal>
</template>

<script>
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { addUser, updateUser, resetUserPwd } from "@/api";
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
  name: "role-modify",
  props: {
    value: Boolean,
    type: {
      validator: function(value) {
        return ["create", "update"].indexOf(value) !== -1;
      }
    }
  },
  components: {
    Treeselect
  },
  computed: {
    title() {
      return _config[this.type].title;
    },
    options() {
      return this.formatData(this.orgCode);
    }
  },
  data() {
    return {
      curValue: false,
      statusEnum: [
        { gender: 1, extension: "男" },
        { gender: 2, extension: "女" }
      ],
      roles: [],
      orgCode: [],
      formObj: {
        account: "",
        name: "",
        password: "",
        gender: "",
        extension: "",
        roles: [],
        orgCode: null
      },
      checkPass: [
        { required: true, message: "密码不能为空", trigger: "blur" },
        {
          pattern: /^[a-zA-Z0-9]{4,16}$/,
          message: "请输入4～16位英文字母或数字的组合",
          trigger: "blur"
        }
      ],
      rules: {
        account: [
          { required: true, message: "账号不能为空", trigger: "blur" },
          {
            pattern: /^[a-zA-Z0-9]{4,16}$/,
            message: "请输入4～16位英文字母或数字的组合",
            trigger: "blur"
          }
        ],
        name: [
          { required: true, message: "姓名不能为空", trigger: "blur" },
          { max: 64, message: "长度超出限制!", trigger: "blur" }
        ],
        orgCode: [
          { required: true, message: "所属机构不能为空", trigger: "blur" }
        ],
        gender: [
          {
            required: true,
            message: "请选择性别",
            trigger: "blur",
            type: "number"
          }
        ],
        extension: [
          { max: 4000, message: "不能超过4000个字符!", trigger: "blur" }
        ]
      },
      isReseting: false
    };
  },
  methods: {
    formatData(data) {
      let arr = [];
      data.forEach(item => {
        let { code: id, orgName: label } = item;
        let obj = {
          id,
          label
        };
        if (item.children && item.children.length > 0) {
          obj.children = this.formatData(item.children);
        }
        arr.push(obj);
      });
      return arr;
    },
    setData(obj) {
      this.formObj.account = obj.account;
      this.formObj.name = obj.name;
      this.formObj.gender = obj.gender;
      this.formObj.extension = obj.extension;
      this.formObj.roles = obj.roles;
      this.formObj.orgCode = obj.orgCode;
    },
    setRoleList(data) {
      this.roles = data;
    },
    setOrgList(data) {
      this.orgCode = data;
    },
    reset() {
      this.formObj = {
        account: "",
        name: "",
        password: "",
        gender: "",
        extension: "",
        roles: [],
        orgCode: null
      };
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
    },
    resetPwd() {
      resetUserPwd({
        account: this.formObj.account
      })
        .then(({ data }) => {
          this.$Modal.success({
            title: "重置成功！",
            content: `${data}`
          });
        })
        .finally(() => {
          this.isReseting = false;
        });
    },
    hdlResetPwd() {
      let self = this;
      this.$Modal.confirm({
        title: "确认操作",
        content: `确定重置${this.formObj.account}的密码?`,
        onOk() {
          self.$emit("user-pwd-reset");
          self.isReseting = true;
          setTimeout(() => {
            self.resetPwd();
          }, 500);
        }
      });
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
