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
      <FormItem label="机构编号" prop="code">
        <Input
          v-model="formObj.code"
          style="width: 280px"
          :disabled="type == 'update'"
        ></Input>
      </FormItem>
      <FormItem label="机构名称" prop="name">
        <Input v-model="formObj.name" style="width: 280px"></Input>
      </FormItem>
      <FormItem label="上级机构" prop="parentName">
        <Input v-model="formObj.parentName" style="width: 280px"></Input>
      </FormItem>
      <FormItem label="机构类型" prop="orgType">
        <Select v-model="formObj.orgType" style="width:280px">
          <Option
            v-for="item in orgTypeList"
            :value="item.value"
            :key="item.value"
            >{{ item.label }}</Option
          >
        </Select>
      </FormItem>
      <FormItem label="机构备注" prop="remark">
        <Input
          v-model="formObj.remark"
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
import { addOrganization, updateOrganization } from "@/api/data_organization";
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
    const validateCode = (rule, value, callback) => {
      if (value === "" && !this.addFlowFlag) {
        callback(new Error("租户编号不能为空"));
      } else {
        callback();
      }
    };

    return {
      curValue: false,
      formObj: {
        name: "",
        parentName: "",
        code: "",
        state: "Y",
        remark: ""
      },
      orgTypeList: [
        {
          value: "COMP",
          label: "公司"
        },
        {
          value: "DEPART",
          label: "部门"
        },
        {
          value: "GROUP",
          label: "小组"
        }
      ],
      rules: {
        code: [{ validator: validateCode, trigger: "blur" }],
        name: [{ required: true, message: "租户名称不能为空", trigger: "blur" }]
      }
    };
  },
  methods: {
    setData(obj) {
      this.formObj.code = obj.tenantId;
      this.formObj.parentName = obj.tenantName;
      this.formObj.remark = obj.remark;
      this.formObj.state = obj.state;
    },
    hdlSubmit(name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          _config[this.type].submit(this.formObj).then(() => {
            this.curValue = false;
            this.$emit(_config[this.type].success_evt, this.formObj);
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
