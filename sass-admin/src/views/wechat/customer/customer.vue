<template>
  <div>
    <Row :gutter="10">
      <Col span="24">
        <Card>
          <Query
            ref="query"
            v-model="formItem"
            @on-customer-query="hdlquery"
            @on-customer-reset="reset"
          ></Query>
          <Table
            stripe
            border
            ref="tables"
            :data="tableData"
            :columns="columns"
            :loading="isLoading"
          >
            <template slot-scope="{ row }" slot="tags">
              <span>{{ row.tags | tags }}</span>
            </template>
            <template slot-scope="{ row }" slot="action">
              <Button
                type="primary"
                size="small"
                style="margin-right: 5px"
                @click="hdlDetailQuery(row)"
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
      title="客户详情"
      width="80"
      v-model="showDetail"
      scrollable
      transfer
    >
      <customer-detail
        :items="curDetail"
        @show-record="hdlShowRecord"
        @on-cancel="
          showDetail = false;
          curDetail = {};
        "
      ></customer-detail>
    </Drawer>
    <msg-record
      ref="record"
      v-model="showRecord"
      :user-id="cur_userId"
    ></msg-record>
  </div>
</template>

<script>
import {
  getCustomerList,
  queryCustomerList,
  getCustomerDetail,
  queryTagList
} from "@/api";
import CustomerDetail from "../common/customer-detail/customer-detail";
import MsgRecord from "@/components/msg-record/msg-record";
import Query from "./components/query";
export default {
  name: "staff-list",
  components: {
    Query,
    CustomerDetail,
    MsgRecord
  },
  filters: {
    tags(arr) {
      return arr
        .map(n => {
          return n.name;
        })
        .join("、");
    }
  },
  data() {
    return {
      showDetail: false,
      curDetail: {},
      cur_userId: "",
      showRecord: false,

      formItem: {
        startTime: "",
        endTime: "",
        memberUserIds: [],
        customerName: ""
      },

      columns: [
        { title: "id", key: "id", width: 80 },
        { title: "客户名称", key: "customerName" },
        { title: "企业名称", key: "corpName", ellipsis: true },
        { title: "添加时间", key: "mobile" },
        { title: "标签", key: "tags", align: "center", slot: "tags" },
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
      tableData: []
    };
  },
  methods: {
    hdlquery() {
      this.changePage(1);
    },
    hdlDetailQuery(row) {
      getCustomerDetail({ id: row.id }).then(({ data }) => {
        this.curDetail = data;
        this.cur_userId = row.userId;
        this.showDetail = true;
      });
    },
    changePage(pageIndex) {
      this.isLoading = true;
      let pageSize = this.pageSize;
      queryCustomerList({ pageIndex, pageSize, ...this.formItem })
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
      this.hdlquery();
    },
    init() {
      this.isLoading = true;
      let pageSize = this.pageSize;
      getCustomerList({ pageIndex: 1, pageSize })
        .then(res => {
          let { data } = res;
          this.tableData = data.items;
          this.total = Number(data.total);
        })
        .finally(() => (this.isLoading = false));
    },
    getAllTags() {
      queryTagList();
    },
    hdlShowRecord(userId) {
      this.$refs.record.init(userId);
      this.showRecord = true;
    }
  },
  mounted() {
    this.init();
  }
};
</script>

<style lang="less" scoped>
.row-operation {
  padding: 10px 0;
}
</style>
