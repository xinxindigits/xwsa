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
      <FormItem label="资源编码" prop="code">
        <Input
          v-model="formObj.code"
          style="width: 250px"
          :disabled="type == 'update'"
        ></Input>
      </FormItem>
      <FormItem label="资源权限" prop="authority">
        <Input v-model="formObj.authority" style="width: 250px"></Input>
      </FormItem>
      <!-- <FormItem label="资源类型" prop="resourceType">
        <Select v-model="formObj.resourceType" style="width: 250px">
          <Option value="menu">菜单</Option>
          <Option value="function">功能</Option>
        </Select>
      </FormItem> -->
      <FormItem label="名称" prop="name">
        <Input v-model="formObj.name" style="width: 250px"></Input>
      </FormItem>
      <FormItem label="组件URI" prop="url">
        <Input v-model="formObj.url" style="width: 250px"></Input>
      </FormItem>
      <FormItem label="描述" prop="extension">
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
      <Button @click="hdlCancel">取消</Button>
      <Button
        style="margin-left: 8px"
        type="primary"
        @click="hdlSubmit('formObj')"
        >确认</Button
      >
    </div>
  </Modal>
</template>

<script>
import { createResource, updateResource } from "@/api/data";
const _config = {
  create: {
    title: "新增",
    submit: createResource
  },
  update: {
    title: "更新",
    submit: updateResource
  }
};
export default {
  name: "resource-modify",
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
      formObj: {
        authority: "",
        name: "",
        code: "",
        parentId: "",
        resourceType: "",
        url: "",
        root: false,
        extension: "",
        id: ""
      },
      rules: {
        code: [
          { required: true, message: "资源编号不能为空", trigger: "blur" }
        ],
        resourceType: [
          { required: true, message: "资源类型不能为空", trigger: "blur" }
        ],
        authority: [
          { required: true, message: "资源权限不能为空", trigger: "blur" }
        ],
        name: [
          { required: true, message: "资源名称不能为空", trigger: "blur" }
        ],
        url: [{ required: true, message: "组件URI不能为空", trigger: "blur" }]
      }
    };
  },
  methods: {
    setData(obj) {
      this.formObj.authority = obj.authority;
      this.formObj.code = obj.code;
      this.formObj.extension = obj.extension;
      this.formObj.name = obj.name;
      this.formObj.parentId = obj.parentId;
      this.formObj.resourceType = obj.resourceType;
      this.formObj.url = obj.url;
      this.formObj.root = false;
      this.formObj.id = obj.id;
    },
    hdlSubmit(name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          _config[this.type].submit(this.formObj).then(() => {
            this.curValue = false;
            this.$emit("resource-modified", this.type);
          });
        }
      });
    },
    hdlCancel() {
      this.curValue = false;
      this.$emit("on-resource-modify-cancel");
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
