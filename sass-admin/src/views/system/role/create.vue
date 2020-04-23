<template>
  <Modal
    v-model="curValue"
    title="新增角色"
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
      <FormItem label="角色编号" prop="code">
        <Input v-model="formObj.code" style="width: 250px"></Input>
      </FormItem>
      <FormItem label="角色名称" prop="name">
        <Input v-model="formObj.name" style="width: 250px"></Input>
      </FormItem>
      <FormItem label="角色类型" prop="roleType">
        <Select v-model="formObj.roleType" style="width: 250px">
          <Option
            v-for="item in statusEnum"
            :value="item.roleType"
            :key="item.roleType"
          >
            {{ item.extension }}
          </Option>
        </Select>
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
      <Button type="primary" @click="hdlSubmit('formObj')">确认新增</Button>
      <Button style="margin-left: 8px" @click="hdlCancel">返回</Button>
    </div>
  </Modal>
</template>

<script>
import { addRole } from "@/api/data";
export default {
  name: "role-create",
  props: { value: Boolean },
  data() {
    return {
      curValue: false,
      statusEnum: [
        { roleType: "admin", extension: "管理员" },
        { roleType: "user", extension: "用户" }
      ],
      formObj: {
        name: "",
        code: "",
        roleType: "",
        extension: ""
      },
      rules: {
        code: [
          { required: true, message: "角色编号不能为空", trigger: "blur" }
        ],
        name: [
          { required: true, message: "角色名称不能为空", trigger: "blur" }
        ],
        roleType: [
          { required: true, message: "请选择角色类型", trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    hdlSubmit(name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          addRole(this.formObj).then(() => {
            this.curValue = false;
            this.$emit("on-add-role", this.formObj);
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
