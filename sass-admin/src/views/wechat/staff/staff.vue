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
              <Button type="primary" @click="hdlQuery">查询</Button>
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
      title="员工详情"
      width="80"
      scrollable
      transfer
    >
      <staff-detail
        :items="memberDetail"
        @show-record="hdlShowRecord"
      ></staff-detail>
    </Drawer>
    <msg-record
      v-model="showRecord"
      :user-id="cur_userId"
      ref="record"
    ></msg-record>
  </div>
</template>

<script>
import { getMemberList, getMemberDetail, queryMember } from "@/api";
import { StaffDetail } from "./components";
import MsgRecord from "@/components/msg-record/msg-record";
export default {
  name: "staff-list",
  components: {
    StaffDetail,
    MsgRecord
  },
  data() {
    return {
      formItem: {
        memberName: "",
        mobile: ""
      },
      curQuery: {},

      showDetail: false,
      memberDetail: {},
      showRecord: false,
      cur_userId: "",

      tableData: [],
      columns: [
        { title: "id", key: "id", width: 80 },
        { title: "姓名", key: "memberName" },
        { title: "账号", key: "userId", ellipsis: true },
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
      }
    };
  },
  methods: {
    hdlQuery() {
      this.curQuery = this.formItem;
      this.changePage(1);
    },
    reset() {
      this.curQuery = {};
      this.formItem.memberName = "";
      this.formItem.mobile = "";
      this.changePage(1);
    },
    hdlShowRecord(userId) {
      this.$refs.record.init(userId);
      this.showRecord = true;
    },

    hdlSingleModified(row) {
      getMemberDetail({ id: row.id }).then(res => {
        this.memberDetail = res.data;
        this.showDetail = true;
        this.cur_userId = row.userId;
      });
    },

    changePage(pageIndex = 1) {
      this.isLoading = true;
      let pageSize = this.pageSize;
      let api =
        Object.keys(this.curQuery).length == 0 ? getMemberList : queryMember;
      api({ pageIndex, pageSize, ...this.curQuery })
        .then(res => {
          let { data } = res;
          this.page = pageIndex;
          this.tableData = data.items;
          this.total = Number(data.total);
        })
        .finally(() => (this.isLoading = false));
    },
    changePageSize(pageSize) {
      this.pageSize = pageSize;
      this.changePage(1);
    }
  },
  mounted() {
    this.reset();
  }
};
</script>

<style lang="less" scoped>
.row-operation {
  padding: 10px 0;
}
</style>
