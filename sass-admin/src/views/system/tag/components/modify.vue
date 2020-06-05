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
      <FormItem label="ID" prop="id" v-if="this.type == 'update'">
        <Input v-model="form.id" style="width: 250px" disabled></Input>
      </FormItem>
      <FormItem label="编号" prop="code">
        <Input
          v-model="form.code"
          style="width: 250px"
          :disabled="this.type == 'update'"
        ></Input>
      </FormItem>
      <FormItem label="名称" prop="name">
        <Input v-model="form.name" style="width: 250px"></Input>
      </FormItem>
      <FormItem label="类型" prop="tagType">
        <Select v-model="form.tagType" style="width: 250px">
          <Option v-for="item in statusEnum" :value="item[0]" :key="item[0]">
            {{ item[1] }}
          </Option>
        </Select>
      </FormItem>
      <FormItem label="描述" prop="description">
        <Input
          v-model="form.description"
          type="textarea"
          :maxlength="500"
          style="width: 250px"
          :show-word-limit="true"
          :rows="5"
        ></Input>
      </FormItem>
    </Form>
    <div slot="footer">
      <Button type="primary" @click="hdlSubmit('form')">确认</Button>
      <Button style="margin-left: 8px" @click="hdlCancel">返回</Button>
    </div>
  </Modal>
</template>

<script>
import { createTag, updateTag } from "@/api";
import { tagType } from "@/libs/dic";
const _config = {
  create: {
    title: "新增标签",
    submit: createTag
  },
  update: {
    title: "更新标签",
    submit: updateTag
  }
};
export default {
  name: "tag-modify",
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
      statusEnum: Object.entries(tagType),
      form: {
        id: "",
        name: "",
        code: "",
        tagType: "",
        description: ""
      },
      rules: {
        code: [
          { required: true, message: "编号不能为空", trigger: "blur" },
          {
            pattern: /^[a-zA-Z0-9]{4,16}$/,
            message: "请输入4～16位英文字母或数字的组合",
            trigger: "blur"
          }
        ],
        name: [
          { required: true, message: "名称不能为空", trigger: "blur" },
          { max: 128, message: "长度超出限制!", trigger: "blur" }
        ],
        tagType: [{ required: true, message: "请选择类型", trigger: "blur" }],
        description: [
          { max: 512, message: "不能超过512个字符!", trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    setData(obj) {
      this.form.code = obj.code;
      this.form.description = obj.description;
      this.form.tagType = obj.tagType;
      this.form.name = obj.name;
      this.form.id = obj.id;
    },
    hdlSubmit(name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          _config[this.type].submit(this.form).then(() => {
            this.curValue = false;
            this.$emit("tag-modified", this.type);
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
