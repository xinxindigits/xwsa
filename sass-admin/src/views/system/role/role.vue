<template>
  <div>
    <Card>
      <role-query
        ref="query"
        v-model="formItem"
        @on-role-query="changePage(1)"
      ></role-query>
      <role-operation
        @on-role-create="hdlSingleModified('create')"
        @on-role-delete="hdlDelete(delteRoleCodes)"
        @on-role-grant="hdlAccessUpdate"
      ></role-operation>
      <Table
        stripe
        border
        ref="tables"
        :data="tableData"
        :columns="columns"
        :loading="isLoading"
        @on-selection-change="hdlSelectionChange"
      >
        <template slot-scope="{ row }" slot="action">
          <Button
            type="primary"
            size="small"
            style="margin-right: 5px"
            @click="hdlSingleModified('update', row)"
          >
            修改
          </Button>
          <Button type="error" size="small" @click="hdlDelete([row.code])">
            删除
          </Button>
        </template>
      </Table>
      <div style="margin: auto; text-align: right;padding-top:10px">
        <Page
          :total="total"
          :current="page"
          :page-size="pageSize"
          :page-size-opts="[10, 20, 50, 100]"
          @on-change="changePage"
          @on-page-size-change="changePageSize"
          show-sizer
          show-elevator
          show-total
          transfer
        ></Page>
      </div>
      <role-grant
        ref="grantModal"
        v-model="showGrantModal"
        @on-cancel="showGrantModal = false"
        @on-grant-role="hdlqueryAfterReset"
      ></role-grant>
      <role-update
        :type="modify"
        v-model="showAddModal"
        @on-cancel="showAddModal = false"
        @role-modified="hdlqueryAfterReset"
        ref="modifyModal"
      ></role-update>
    </Card>
  </div>
</template>

<script>
import { getRoleList, delRole } from "@/api/data";
import { RoleGrant, RoleUpdate, RoleQuery, RoleOperation } from "./components";
export default {
  name: "role-list",
  components: {
    RoleUpdate,
    RoleGrant,
    RoleQuery,
    RoleOperation
  },
  computed: {
    delteRoleCodes() {
      return this.tbSelection.map(item => item.code);
    }
  },
  data() {
    return {
      modify: "create",
      showAddModal: false,
      showGrantModal: false,
      showUpdateModal: false,
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
        { title: "角色描述", key: "extension", align: "center" },
        {
          title: "操作",
          slot: "action",
          width: 150,
          align: "center"
        }
      ],
      tbSelection: []
    };
  },
  methods: {
    hdlqueryAfterReset() {
      this.$refs.query.reset();
      this.changePage(1);
    },
    changePage(pageIndex = 1) {
      this.isLoading = true;
      let pageSize = this.pageSize;
      getRoleList({ pageIndex, pageSize, ...this.formItem })
        .then(res => {
          let { data } = res;
          this.tableData = data.items;
          this.total = Number(data.total);
        })
        .finally(() => (this.isLoading = false));
    },
    changePageSize(pageSize) {
      this.pageSize = pageSize;
      this.changePage(1);
    },
    hdlDelete(roleCodes) {
      let self = this;
      if (roleCodes.length > 0) {
        this.$Modal.confirm({
          title: "确认删除？",
          content: `确定删除选中记录?`,
          onOk() {
            delRole({ roleCodes }).then(() => {
              this.$Message.success("删除成功！");
              self.hdlqueryAfterReset();
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
    hdlSelectionChange(selection) {
      this.tbSelection = selection;
    },
    hdlSingleModified(type = "create", data) {
      this.modify = type;
      let d = data || {
        name: "",
        code: "",
        roleType: "",
        extension: ""
      };
      this.$refs.modifyModal.setData(d);
      this.showAddModal = true;
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
