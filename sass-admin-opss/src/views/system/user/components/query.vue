<template>
  <Form :model="form" inline label-colon>
    <FormItem>
      <Input v-model="form.account" placeholder="账号"></Input>
    </FormItem>
    <FormItem>
      <Input v-model="form.name" placeholder="姓名"></Input>
    </FormItem>
    <FormItem prop="gender">
      <Select v-model="form.gender" placeholder="性别" style="width:100px">
        <Option v-for="(n, index) in gender" :value="index" :key="index">{{
          n
        }}</Option>
      </Select>
    </FormItem>
    <FormItem prop="status">
      <Select v-model="form.status" placeholder="状态" style="width:100px">
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
      form: {
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
      this.$emit("on-user-query", this.form);
    },
    reset() {
      this.form.name = "";
      this.form.account = "";
      this.form.code = "";
      this.form.gender = "";
      this.form.status = "";
      this.$emit("on-user-reset");
    }
  },
  watch: {
    value: {
      deep: true,
      immediate: true,
      handler(newValue) {
        this.form = newValue;
      }
    },
    form: {
      deep: true,
      immediate: true,
      handler(newValue) {
        this.$emit("input", newValue);
      }
    }
  }
};
</script>
