<template>
  <Form :model="form" inline label-colon>
    <FormItem prop="account">
      <Input v-model="form.account" placeholder="操作账号"></Input>
    </FormItem>
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
      form: {
        account: ""
      }
    };
  },
  methods: {
    hdlquery() {
      this.$emit("on-log-query", this.form);
    },
    reset() {
      this.form = {
        account: ""
      };
      this.$emit("on-log-query-reset", this.form);
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
