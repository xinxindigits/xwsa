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
      <FormItem label="机构编号" prop="code">
        <Input v-model="form.code" style="width: 280px"></Input>
      </FormItem>
      <FormItem label="机构名称" prop="name">
        <Input v-model="form.name" style="width: 280px"></Input>
      </FormItem>
      <FormItem label="上级机构" prop="parentName">
        <Input
          v-model="form.parentName"
          style="width: 280px"
          :disabled="no_edit_parentName"
        ></Input>
      </FormItem>
      <FormItem label="机构类型" prop="orgType">
        <Select v-model="form.orgType" style="width:280px">
          <Option
            v-for="item in orgTypeList"
            :value="item.orgType"
            :key="item.orgType"
            >{{ item.desc }}</Option
          >
        </Select>
      </FormItem>
      <FormItem label="机构备注" prop="remark">
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
import { addOrganization, updateOrganization } from "@/api";
const _config = {
  create: {
    title: "新增机构",
    success_evt: "on-add-organization",
    submit: addOrganization
  },
  update: {
    title: "更新机构",
    success_evt: "on-update-organization",
    submit: updateOrganization
  }
};
export default {
  name: "organization-update",
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
      no_edit_parentName: false,
      form: {
        name: "",
        parentId: "",
        parentName: "",
        code: "",
        state: "Y",
        remark: "",
        orgType: ""
      },
      orgTypeList: [
        {
          orgType: "COMP",
          desc: "公司"
        },
        {
          orgType: "DEPART",
          desc: "部门"
        },
        {
          orgType: "GROUP",
          desc: "小组"
        }
      ],
      rules: {
        code: [
          { required: true, message: "机构编号不能为空", trigger: "blur" }
        ],
        name: [
          { required: true, message: "机构名称不能为空", trigger: "blur" }
        ],
        orgType: [
          { required: true, message: "机构类型不能为空", trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    setData(obj) {
      this.form.orgType = obj.orgType;
      this.form.code = obj.code;
      this.form.name = obj.name;
      this.form.parentName = obj.parentName;
      this.form.parentId = obj.parentId;
      this.no_edit_parentName = obj.no_edit_parentName;
      this.form.remark = obj.remark;
    },
    hdlSubmit(name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          _config[this.type].submit(this.form).then(() => {
            this.curValue = false;
            this.$emit(_config[this.type].success_evt, this.form);
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
