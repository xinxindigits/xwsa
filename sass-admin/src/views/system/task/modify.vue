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
      <FormItem label="租户编号" prop="code" v-if="type == 'update'">
        <Input
          v-model="formObj.code"
          style="width: 280px"
          :disabled="true"
        ></Input>
      </FormItem>
      <FormItem label="任务类型" prop="taskType">
        <Select v-model="formObj.taskType" style="width:280px">
          <Option v-for="item in statusEnum" :value="item[0]" :key="item[0]">{{
            item[1]
          }}</Option>
        </Select>
      </FormItem>
      <FormItem label="cron表达式" prop="cronExpression">
        <Input v-model="formObj.cronExpression" style="width: 280px"></Input>
      </FormItem>
      <FormItem label="会话每次提取上限" prop="countCeiling">
        <Input
          v-model="formObj.countCeiling"
          number
          style="width: 280px"
        ></Input>
      </FormItem>
      <FormItem label="会话每次提取间隔" prop="timeInterval">
        <Input
          v-model="formObj.timeInterval"
          number
          style="width: 280px"
        ></Input>
      </FormItem>
      <FormItem label="状态" prop="status">
        <RadioGroup v-model="formObj.status">
          <Radio label="0">启用</Radio>
          <Radio label="1">禁用</Radio>
        </RadioGroup>
      </FormItem>
      <FormItem label="任务描述" prop="remark">
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
import { updateTenantTask, createTenantTask } from "@/api";
import { taskType } from "@/libs/dic";
const _config = {
  create: {
    title: "新增任务",
    success_evt: "on-add-task",
    submit: createTenantTask
  },
  update: {
    title: "更新任务",
    success_evt: "on-update-task",
    submit: updateTenantTask
  }
};

export default {
  name: "organization-task",
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
      statusEnum: Object.entries(taskType),
      formObj: {
        id: "",
        code: "",
        taskType: "",
        cronExpression: "",
        countCeiling: "",
        timeInterval: "",
        status: ""
      },
      rules: {
        taskType: [
          { required: true, message: "任务类型不能为空", trigger: "blur" }
        ],
        cronExpression: [
          { required: true, message: "corn表达式不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "状态不能为空", trigger: "change" }
        ],
        countCeiling: [
          { type: "number", message: "请输入数字", trigger: "blur" }
        ],
        timeInterval: [
          { type: "number", message: "请输入数字", trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    setData(obj) {
      this.formObj.id = obj.id;
      this.formObj.code = obj.tenantId;
      this.formObj.taskType = obj.taskType;
      this.formObj.cronExpression = obj.cronExpression;
      this.formObj.countCeiling = obj.countCeiling;
      this.formObj.timeInterval = obj.timeInterval;
      this.formObj.status = "" + obj.deleted;
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
