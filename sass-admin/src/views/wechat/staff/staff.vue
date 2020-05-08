<template>
  <div>
    <Row :gutter="10">
      <Col span="24">
        <Card>
          <Form :model="formItem" inline label-colon>
            <FormItem>
              <Input v-model="formItem.memberName" placeholder="姓名"></Input>
            </FormItem>
            <FormItem>
              <Input v-model="formItem.mobile" placeholder="手机号"></Input>
            </FormItem>
            <FormItem>
              <Button type="primary" @click="hdlquery">查询</Button>
              <Button style="margin-left: 8px" @click="reset">重置</Button>
            </FormItem>
          </Form>
          <Table
            stripe
            border
            ref="tables"
            :data="tableData"
            :columns="columns"
            :loading="isLoading"
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
    <Drawer
      v-model="showDetail"
      :closable="false"
      width="80"
      scrollable
      transfer
    >
      <member-detail
        :items="memberDetail"
        @show-record="showRecord = true"
      ></member-detail>
    </Drawer>
    <Drawer title="会话管理" v-model="showRecord" width="100">
      <msg-record :user-id="cur_userId"></msg-record>
    </Drawer>
  </div>
</template>

<script>
import { getMemberList, getMemberDetail, queryMember } from "@/api";
import MemberDetail from "../common/member-detail/member-detail";
import MsgRecord from "@/components/msg-record/msg-record";
export default {
  name: "staff-list",
  components: {
    MemberDetail,
    MsgRecord
  },
  data() {
    return {
      showDetail: false,
      memberDetail: {},
      showRecord: false,
      cur_userId: "",

      formItem: {
        memberName: "",
        mobile: ""
      },

      columns: [
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
      tableData: []
    };
  },
  methods: {
    hdlSingleModified(row) {
      getMemberDetail({ id: row.id }).then(res => {
        this.memberDetail = res.data;
        this.showDetail = true;
        this.cur_userId = row.userId;
      });
    },
    hdlquery() {
      this.isLoading = true;
      let pageSize = this.pageSize;
      queryMember({ pageIndex: 1, pageSize, ...this.formItem })
        .then(res => {
          let { data } = res;
          this.tableData = data.items;
          this.total = Number(data.total);
        })
        .finally(() => (this.isLoading = false));
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
      this.formItem.memberName = "";
      this.formItem.mobile = "";
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
