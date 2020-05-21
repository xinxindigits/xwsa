<template>
  <Row>
    <Col span="24">
      <Card>
        <query
          ref="query"
          v-model="form"
          @on-wc-msg-query="hdlQuery"
          @on-wc-msg-reset="reset"
        ></query>
        <Table
          stripe
          border
          ref="tables"
          :data="tableData"
          :columns="columns"
          :loading="isLoading"
          @on-selection-change="hdlSelectionChange"
        >
          <template slot-scope="{ row }" slot="msgType">
            <span>{{ $mapd("msgType", row.msgType) }}</span>
          </template>
          <template slot-scope="{ row }" slot="msgTime">
            <span>{{ row.msgTime | timeFilter }}</span>
          </template>
          <template slot-scope="{ row }" slot="msgAction">
            <span>{{ $mapd("msgAction", row.action) }}</span>
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

        <msg-detail v-model="showDetail" :items="curDetail"></msg-detail>
      </Card>
    </Col>
  </Row>
</template>

<script>
import { queryMsgList, getMsgByMsgId } from "@/api";
import { Query, MsgDetail } from "./components";
export default {
  name: "wc-message",
  components: {
    Query,
    MsgDetail
  },
  data() {
    return {
      isLoading: false,
      pageSize: 10,
      total: 0,
      page: 1,
      form: {
        userId: "",
        startTime: "",
        endTime: ""
      },
      tableData: [],
      columns: [
        { title: "发送时间", key: "msgTime", align: "center", slot: "msgTime" },
        { title: "发送方", key: "fromUserName", align: "center" },
        {
          title: "接收方",
          key: "toChatPartyName",
          align: "center",
          ellipsis: true
        },
        {
          title: "类型",
          key: "msgType",
          align: "center",
          slot: "msgType",
          width: 150
        },
        {
          title: "操作类型",
          key: "action",
          align: "center",
          slot: "msgAction",
          width: 100
        },
        {
          title: "内容",
          key: "content",
          align: "center",
          width: 150,
          ellipsis: true
        },
        {
          title: "操作",
          slot: "action",
          width: 100,
          align: "center"
        }
      ],
      tbSelection: [],

      showDetail: false,
      curDetail: {},

      curQuery: {}
    };
  },
  methods: {
    hdlQuery() {
      this.curQuery = this.form;
      this.changePage(1);
    },
    reset() {
      this.curQuery = {};
      this.changePage(1);
    },
    changePage(pageIndex) {
      this.isLoading = true;
      let pageSize = this.pageSize;
      queryMsgList({ pageIndex, pageSize, ...this.curQuery })
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
    hdlSelectionChange(selection) {
      this.tbSelection = selection;
    },

    hdlQueryInfo({ id }) {
      getMsgByMsgId({ id }).then(({ data }) => {
        this.curDetail = data;
        this.showDetail = true;
      });
    }
  },
  mounted() {
    this.reset();
  }
};
</script>
