<template>
  <Form ref="form" :model="formItem" inline label-colon>
    <FormItem prop="code">
      <Input v-model="formItem.code" placeholder="角色编号"></Input>
    </FormItem>
    <FormItem prop="name">
      <Input v-model="formItem.name" placeholder="角色名称"></Input>
    </FormItem>
    <FormItem prop="roleType">
      <Select
        v-model="formItem.roleType"
        placeholder="角色类型"
        style="width:160px"
        filterable
      >
        <Option value="admin">admin</Option>
        <Option value="user">user</Option>
      </Select>
    </FormItem>
    <FormItem>
      <Button type="primary" @click="hdlquery">查询</Button>
      <Button style="margin-left: 8px" @click="reset">重置</Button>
    </FormItem>
  </Form>
</template>

<script>
export default {
  name: "role-query",
  props: {
    value: {
      type: Object,
      default() {
        return {
          name: "",
          code: "",
          roleType: ""
        };
      }
    }
  },
  data() {
    return {
      formItem: {
        name: "",
        code: "",
        roleType: ""
      }
    };
  },
  methods: {
    hdlquery() {
      this.$emit("on-role-query", this.formItem);
    },
    reset() {
      this.$refs.form.resetFields();
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
