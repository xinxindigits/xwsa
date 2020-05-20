<template>
  <Form :model="form" inline label-colon @submit.native.prevent>
    <FormItem>
      <Input v-model="form.name" placeholder="标签名"></Input>
    </FormItem>
    <FormItem>
      <Button type="primary" @click="hdlquery">查询</Button>
      <Button style="margin-left: 8px" @click="reset">重置</Button>
    </FormItem>
  </Form>
</template>

<script>
export default {
  name: "tag-query",
  props: {
    value: {
      type: Object,
      default() {
        return {
          name: ""
        };
      }
    }
  },
  data() {
    return {
      form: {
        name: ""
      }
    };
  },
  methods: {
    hdlquery() {
      this.$emit("on-tag-query", this.form);
    },
    reset() {
      this.form.name = "";
      this.$emit("on-tag-reset");
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
