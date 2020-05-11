<template>
  <div>
    <Card>
      <user-query
        ref="query"
        v-model="formItem"
        @on-user-query="changePage(1)"
      ></user-query>
      <user-operation
        @on-user-create="hdlSingleCreate"
        @on-user-delete="hdlDelete(selectedAccounts)"
        @on-user-grant="hdlAccessUpdate(selectedAccounts)"
      ></user-operation>

      <Table
        stripe
        border
        ref="tables"
        :data="tableData"
        :columns="columns"
        :loading="isLoading"
        @on-selection-change="hdlSelectionChange"
      >
        <template slot-scope="{ row }" slot="gender">
          <span>{{ $mapd("gender", row.gender) }}</span>
        </template>
        <template slot-scope="{ row }" slot="status">
          <span>{{ $mapd("userStatus", row.status) }}</span>
        </template>
        <template slot-scope="{ row }" slot="org">
          <span>{{ row | orgName }}</span>
        </template>
        <template slot-scope="{ row }" slot="create_time">
          <span>{{ row.gmtCreated | timeFilter }}</span>
        </template>
        <template slot-scope="{ row }" slot="action">
          <Button
            type="info"
            size="small"
            style="margin-right: 5px"
            @click="hdlQueryInfo(row)"
            ghost
          >
            详情
          </Button>
          <Button
            type="primary"
            size="small"
            style="margin-right: 5px"
            @click="hdlSingleModified(row)"
          >
            修改
          </Button>
          <Button type="error" size="small" @click="hdlDelete([row.account])">
            删除
          </Button>
        </template>
      </Table>
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
      <user-modify
        ref="modifyModal"
        v-model="showModal"
        :type="modifyType"
        @on-cancel="showModal = false"
        @user-modified="hdlquery"
      >
      </user-modify>
      <user-grant
        ref="showRoleGrantModal"
        v-model="showRoleGrantModal"
        @on-user-grant-role="hdlRoleGrant"
        :roleList="roleList"
      ></user-grant>
    </Card>
    <Modal v-model="showUserDetail" title="用户详情" footer-hide>
      <Row class="row-detail">
        <Col span="12">账号：{{ userDetail.account }}</Col>
        <Col span="12">id：{{ userDetail.id }}</Col>
      </Row>
      <Row class="row-detail">
        <Col span="12">名称：{{ userDetail.name }}</Col>
        <Col span="12">角色：{{ userDetail.roleName }}</Col>
      </Row>
      <Row class="row-detail">
        <Col span="24">所属机构：{{ userDetail | orgName }}</Col>
      </Row>
      <Row class="row-detail">
        <Col span="24">创建时间：{{ userDetail.gmtCreated | timeFilter }}</Col>
      </Row>
      <Row class="row-detail">
        <Col span="24">备注：{{ userDetail.extension }}</Col>
      </Row>
    </Modal>
  </div>
</template>

<script>
import {
  getUserList,
  deleteUser,
  getUserDetail,
  getAllRoles,
  getAllOrganizationTree
} from "@/api";
import { UserGrant, UserModify, UserOperation, UserQuery } from "./components";
export default {
  name: "user-list",
  components: { UserGrant, UserModify, UserOperation, UserQuery },
  computed: {
    selectedAccounts() {
      return this.tbSelection.map(item => item.account);
    }
  },
  filters: {
    orgName: function(data) {
      return data.orgs && data.orgs.length > 0 ? data.orgs[0].name : "";
    }
  },
  data() {
    return {
      showRoleGrantModal: false,
      modifyType: "create",
      showModal: false,
      showGrantModal: false,
      showUpdateModal: false,
      showUserDetail: false,

      isLoading: false,
      pageSize: 10,
      total: 0,
      page: 1,
      formItem: {
        account: "",
        name: "",
        code: "",
        gender: ""
      },
      tableData: [],
      columns: [
        {
          type: "selection",
          width: 60,
          align: "center"
        },
        { title: "账号", key: "account", align: "center" },
        { title: "名称", key: "name", align: "center" },
        { title: "所属机构", key: "orgName", align: "center", slot: "org" },
        { title: "性别", key: "gender", align: "center", slot: "gender" },
        { title: "状态", key: "status", align: "center", slot: "status" },
        {
          title: "创建时间",
          key: "gmtCreated",
          align: "center",
          slot: "create_time"
        },
        { title: "备注", key: "extension", align: "center" },
        {
          title: "操作",
          slot: "action",
          width: 200,
          align: "center"
        }
      ],
      tbSelection: [],
      userDetail: {
        account: "",
        extension: "",
        gmtCreated: "",
        id: "",
        name: "",
        roleName: "",
        orgs: []
      },
      roleList: []
    };
  },
  methods: {
    hdlquery() {
      this.changePage(1);
    },
    changePage(pageIndex) {
      this.isLoading = true;
      let pageSize = this.pageSize;
      getUserList({ pageIndex, pageSize, ...this.formItem })
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
    hdlDelete(accounts) {
      let self = this;
      if (accounts.length > 0) {
        this.$Modal.confirm({
          title: "确认删除？",
          content: `确定删除选中记录?`,
          onOk() {
            deleteUser({ accounts }).then(() => {
              this.$Message.success("删除成功！");
              self.hdlquery();
            });
          }
        });
      } else {
        this.$Message.warning("请选择一条记录!");
      }
    },

    hdlSelectionChange(selection) {
      this.tbSelection = selection;
    },
    hdlSingleCreate() {
      this.modifyType = "create";
      this.showModal = true;
      this.$refs.modifyModal.reset();
    },
    hdlAccessUpdate(arr) {
      if (arr.length == 0) {
        this.$Message.warning("请选择授权用户");
      } else if (arr.length > 1) {
        this.$Message.warning("当前接口支持一个用户进行授权");
      } else {
        getUserDetail({ account: arr[0] }).then(({ data }) => {
          this.showRoleGrantModal = true;
          let codeArr = data.roles.map(n => {
            return n.code;
          });
          this.$refs.showRoleGrantModal.setData({ userRoles: codeArr });
        });
      }
    },
    hdlRoleGrant() {},
    hdlSingleModified({ account }) {
      getUserDetail({ account }).then(res => {
        this.modifyType = "update";
        let { data } = res;
        let codeArr = data.roles.map(n => {
          return n.code;
        });
        let orgCode = data.orgs.map(n => {
          return n.code;
        })[0];
        data.roles = codeArr;
        data.orgCode = orgCode;
        this.$refs.modifyModal.setData(data);
        this.showModal = true;
      });
    },
    hdlQueryInfo({ account }) {
      getUserDetail({ account }).then(res => {
        let { data } = res;
        data.roleName = data.roles
          .map(n => {
            return n.name;
          })
          .join("、");
        this.userDetail = data;
        this.showUserDetail = true;
      });
    }
  },
  mounted() {
    getAllOrganizationTree().then(({ data }) => {
      this.$refs.modifyModal.setOrgList(data);
    });
    getAllRoles().then(({ data }) => {
      this.roleList = data;
      this.$refs.modifyModal.setRoleList(data);
    });
    this.changePage(1);
  }
};
</script>

<style lang="less" scoped>
.row-operation {
  padding: 10px 0;
}
.row-detail {
  margin-bottom: 10px;
  font-size: 16px;
}
</style>
