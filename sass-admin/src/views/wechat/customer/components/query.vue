<template>
  <Form ref="form" :model="formItem" inline label-colon>
    <FormItem>
      <Input v-model.trim="formItem.customerName" placeholder="姓名"></Input>
    </FormItem>
    <FormItem>
      <DatePicker
        v-model="daterange"
        type="daterange"
        placement="bottom-start"
        split-panels
        confirm
        :options="rangeOption"
        placeholder="选择日期"
        style="width: 200px"
      ></DatePicker>
    </FormItem>
    <FormItem>
      <Button type="primary" @click="hdlquery">查询</Button>
      <Button style="margin-left: 8px" @click="reset">重置</Button>
    </FormItem>
  </Form>
</template>

<script>
export default {
  name: "customer-query",
  props: {
    value: {
      type: Object,
      default() {
        return {
          startTime: "",
          endTime: "",
          memberUserIds: [],
          customerName: ""
        };
      }
    }
  },
  data() {
    return {
      daterange: [],
      rangeOption: {
        disabledDate(date) {
          return date && date.valueOf() > Date.now();
        }
      },
      formItem: {
        startTime: "",
        endTime: "",
        memberUserIds: [],
        customerName: ""
      }
    };
  },
  methods: {
    hdlquery() {
      this.$emit("on-customer-query", this.formItem);
    },
    reset() {
      this.daterange = [];
      this.$refs.form.resetFields();
      this.$emit("input", {
        startTime: "",
        endTime: "",
        memberUserIds: [],
        customerName: ""
      });
      this.$emit("on-customer-reset");
    }
  },
  watch: {
    value: {
      deep: true,
      immediate: true,
      handler(newValue) {
        this.formItem = newValue;
      }
    },
    formItem: {
      deep: true,
      immediate: true,
      handler(newValue) {
        this.$emit("input", newValue);
      }
    },
    daterange(newValue) {
      if (
        newValue.length === 2 &&
        newValue[0] instanceof Date &&
        newValue[0] instanceof Date
      ) {
        this.formItem.startTime = newValue[0].getTime() + "";
        this.formItem.endTime =
          newValue[1].getTime() + 24 * 60 * 60 * 1000 - 1000 + "";
      } else {
        this.formItem.startTime = "";
        this.formItem.endTime = "";
      }
    }
  }
};
</script>
