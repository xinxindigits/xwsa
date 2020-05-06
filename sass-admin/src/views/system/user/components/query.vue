<template>
  <Form :model="formItem" inline label-colon>
    <FormItem>
      <Input v-model="formItem.account" placeholder="账号"></Input>
    </FormItem>
    <FormItem>
      <Input v-model="formItem.name" placeholder="姓名"></Input>
    </FormItem>
    <FormItem>
      <Button type="primary" @click="hdlquery">查询</Button>
      <Button style="margin-left: 8px" @click="reset">重置</Button>
    </FormItem>
  </Form>
</template>

<script>
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
          gender: ""
        };
      }
    }
  },
  data() {
    return {
      formItem: {
        account: "",
        name: "",
        code: "",
        gender: ""
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
