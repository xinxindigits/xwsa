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
            @click="hdlDelete(delteAccounts)"
            >删除</Button
          ></Col
        >
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
  </div>
</template>

<script>
import { getUserList, deleteUser, getUserDetail } from "@/api/data_user";
import { getRoleList } from "@/api/data";
import modify from "./modify";
export default {
  name: "user-list",
  components: { modify },
  computed: {
    delteAccounts() {
      return this.tbSelection.map(item => item.account);
    }
  },
  data() {
    return {
      modifyType: "create",
      showModal: false,
      showGrantModal: false,
      showUpdateModal: false,
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
        { title: "姓名", key: "name", align: "center" },
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
          width: 150,
          align: "center"
        }
      ],
      tbSelection: []
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
    hdlSingleModified(data) {
      this.modifyType = "update";
      console.log(data);
      let { account } = data;
      getUserDetail({ account }).then(res => {
        let { data } = res;
        this.showModal = true;
        let codeArr = data.roles.map(n => {
          return n.code;
        });
        data.roles = codeArr;
        this.$refs.modifyModal.setData(data);
      });
    }
  },
  mounted() {
    getRoleList({ pageIndex: 1, pageSize: 1000 }).then(rolelist => {
      this.$refs.modifyModal.setRoleList(rolelist.data.items);
    });
    this.changePage(1);
  }
};
</script>

<style lang="less" scoped>
.row-operation {
  padding: 10px 0;
}
</style>
