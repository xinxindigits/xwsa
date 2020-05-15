<template>
  <Card class="search">
    <Form ref="searchForm" :model="formObj">
      <FormItem prop="keyWord">
        <Input v-model="formObj.keyWord" placeholder="关键词"></Input>
      </FormItem>
      <FormItem>
        <DatePicker
          v-model="daterange"
          type="daterange"
          placement="bottom-start"
          split-panels
          confirm
          :options="rangeOption"
          placeholder="日期范围"
          style="width: 200px"
        ></DatePicker>
      </FormItem>
      <FormItem>
        <Button type="primary" @click="hdlQuery">查询 </Button>
        <Button style="margin-left:8px" @click="hdlReset">重置 </Button>
      </FormItem>
    </Form>
  </Card>
</template>

<script>
export default {
  name: "msg-record-query",
  data() {
    return {
      formObj: {
        keyWord: "",
        startTime: "",
        endTime: ""
      },
      daterange: [],
      rangeOption: {
        disabledDate(date) {
          return date && date.valueOf() > Date.now();
        }
      }
    };
  },
  methods: {
    hdlQuery() {
      this.$emit("on-query", this.formObj);
    },
    reset() {
      this.$refs.searchForm.resetFields();
      this.daterange = [];
    },
    hdlReset() {
      this.reset();
      this.$emit("on-reset");
    }
  },
  watch: {
    daterange(newValue) {
      if (
        newValue.length === 2 &&
        newValue[0] instanceof Date &&
        newValue[0] instanceof Date
      ) {
        console.log(newValue[0].getTime());
        this.formObj.startTime = newValue[0].getTime() + "";
        this.formObj.endTime =
          newValue[1].getTime() + 24 * 60 * 60 * 1000 - 1000 + "";
      } else {
        this.formObj.startTime = "";
        this.formObj.endTime = "";
      }
    }
  }
};
</script>

<style lang="less" scoped></style>
