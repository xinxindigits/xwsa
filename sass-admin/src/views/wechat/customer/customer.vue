<template>
  <div>
    <Row :gutter="10" v-show="!showDetail">
      <Col span="24">
        <Card>
          <Form :model="formItem" inline label-colon>
            <FormItem>
              <Input v-model="formItem.customerName" placeholder="姓名"></Input>
            </FormItem>
            <FormItem>
              <DatePicker
                v-model="daterange"
                type="daterange"
                placement="bottom-start"
                split-panels
                confirm
                :options="rangeOption"
                placeholder="选择日期"
                style="width: 200px"
              ></DatePicker>
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
    <Row v-show="showDetail">
      <Col span="24">
        <customer-detail
          :items="curDetail"
          @on-cancel="
            showDetail = false;
            curDetail = {};
          "
        ></customer-detail>
      </Col>
    </Row>
  </div>
</template>

<script>
import { getCustomerList, queryCustomerList, getCustomerDetail } from "@/api";
import CustomerDetail from "../common/customer-detail/customer-detail";
export default {
  name: "staff-list",
  components: {
    CustomerDetail
  },
  data() {
    return {
      showDetail: false,
      curDetail: {},

      formItem: {
        orgId: "xinxin",
        startTime: "",
        endTime: "",
        memberUserIds: [],
        customerName: ""
      },
      daterange: [],
      rangeOption: {
        disabledDate(date) {
          return date && date.valueOf() > Date.now();
        }
      },

      columns: [
        { title: "id", key: "id", width: 80 },
        { title: "客户名称", key: "customerName" },
        { title: "企业名称", key: "corpName", ellipsis: true },
        { title: "添加时间", key: "mobile" },
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
      console.log(row);
      getCustomerDetail({ id: row.id }).then(({ data }) => {
        this.curDetail = data;
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
      this.formItem.customerName = "";
      this.daterange = [];
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
    }
  },
  mounted() {
    this.init();
  },
  watch: {
    daterange(newValue) {
      if (
        newValue.length === 2 &&
        newValue[0] instanceof Date &&
        newValue[0] instanceof Date
      ) {
        this.formItem.startTime = newValue[0].getTime() + "";
        this.formItem.endTime = newValue[1].getTime() + "";
      } else {
        this.formItem.startTime = "";
        this.formItem.endTime = "";
      }
    }
  }
};
</script>

<style lang="less" scoped>
.row-operation {
  padding: 10px 0;
}
</style>
