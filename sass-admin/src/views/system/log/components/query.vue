<template>
  <Form :model="formItem" inline label-colon>
    <FormItem>
      <Input v-model="formItem.account"></Input>
    </FormItem>
    <!-- <FormItem>
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
    </FormItem> -->
    <FormItem>
      <Button type="primary" @click="hdlquery">查询</Button>
      <Button style="margin-left: 8px" @click="reset">重置</Button>
    </FormItem>
  </Form>
</template>

<script>
export default {
  name: "log-query",
  props: {
    value: {
      type: Object,
      default() {
        return {
          account: ""
        };
      }
    }
  },
  data() {
    return {
      // daterange: [],
      // rangeOption: {
      //   disabledDate(date) {
      //     return date && date.valueOf() > Date.now();
      //   }
      // },
      formItem: {
        account: ""
        // startTime: "",
        // endTime: ""
      }
    };
  },
  methods: {
    hdlquery() {
      this.$emit("on-log-query", this.formItem);
    },
    reset() {
      //TODO
      // this.daterange = [];
      this.formItem = {
        account: ""
        // startTime: "",
        // endTime: ""
      };
      this.$emit("on-log-query-reset", this.formItem);
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
    }
    // daterange(newValue) {
    //   if (
    //     newValue.length === 2 &&
    //     newValue[0] instanceof Date &&
    //     newValue[0] instanceof Date
    //   ) {
    //     this.formItem.startTime = newValue[0].getTime() + "";
    //     this.formItem.endTime = newValue[1].getTime() + "";
    //   } else {
    //     this.formItem.startTime = "";
    //     this.formItem.endTime = "";
    //   }
    // }
  }
};
</script>
