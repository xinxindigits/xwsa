<template>
  <div>
    <Row :gutter="10" v-show="showList">
      <Col span="24">
        <Card>
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
                @click="hdlSingleModified(row)"
              >
                详情
              </Button>
            </template>
          </Table>
          <div style="margin: auto; text-align: right;padding-top:10px">
            <Page
              v-show="tableData.length > 0"
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
      </Col>
    </Row>
    <Row v-show="!showList">
      <Col span="24">
        <member-detail
          :items="memberDetail"
          @on-cancel="showList = true"
        ></member-detail>
      </Col>
    </Row>
  </div>
</template>

<script>
//搜索查部门、个人；点击查个人详情/部门员工列表
//初始查根节点部门；点击查当前节点员工列表

import { getMemberList, getMemberDetail } from "@/api";
import MemberDetail from "../common/member-detail/member-detail";
export default {
  name: "staff-list",
  components: {
    MemberDetail
  },
  data() {
    return {
      showList: true,
      memberDetail: {},

      columns: [
        // {
        //   type: "selection",
        //   width: 60,
        //   align: "center"
        // },
        { title: "id", key: "id", width: 80 },
        { title: "名称", key: "memberName" },
        { title: "微信号", key: "userId", ellipsis: true },
        { title: "手机", key: "mobile" },
        { title: "部门", key: "deptName" },
        { title: "邮箱", key: "mail" },
        {
          title: "操作",
          slot: "action",
          width: 100,
          align: "center"
        }
      ],
      test: false,
      isLoading: false,
      pageSize: 10,
      total: 0,
      page: 1,
      rules: {
        name: [{ required: true, message: "名称不能为空", trigger: "blur" }],
        url: [{ required: true, message: "URI不能为空", trigger: "blur" }],
        authority: [
          { required: true, message: "权限值不能为空", trigger: "blur" }
        ]
      },
      formItem: {
        name: "",
        code: ""
      },
      tableData: [],

      tbSelection: [],
      showDrawer: false,
      styles: {
        height: "calc(100% - 55px)",
        overflow: "auto",
        paddingBottom: "53px",
        position: "static"
      }
    };
  },
  methods: {
    hdlSingleModified(row) {
      getMemberDetail({ id: row.id }).then(res => {
        this.memberDetail = res.data;
        this.showList = false;
      });
    },
    hdlquery() {
      this.changePage(1);
    },
    changePage(pageIndex) {
      this.isLoading = true;
      let pageSize = this.pageSize;
      getMemberList({ pageIndex, pageSize })
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
    reset() {
      this.formItem.name = "";
      this.formItem.code = "";
    },
    hdlSelectionChange(selection) {
      this.tbSelection = selection;
    },
    hdlShowDrawer() {
      this.showDrawer = true;
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
