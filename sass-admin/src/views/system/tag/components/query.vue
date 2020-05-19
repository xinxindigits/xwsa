<template>
  <Form :model="formItem" inline label-colon @submit.native.prevent>
    <FormItem>
      <Input v-model="formItem.name" placeholder="标签名"></Input>
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
      formItem: {
        name: ""
      }
    };
  },
  methods: {
    hdlquery() {
      this.$emit("on-tag-query", this.formItem);
    },
    reset() {
      this.formItem.name = "";
      this.$emit("on-tag-reset");
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
