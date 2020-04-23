<template>
  <div>
    <Card>
      <Form :model="formItem" :label-width="70" inline>
        <FormItem label="角色编号">
          <Input v-model="formItem.code" placeholder="请输入角色编号"></Input>
        </FormItem>
        <FormItem label="角色名称">
          <Input v-model="formItem.name" placeholder="请输入角色名称"></Input>
        </FormItem>
        <FormItem label="角色类型">
          <Input
            v-model="formItem.roleType"
            placeholder="请输入角色类型"
          ></Input>
        </FormItem>
        <FormItem style="float:right">
          <Button type="primary" @click="hdlquery">查询</Button>
          <Button style="margin-left: 8px" @click="reset">重置</Button>
        </FormItem>
      </Form>
      <Row type="flex" :gutter="20" class="row-operation">
        <Col
          ><Button icon="md-add" type="primary" @click="showAddModal = true"
            >新增</Button
          >
        </Col>
        <Col
          ><Button icon="md-trash" type="error" @click="hdlDelete"
            >删除</Button
          ></Col
        >
        <!-- <Col><Button icon="md-person-add">权限设置</Button></Col> -->
      </Row>
      <role-create
        v-model="showAddModal"
        @on-cancel="showAddModal = false"
        @on-add-role="hdlquery"
      ></role-create>
      <tables
        stripe
        border
        ref="tables"
        v-model="tableData"
        :columns="columns"
        :loading="isLoading"
      />
      <div style="margin: auto; text-align: right;padding-top:10px">
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
import RoleCreate from "./create";
export default {
  name: "role-list",
  components: {
    Tables,
    RoleCreate
  },
  data() {
    return {
      showAddModal: false,
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
        {
          type: "selection",
          width: 60,
          align: "center"
        },
        { title: "角色编号", key: "code", align: "center" },
        { title: "角色名称", key: "name", align: "center" },
        { title: "角色类别", key: "roleType", align: "center" },
        { title: "角色描述", key: "extension", align: "center" }
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
    hdlDelete() {
      this.$Modal.confirm({
        title: "确认删除？",
        content: "确定删除选中记录?",
        onOk() {
          console.log("delete");
        }
      });
      // delRole();
    },
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
