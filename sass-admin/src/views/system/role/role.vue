<template>
  <div>
    <Card>
      <Form :model="formItem" inline label-colon>
        <FormItem>
          <Input v-model="formItem.code" placeholder="角色编号"></Input>
        </FormItem>
        <FormItem>
          <Input v-model="formItem.name" placeholder="角色名称"></Input>
        </FormItem>
        <FormItem>
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
        <Col
          ><Button icon="md-person-add" @click="hdlAccessUpdate"
            >权限设置</Button
          ></Col
        >
        <role-grant
          ref="grantModal"
          v-model="showGrantModal"
          @on-cancel="showGrantModal = false"
          @on-grant-role="hdlquery"
        ></role-grant>
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
        @on-selection-change="hdlSelectionChange"
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
import { getRoleList, delRole } from "@/api/data";
import RoleCreate from "./create";
import RoleGrant from "./granttree";
export default {
  name: "role-list",
  components: {
    Tables,
    RoleCreate,
    RoleGrant
  },
  computed: {
    delteRoleCodes() {
      return this.tbSelection.map(item => item.code);
    }
  },
  data() {
    return {
      showAddModal: false,
      showGrantModal: false,
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
      ],
      tbSelection: []
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
      const roleCodes = this.delteRoleCodes;
      if (this.tbSelection.length > 0) {
        this.$Modal.confirm({
          title: "确认删除？",
          content: `确定删除选中记录?`,
          onOk() {
            delRole({ roleCodes }).then(() => {
              this.$Message.success("删除成功！");
            });
          }
        });
      } else {
        this.$Message.warning("请选择一条记录!");
      }
    },
    hdlAccessUpdate() {
      if (this.tbSelection.length > 1) {
        this.$Message.warning("只能选一条数据！");
      } else if (this.tbSelection.length == 0) {
        this.$Message.warning("请选择一条记录!");
      } else {
        let { name: roleName, code: roleCode } = this.tbSelection[0];
        this.$refs.grantModal.queryGrantTree({
          roleCode,
          roleName
        });
      }
    },
    reset() {
      this.formItem.name = "";
      this.formItem.roleType = "";
      this.formItem.code = "";
    },
    hdlSelectionChange(selection) {
      this.tbSelection = selection;
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
