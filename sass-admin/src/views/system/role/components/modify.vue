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
      <FormItem label="角色编号" prop="code">
        <Input
          v-model="formObj.code"
          style="width: 250px"
          :disabled="this.type == 'update'"
        ></Input>
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
      <Button type="primary" @click="hdlSubmit('formObj')">确认</Button>
      <Button style="margin-left: 8px" @click="hdlCancel">返回</Button>
    </div>
  </Modal>
</template>

<script>
import { addRole, updateRole } from "@/api/data";
const _config = {
  create: {
    title: "新增角色",
    submit: addRole
  },
  update: {
    title: "更新角色",
    submit: updateRole
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
      code_editable: false,
      curValue: false,
      statusEnum: [
        { roleType: "admin", extension: "管理员" },
        { roleType: "user", extension: "用户" }
      ],
      formObj: {
        name: "",
        code: "",
        roleType: "",
        extension: "",
        roles: []
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
    setData(obj) {
      this.formObj.code = obj.code;
      this.formObj.extension = obj.extension;
      this.formObj.roleType = obj.roleType;
      this.formObj.name = obj.name;
      this.code_editable = true;
    },
    hdlSubmit(name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          _config[this.type].submit(this.formObj).then(() => {
            this.curValue = false;
            this.$emit("role-modified", this.type);
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
