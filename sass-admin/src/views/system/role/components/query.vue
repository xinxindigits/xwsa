<template>
  <Form ref="form" :model="form" inline label-colon>
    <FormItem prop="code">
      <Input v-model="form.code" placeholder="角色编号"></Input>
    </FormItem>
    <FormItem prop="name">
      <Input v-model="form.name" placeholder="角色名称"></Input>
    </FormItem>
    <FormItem prop="roleType">
      <Select
        v-model="form.roleType"
        placeholder="角色类型"
        style="width:160px"
        filterable
      >
        <Option value="admin">管理员</Option>
        <Option value="user">用户</Option>
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
      form: {
        name: "",
        code: "",
        roleType: ""
      }
    };
  },
  methods: {
    hdlquery() {
      this.$emit("on-role-query", this.form);
    },
    reset() {
      this.$refs.form.resetFields();
      this.$emit("on-role-reset");
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
