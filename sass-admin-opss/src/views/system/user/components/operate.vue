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
    <Col>
      <Button icon="md-person-add" @click="hdlClickGrant">
        角色授权
      </Button>
    </Col>
  </Row>
</template>

<script>
export default {
  name: "user-operation",
  methods: {
    hdlClickCreate() {
      this.$emit("on-user-create");
    },
    hdlClickDelete() {
      this.$emit("on-user-delete");
    },
    hdlClickGrant() {
      this.$emit("on-user-grant");
    }
  }
};
</script>
