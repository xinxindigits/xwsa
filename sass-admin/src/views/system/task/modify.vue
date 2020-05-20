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
          :disabled="true"
        ></Input>
      </FormItem>
      <FormItem label="任务类型" prop="taskType">
        <Select
          v-model="form.taskType"
          style="width:280px"
          :disabled="type == 'update'"
        >
          <Option v-for="item in statusEnum" :value="item[0]" :key="item[0]">{{
            item[1]
          }}</Option>
        </Select>
      </FormItem>
      <FormItem label="cron表达式" prop="cronExpression">
        <Input v-model="form.cronExpression" style="width: 280px"></Input>
      </FormItem>
      <FormItem
        label="会话每次提取上限(条)"
        prop="countCeiling"
        v-if="form.taskType == this.MESSAGE_SYNC"
      >
        <Input
          v-model="form.countCeiling"
          number
          placeholder="请输入1-1000范围内的整数"
          style="width: 280px"
        ></Input>
      </FormItem>
      <FormItem
        label="会话每次提取间隔(秒)"
        prop="timeInterval"
        v-if="form.taskType == this.MESSAGE_SYNC"
      >
        <Input
          v-model="form.timeInterval"
          placeholder="请输入大于等于1的整数"
          number
          style="width: 280px"
        ></Input>
      </FormItem>
      <FormItem label="状态" prop="status">
        <RadioGroup v-model="form.status">
          <Radio label="0">启用</Radio>
          <Radio label="1">禁用</Radio>
        </RadioGroup>
      </FormItem>
      <FormItem label="任务描述" prop="remark">
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
      MESSAGE_SYNC: "MESSAGE_SYNC",
      form: {
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
          {
            required: true,
            min: 1,
            max: 1000,
            type: "integer",
            message: "请输入1-1000范围内的整数",
            trigger: "blur"
          }
        ],
        timeInterval: [
          {
            required: true,
            type: "integer",
            min: 1,
            message: "请输入大于等于1的整数",
            trigger: "blur"
          }
        ]
      }
    };
  },
  methods: {
    setData(obj) {
      this.form.id = obj.id;
      this.form.code = obj.tenantId;
      this.form.taskType = obj.taskType;
      this.form.cronExpression = obj.cronExpression;
      this.form.countCeiling = obj.countCeiling;
      this.form.timeInterval = obj.timeInterval;
      this.form.status = "" + obj.status;
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
