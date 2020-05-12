<template>
  <Form :model="formItem" inline label-colon>
    <FormItem>
      <Input v-model="formItem.account" placeholder="账号"></Input>
    </FormItem>
    <FormItem>
      <Input v-model="formItem.name" placeholder="姓名"></Input>
    </FormItem>
    <FormItem prop="gender">
      <Select v-model="formItem.gender" placeholder="性别" style="width:100px">
        <Option v-for="(n, index) in gender" :value="index" :key="index">{{
          n
        }}</Option>
      </Select>
    </FormItem>
    <FormItem prop="status">
      <Select v-model="formItem.status" placeholder="状态" style="width:100px">
        <Option v-for="(n, index) in userStatus" :value="index" :key="index">{{
          n
        }}</Option>
      </Select>
    </FormItem>
    <FormItem>
      <Button type="primary" @click="hdlquery">查询</Button>
      <Button style="margin-left: 8px" @click="reset">重置</Button>
    </FormItem>
  </Form>
</template>

<script>
import { gender, userStatus } from "@/libs/dic";
export default {
  name: "user-query",
  props: {
    value: {
      type: Object,
      default() {
        return {
          account: "",
          name: "",
          code: "",
          gender: "",
          status: ""
        };
      }
    }
  },
  data() {
    return {
      gender,
      userStatus,
      formItem: {
        account: "",
        name: "",
        code: "",
        gender: "",
        status: ""
      }
    };
  },
  methods: {
    hdlquery() {
      this.$emit("on-user-query", this.formItem);
    },
    reset() {
      this.formItem.name = "";
      this.formItem.account = "";
      this.formItem.code = "";
      this.formItem.gender = "";
      this.formItem.status = "";
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
  }
};
</script>
