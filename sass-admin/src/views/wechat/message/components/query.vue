<template>
  <Form :model="formItem" inline label-colon>
    <FormItem>
      <Select
        v-model="formItem.userId"
        filterable
        remote
        :remote-method="remoteMethod1"
        :loading="loading"
        placeholder="成员名"
      >
        <Option
          v-for="(option, index) in options"
          :value="option.value"
          :key="index"
          >{{ option.label }}</Option
        >
      </Select>
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
      <Input v-model="formItem.keyWord" placeholder="内容"></Input>
    </FormItem>
    <FormItem>
      <Button type="primary" @click="hdlquery">查询</Button>
      <Button style="margin-left: 8px" @click="reset">重置</Button>
    </FormItem>
  </Form>
</template>

<script>
import { queryMember } from "@/api";
export default {
  name: "wc-msg-query",
  props: {
    value: {
      type: Object,
      default() {
        return {
          tenantId: "",
          userId: "",
          startTime: "",
          endTime: "",
          keyWord: ""
        };
      }
    }
  },
  data() {
    return {
      loading: false,
      options: [],
      list: [],

      daterange: [],
      rangeOption: {
        disabledDate(date) {
          return date && date.valueOf() > Date.now();
        }
      },
      formItem: {
        tenantId: "",
        userId: "",
        startTime: "",
        endTime: "",
        keyWord: ""
      }
    };
  },
  methods: {
    remoteMethod1(query) {
      let self = this;
      if (query !== "") {
        this.loading = true;
        queryMember({ pageIndex: 1, pageSize: 3000, memberName: query }).then(
          res => {
            this.loading = false;
            let data = res.data.items;
            self.options = data.map(item => {
              return {
                value: item.userId,
                label: item.memberName
              };
            });
          }
        );
      } else {
        this.options = [];
      }
    },

    hdlquery() {
      this.$emit("on-wc-msg-query", this.formItem);
    },
    reset() {
      this.formItem.userId = "";
      this.formItem.startTime = "";
      this.formItem.endTime = "";
      this.$emit("on-wc-msg-reset");
    }
  },
  mounted() {},
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
