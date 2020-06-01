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
      <FormItem label="资源编码" prop="code">
        <Input
          v-model="form.code"
          style="width: 250px"
          :disabled="type == 'update'"
        ></Input>
      </FormItem>
      <FormItem label="上级ID" prop="parentId">
        <Input
          v-model="form.parentId"
          style="width: 250px"
          :disabled="true"
        ></Input>
      </FormItem>
      <FormItem label="资源类型" prop="resourceType">
        <Select
          v-model="form.resourceType"
          :disabled="type == 'update'"
          style="width: 250px"
        >
          <Option value="menu">菜单</Option>
          <Option value="function">功能</Option>
        </Select>
      </FormItem>
      <FormItem label="资源权限值" prop="authority">
        <Select
          v-if="type == 'update'"
          v-model="form.authority"
          filterable
          style="width: 250px"
        >
          <Option
            v-for="(n, index) in allGrantsList"
            :value="n.code"
            :key="`${n.code}_${index}`"
            >{{ n.name }}({{ n.code }})</Option
          >
        </Select>
        <Input v-model="form.authority" style="width: 250px" v-else></Input>
      </FormItem>
      <FormItem label="名称" prop="name">
        <Input v-model="form.name" style="width: 250px"></Input>
      </FormItem>
      <FormItem label="组件URI" prop="url">
        <Input v-model="form.url" style="width: 250px"></Input>
      </FormItem>
      <FormItem label="描述" prop="extension">
        <Input
          v-model="form.extension"
          type="textarea"
          style="width: 250px"
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
import { mngCreateAuth, getGrantList, mngUpdateAuth } from "@/api";
const _config = {
  create: {
    title: "新增",
    submit: mngCreateAuth
  },
  update: {
    title: "更新",
    submit: mngUpdateAuth
  }
};
export default {
  name: "mng-resource-modify",
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
      allGrantsList: [],
      form: {
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
          { required: true, message: "资源编号不能为空", trigger: "blur" },
          {
            pattern: /^[a-zA-Z0-9]{4,16}$/,
            message: "请输入4～16位英文字母或数字的组合",
            trigger: "blur"
          }
        ],
        resourceType: [
          { required: true, message: "资源类型不能为空", trigger: "blur" }
        ],
        authority: [
          { required: true, message: "资源权限不能为空", trigger: "blur" },
          { max: 128, message: "长度超出限制!", trigger: "blur" }
        ],
        name: [
          { required: true, message: "资源名称不能为空", trigger: "blur" },
          { max: 128, message: "长度超出限制!", trigger: "blur" }
        ],
        url: [
          { required: true, message: "组件URI不能为空", trigger: "blur" },
          { max: 128, message: "长度超出限制!", trigger: "blur" }
        ],
        extension: [
          { max: 4000, message: "不能超过4000个字符!", trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    setData(obj) {
      this.form.authority = obj.authority;
      this.form.code = obj.code;
      this.form.extension = obj.extension;
      this.form.name = obj.name;
      this.form.parentId = obj.parentId;
      this.form.resourceType = obj.resourceType;
      this.form.url = obj.url;
      this.form.root = false;
      this.form.id = obj.id;
    },
    hdlSubmit(name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          _config[this.type].submit(this.form).then(() => {
            this.curValue = false;
            this.$emit("auths-modified", this.type);
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
        if (newValue) {
          getGrantList().then(({ data }) => {
            this.allGrantsList = data;
          });
        }
      },
      immediate: true
    },
    curValue(newValue) {
      this.$emit("input", newValue);
    }
  }
};
</script>
