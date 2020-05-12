<template>
  <div>
    <Card>
      <query
        ref="query"
        v-model="formItem"
        @on-log-query="changePage(1)"
        @on-log-query-reset="changePage(1, true)"
      ></query>
      <Table
        stripe
        border
        ref="tables"
        :data="tableData"
        :columns="columns"
        :loading="isLoading"
      >
        <template slot-scope="{ row }" slot="gmtCreated">
          <span>{{ row.gmtCreated | timeFilter }}</span>
        </template>
        <template slot-scope="{ row }" slot="action">
          <Button
            type="primary"
            size="small"
            style="margin-right: 5px"
            @click="getDetail(row)"
          >
            详情
          </Button>
        </template>
      </Table>
      <div style="margin: auto; text-align: right;padding-top:10px">
        <Page
          :total="total"
          :current="page"
          :page-size="pageSize"
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
    <Drawer
      title="日志详情"
      width="40"
      v-model="showDetail"
      scrollable
      transfer
    >
      <detail :items="current_log"></detail>
    </Drawer>
  </div>
</template>

<script>
import { getLogList, queryLogList } from "@/api";
import { Query, Detail } from "./components";
export default {
  name: "sys-log",
  components: {
    Query,
    Detail
  },
  data() {
    return {
      showDetail: false,

      isLoading: false,
      pageSize: 10,
      total: 0,
      page: 1,
      formItem: {
        account: ""
      },
      tableData: [],
      columns: [
        { title: "id", key: "id", align: "center", width: "80" },
        { title: "账号", key: "account", align: "center" },
        { title: "IP", key: "ip", align: "center" },
        { title: "操作", key: "operation", align: "center" },
        {
          title: "创建时间",
          key: "gmtCreated",
          align: "center",
          slot: "gmtCreated"
        },

        {
          title: "操作",
          slot: "action",
          width: 150,
          align: "center"
        }
      ],
      tbSelection: [],
      current_log: {}
    };
  },
  methods: {
    hdlqueryAfterReset() {
      this.$refs.query.reset();
      this.changePage(1);
    },
    changePage(pageIndex = 1, isInit) {
      this.isLoading = true;
      let pageSize = this.pageSize;
      let api = isInit ? getLogList : queryLogList;
      api({ pageIndex, pageSize, ...this.formItem })
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
    getDetail(n) {
      this.current_log = n;
      this.showDetail = true;
    }
  },
  mounted() {
    this.changePage(1, true);
  }
};
</script>

<style lang="less" scoped>
.row-operation {
  padding: 10px 0;
}
</style>
