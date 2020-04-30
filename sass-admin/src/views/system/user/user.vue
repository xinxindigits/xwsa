<template>
  <div>
    <Card>
      <Form :model="formItem" inline label-colon>
        <FormItem>
          <Input v-model="formItem.account" placeholder="账号"></Input>
        </FormItem>
        <FormItem>
          <Input v-model="formItem.name" placeholder="姓名"></Input>
        </FormItem>
        <FormItem>
          <Button type="primary" @click="hdlquery">查询</Button>
          <Button style="margin-left: 8px" @click="reset">重置</Button>
        </FormItem>
      </Form>
      <Row type="flex" :gutter="20" class="row-operation">
        <Col
          ><Button icon="md-add" type="primary" @click="hdlSingleCreate"
            >新增</Button
          >
        </Col>
        <Col
          ><Button
            icon="md-trash"
            type="error"
            @click="hdlDelete(selectedAccounts)"
            >删除</Button
          ></Col
        >
        <Col>
          <Button
            icon="md-person-add"
            @click="hdlAccessUpdate(selectedAccounts)"
          >
            角色授权
          </Button>
          <grant-role
            ref="showRoleGrantModal"
            v-model="showRoleGrantModal"
            @on-user-grant-role="hdlRoleGrant"
            :roleList="roleList"
          ></grant-role>
        </Col>
      </Row>

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
      <modify
        ref="modifyModal"
        v-model="showModal"
        :type="modifyType"
        @on-cancel="showModal = false"
        @user-modified="hdlquery"
      >
      </modify>
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
        <Col span="24">创建时间：{{ userDetail.gmtCreated | timeFilter }}</Col>
      </Row>
      <Row class="row-detail">
        <Col span="24">备注：{{ userDetail.extension }}</Col>
      </Row>
    </Modal>
  </div>
</template>

<script>
import { getUserList, deleteUser, getUserDetail } from "@/api";
import { getRoleList } from "@/api/data";
import modify from "./modify";
import GrantRole from "./grant";
export default {
  name: "user-list",
  components: { modify, GrantRole },
  computed: {
    selectedAccounts() {
      return this.tbSelection.map(item => item.account);
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
        roleName: ""
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

    reset() {
      this.formItem.name = "";
      this.formItem.account = "";
      this.formItem.code = "";
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
        this.showModal = true;
        let { data } = res;
        let codeArr = data.roles.map(n => {
          return n.code;
        });
        data.roles = codeArr;
        this.$refs.modifyModal.setData(data);
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
    getRoleList({ pageIndex: 1, pageSize: 1000 }).then(({ data }) => {
      this.roleList = data.items;
      this.$refs.modifyModal.setRoleList(data.items);
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
