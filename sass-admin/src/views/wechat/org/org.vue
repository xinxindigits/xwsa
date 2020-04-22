<template>
  <div>
    <Card> </Card>
  </div>
</template>

<script>
export default {
  name: "org-list",
  data() {
    return {
      tableData: [],
      columns: [
        { title: "姓名", key: "name" },
        { title: "职务", key: "title" },
        { title: "部门", key: "department" },
        { title: "收集", key: "phoneNum" },
        { title: "邮箱", key: "email" }
      ]
    };
  },
  methods: {
    handleDelete() {}
  }
};
</script>

<style lang="less" scoped></style>
