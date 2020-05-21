<template>
  <div>
    <Row :gutter="10">
      <Col span="24">
        <Card>
          <Query
            ref="query"
            v-model="form"
            @on-customer-query="hdlQuery"
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
            <template slot-scope="{ row }" slot="createdTime">
              <span>{{ row.createdTime | timeFilter }}</span>
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

    <customer-detail
      v-model="showDetail"
      :tag-list="tagList"
      :items="curDetail"
      @show-record="hdlShowRecord"
      @tag-updated="changePage(page)"
      @on-cancel="
        showDetail = false;
        curDetail = {};
      "
    ></customer-detail>

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
  getAllTags
} from "@/api";
import MsgRecord from "@/components/msg-record/msg-record";
import { Query, CustomerDetail } from "./components";
export default {
  name: "wc-customer",
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
        .join("|");
    }
  },
  data() {
    return {
      showDetail: false,
      curDetail: {},
      cur_userId: "",
      showRecord: false,

      form: {
        startTime: "",
        endTime: "",
        memberUserIds: [],
        customerName: ""
      },

      columns: [
        { title: "id", key: "id", width: 80 },
        { title: "客户名称", key: "customerName" },
        { title: "企业名称", key: "corpName", ellipsis: true },
        { title: "添加时间", key: "createdTime", slot: "createdTime" },
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
      tableData: [],

      tagList: [],

      curQuery: {}
    };
  },
  methods: {
    init() {
      this.reset();
      getAllTags().then(({ data }) => {
        this.tagList = data;
      });
    },
    hdlQuery() {
      this.curQuery = this.form;
      this.changePage(1);
    },
    reset() {
      this.curQuery = {};
      this.changePage(1);
    },
    getTagList() {
      return new Promise(resolve => {
        getAllTags().then(({ data }) => {
          resolve(data);
        });
      });
    },
    async hdlDetailQuery(row) {
      if (this.tagList.length < 1) {
        this.tagList = await this.getTagList();
      }
      getCustomerDetail({ id: row.id }).then(({ data }) => {
        this.curDetail = data;
        this.cur_userId = row.userId;
        this.showDetail = true;
      });
    },
    changePage(pageIndex) {
      this.isLoading = true;
      let pageSize = this.pageSize;
      let api =
        Object.keys(this.curQuery).length == 0
          ? getCustomerList
          : queryCustomerList;
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
