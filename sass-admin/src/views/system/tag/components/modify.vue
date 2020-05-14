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
      <FormItem label="ID" prop="id" v-if="this.type == 'update'">
        <Input v-model="formObj.id" style="width: 250px" disabled></Input>
      </FormItem>
      <FormItem label="编号" prop="code">
        <Input
          v-model="formObj.code"
          style="width: 250px"
          :disabled="this.type == 'update'"
        ></Input>
      </FormItem>
      <FormItem label="名称" prop="name">
        <Input v-model="formObj.name" style="width: 250px"></Input>
      </FormItem>
      <FormItem label="类型" prop="tagType">
        <Select v-model="formObj.tagType" style="width: 250px">
          <Option v-for="item in statusEnum" :value="item[0]" :key="item[0]">
            {{ item[1] }}
          </Option>
        </Select>
      </FormItem>
      <FormItem label="描述" prop="description">
        <Input
          v-model="formObj.description"
          type="textarea"
          :maxlength="500"
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
      formObj: {
        id: "",
        name: "",
        code: "",
        tagType: "",
        description: ""
      },
      rules: {
        code: [
          { required: true, message: "编号不能为空", trigger: "blur" },
          { max: 32, message: "不能超过32个字符!", trigger: "blur" }
        ],
        name: [
          { required: true, message: "名称不能为空", trigger: "blur" },
          { max: 128, message: "不能超过128个字符!", trigger: "blur" }
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
      this.formObj.code = obj.code;
      this.formObj.description = obj.description;
      this.formObj.tagType = obj.tagType;
      this.formObj.name = obj.name;
      this.formObj.id = obj.id;
    },
    hdlSubmit(name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          _config[this.type].submit(this.formObj).then(() => {
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
