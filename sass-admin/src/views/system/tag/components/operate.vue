<template>
  <Row type="flex" :gutter="20" class="row-operation">
    <Col
      ><Button icon="md-add" type="primary" @click="hdlClickCreate"
        >新增</Button
      >
    </Col>
  </Row>
</template>

<script>
export default {
  name: "tag-operation",
  methods: {
    hdlClickCreate() {
      this.$emit("on-tag-create");
    }
  }
};
</script>

<style lang="less" scoped></style>
