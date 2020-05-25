<template>
  <Modal
    v-model="curValue"
    :title="title"
    :loading="true"
    :mask-closable="false"
  >
    <Form
      ref="form"
      :model="form"
      label-position="right"
      :label-width="150"
      :rules="rules"
    >
      <FormItem label="租户编号" prop="code" v-if="type == 'update'">
        <Input
          v-model="form.code"
          style="width: 280px"
          :disabled="type == 'update'"
        ></Input>
      </FormItem>
      <FormItem label="租户名称" prop="name">
        <Input v-model="form.name" style="width: 280px"></Input>
      </FormItem>
      <FormItem label="企业微信corpId" prop="corpId">
        <Input v-model="form.corpId" style="width: 280px"></Input>
      </FormItem>
      <FormItem label="私钥" prop="privateKey">
        <Input v-model="form.privateKey" style="width: 280px"></Input>
      </FormItem>
      <FormItem label="通讯录应用secret" prop="addressListSecret">
        <Input v-model="form.addressListSecret" style="width: 280px"></Input>
      </FormItem>
      <FormItem label="联系人应用secret" prop="customerContactSecret">
        <Input
          v-model="form.customerContactSecret"
          style="width: 280px"
        ></Input>
      </FormItem>
      <FormItem label="会话应用secret" prop="chatRecordSecret">
        <Input v-model="form.chatRecordSecret" style="width: 280px"></Input>
      </FormItem>
      <FormItem label="状态" prop="state">
        <RadioGroup v-model="form.state">
          <Radio label="Y">启用</Radio>
          <Radio label="N">禁用</Radio>
        </RadioGroup>
      </FormItem>
      <FormItem label="租户描述" prop="remark">
        <Input
          v-model="form.remark"
          type="textarea"
          :maxlength="50"
          style="width: 280px"
          :show-word-limit="true"
          :rows="5"
        ></Input>
      </FormItem>
    </Form>
    <div slot="footer">
      <Button @click="hdlCancel">取消</Button>
      <Button style="margin-left: 8px" type="primary" @click="hdlSubmit('form')"
        >确认</Button
      >
    </div>
  </Modal>
</template>

<script>
import { addTenant, updateTenant } from "@/api";
const _config = {
  create: {
    title: "新增租户",
    submit: addTenant
  },
  update: {
    title: "更新租户",
    submit: updateTenant
  }
};
export default {
  name: "tenant-modify",
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
      form: {
        name: "",
        code: "",
        privateKey: "",
        corpId: "",
        addressListSecret: "",
        customerContactSecret: "",
        chatRecordSecret: "",
        state: "Y",
        remark: ""
      },
      rules: {
        code: [
          { required: true, message: "租户编号不能为空", trigger: "blur" }
        ],
        name: [
          { required: true, message: "租户名称不能为空", trigger: "blur" }
        ],
        corpId: [
          { required: true, message: "企业微信corpId不能为空", trigger: "blur" }
        ],
        state: [{ required: true, message: "状态不能为空", trigger: "change" }]
      }
    };
  },
  methods: {
    setData({ obj, remark, state }) {
      this.form.code = obj.tenantId;
      this.form.name = obj.tenantName;
      this.form.remark = remark;
      this.form.state = state;
      this.form.privateKey = obj.privateKey;
      this.form.corpId = obj.corpId;
      this.form.addressListSecret = obj.addressListSecret;
      this.form.customerContactSecret = obj.customerContactSecret;
      this.form.chatRecordSecret = obj.chatRecordSecret;
    },
    hdlSubmit(name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          _config[this.type].submit(this.form).then(() => {
            this.curValue = false;
            this.$emit("tenant-modified", this.type);
          });
        }
      });
    },
    hdlCancel() {
      this.curValue = false;
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

<style scoped></style>
