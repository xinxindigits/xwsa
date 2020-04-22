<template>
  <div>
    <Card>
      <tables
        ref="tables"
        editable
        searchable
        search-place="top"
        v-model="tableData"
        :columns="columns"
        @on-delete="handleDelete"
      />
    </Card>
  </div>
</template>

<script>
import Tables from "@/components/tables";
export default {
  name: "staff-list",
  components: {
    Tables
  },
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
