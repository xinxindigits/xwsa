<template>
  <div>
    <Card>
      <Form :model="formItem" :label-width="80" inline>
        <FormItem label="角色名称">
          <i-input
            v-model="formItem.name"
            placeholder="请输入角色名称"
          ></i-input>
        </FormItem>
        <FormItem label="租户id">
          <i-input v-model="formItem.code" placeholder="请输入租户id"></i-input>
        </FormItem>
        <FormItem style="float:right">
          <Button type="primary" @click="hdlquery">查询</Button>
          <Button style="margin-left: 8px">重置</Button>
        </FormItem>
      </Form>
      <Row type="flex" :gutter="20" class="row-operation">
        <i-col><Button icon="md-add" type="primary">新增</Button></i-col>
        <i-col><Button icon="md-trash" type="error">删除</Button></i-col>
        <i-col><Button icon="md-person-add">权限设置</Button></i-col>
      </Row>

      <tables
        stripe
        border
        ref="tables"
        v-model="tableData"
        :columns="columns"
        @on-delete="handleDelete"
        :loading="isLoading"
      />
      <div style="margin: auto; text-align: right">
        <Page
          :total="total"
          :current="page"
          :page-size-opts="[10, 20, 50, 100]"
          @on-change="changePage"
          @on-page-size-change="changePageSize"
          show-sizer
          show-elevator
          show-total
          transfer
        ></Page>
      </div>
    </Card>
  </div>
</template>

<script>
import Tables from "@/components/tables";
import { getRoleList } from "@/api/data";
export default {
  name: "role-list",
  components: {
    Tables
  },
  data() {
    return {
      isLoading: false,
      pageSize: 10,
      total: 0,
      page: 1,
      formItem: {
        name: "",
        code: "",
        roleType: ""
      },
      tableData: [],
      columns: [
        { title: "角色编号", key: "code" },
        { title: "角色名称", key: "name" },
        { title: "角色类别", key: "roleType" },
        { title: "角色描述", key: "extension" }
      ]
    };
  },
  methods: {
    hdlquery() {
      this.changePage(1);
    },
    changePage(pageNum) {
      this.isLoading = true;
      let pageSize = this.pageSize;
      getRoleList({ pageNum, pageSize, ...this.formItem })
        .then(res => {
          let { data } = res;
          this.reset();
          this.tableData = data.items;
          this.total = Number(data.total);
        })
        .finally(() => (this.isLoading = false));
    },
    changePageSize(pageSize) {
      this.pageSize = pageSize;
      this.changePage(1);
    },
    handleDelete() {},
    reset() {
      this.formItem.name = "";
      this.formItem.roleType = "";
      this.formItem.code = "";
    }
  },
  mounted() {
    this.changePage(1);
  }
};
</script>

<style lang="less" scoped>
.row-operation {
  padding: 10px 0;
}
</style>
