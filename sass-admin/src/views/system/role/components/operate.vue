<template>
  <Row type="flex" :gutter="20" class="row-operation">
    <Col
      ><Button icon="md-add" type="primary" @click="hdlClickCreate"
        >新增</Button
      >
    </Col>
    <Col
      ><Button icon="md-trash" type="error" @click="hdlClickDelete"
        >删除</Button
      ></Col
    >
    <Col
      ><Button icon="md-person-add" @click="hdlClickGrant"
        >权限设置</Button
      ></Col
    >
  </Row>
</template>

<script>
export default {
  name: "role-operation",
  methods: {
    hdlClickCreate() {
      this.$emit("on-role-create");
    },
    hdlClickDelete() {
      this.$emit("on-role-delete");
    },
    hdlClickGrant() {
      this.$emit("on-role-grant");
    }
  }
};
</script>

<style lang="less" scoped></style>
