<template>
  <Row>
    <Col span="24">
      <Card>
        <msg-query
          ref="query"
          v-model="formItem"
          @on-wc-msg-query="changePage(1)"
        ></msg-query>
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
      </Card>
    </Col>
  </Row>
</template>

<script>
import { queryMsgList, getMsgByMsgId } from "@/api";
import { MsgQuery } from "./components";
export default {
  name: "message-list",
  components: {
    MsgQuery
  },
  data() {
    return {
      isLoading: false,
      pageSize: 10,
      total: 0,
      page: 1,
      formItem: {
        tenantId: "xinxin",
        userId: "",
        startTime: "",
        endTime: ""
      },
      tableData: [],
      columns: [
        { title: "消息id", key: "msgId", align: "center" },
        { title: "发送方", key: "fromUserId", align: "center" },
        { title: "发送时间", key: "msgTime", align: "center", slot: "msgTime" },
        { title: "类型", key: "msgType", align: "center", slot: "msgType" },
        {
          title: "操作类型",
          key: "action",
          align: "center",
          slot: "msgAction"
        },
        {
          title: "操作",
          slot: "action",
          width: 200,
          align: "center"
        }
      ],
      tbSelection: []
    };
  },
  methods: {
    changePage(pageIndex) {
      this.isLoading = true;
      let pageSize = this.pageSize;
      queryMsgList({ pageIndex, pageSize, ...this.formItem })
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
    hdlSelectionChange(selection) {
      this.tbSelection = selection;
    },

    hdlQueryInfo({ msgId }) {
      getMsgByMsgId({ msgId });
    }
  },
  mounted() {
    this.changePage(1);
  }
};
</script>
