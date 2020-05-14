<template>
  <div>
    <Card>
      <Form :model="formItem" inline label-colon>
        <FormItem>
          <Input v-model="formItem.tenantId" placeholder="租户编码"></Input>
        </FormItem>
        <FormItem>
          <Input v-model="formItem.tenantName" placeholder="租户名称"></Input>
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
            @click="hdlDelete(deleteOrgCodes)"
            >删除</Button
          ></Col
        >
      </Row>
      <Table
        stripe
        border
        ref="tables"
        :columns="columns"
        :data="tableData"
        :loading="isLoading"
        @on-selection-change="hdlSelectionChange"
      >
        <template slot-scope="{ row }" slot="taskType">
          <span>{{ $mapd("taskType", row.taskType) }}</span>
        </template>
        <template slot-scope="{ row }" slot="deleted">
          <span>{{ $mapd("taskState", row.deleted) }}</span>
        </template>
        <template slot-scope="{ row }" slot="action">
          <Button
            type="primary"
            size="small"
            style="margin-right: 5px"
            @click="hdlSingleModified(row)"
          >
            更新
          </Button>
          <Button
            type="error"
            size="small"
            style="margin-right: 5px"
            @click="hdlDelete([row.tenantId])"
          >
            删除
          </Button>
          <Button
            type="info"
            size="small"
            style="margin-right: 5px"
            @click="hdlDelete([row.tenantId])"
          >
            日志
          </Button>
          <Button
            type="warning"
            size="small"
            @click="hdlDelete([row.tenantId])"
          >
            触发
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
      <organization-update
        type="create"
        ref="createModal"
        v-model="showAddModal"
        @on-cancel="showAddModal = false"
        @on-add-organization="hdlquery"
      ></organization-update>
      <organization-update
        type="update"
        v-model="showUpdateModal"
        @on-update-organization="hdlquery"
        ref="updateModal"
      ></organization-update>
    </Card>
  </div>
</template>

<script>
import { delTenant, queryTenantConfig } from "@/api";
import OrganizationUpdate from "./modify";
export default {
  name: "organization",
  props: {
    value: Boolean,
    type: {
      validator: function(value) {
        return ["create", "update"].indexOf(value) !== -1;
      }
    }
  },
  components: {
    OrganizationUpdate
  },
  computed: {
    deleteOrgCodes() {
      return this.tbSelection.map(item => item.tenantId);
    }
  },
  data() {
    return {
      showTaskModal: false,
      showAddModal: false,
      showGrantModal: false,
      showUpdateModal: false,
      isLoading: false,
      pageSize: 10,
      total: 0,
      page: 1,
      formItem: {
        tenantId: "",
        tenantName: "",
        state: ""
      },
      columns: [
        {
          type: "selection",
          width: 60,
          align: "center"
        },
        { title: "租户编码", key: "tenantId", align: "center" },
        { title: "任务类型", slot: "taskType", align: "center" },
        {
          title: "cron表达式",
          width: 180,
          key: "cronExpression",
          align: "center"
        },
        { title: "当前消息序号", key: "fetchedSeqNo", align: "center" },
        { title: "会话每次提取上限", key: "countCeiling", align: "center" },
        { title: "会话每次提取间隔(秒)", key: "timeInterval", align: "center" },
        {
          title: "状态",
          key: "deleted",
          align: "center",
          slot: "deleted"
        },
        { title: "操作", slot: "action", align: "center", width: 250 }
      ],
      tableData: [],
      tbSelection: []
    };
  },
  methods: {
    hdlquery() {
      this.changePage(1);
    },
    changePage(pageNum) {
      this.isLoading = true;
      let pageSize = this.pageSize;
      let pageIndex = pageNum;
      queryTenantConfig({ pageIndex, pageSize, ...this.formItem })
        .then(res => {
          let { data } = res;
          this.reset();
          this.tableData = data;
          this.total = Number(data.total);
        })
        .finally(() => (this.isLoading = false));
    },
    changePageSize(pageSize) {
      this.pageSize = pageSize;
      this.changePage(1);
    },
    reset() {
      this.formItem.tenantName = "";
      this.formItem.tenantId = "";
      this.formItem.state = "";
    },
    hdlDelete(codes) {
      let self = this;
      if (codes.length > 0) {
        this.$Modal.confirm({
          title: "确认删除？",
          content: `确定删除选中记录?`,
          onOk() {
            delTenant({ codes }).then(() => {
              this.$Message.success("删除成功！");
              self.hdlquery();
            });
          }
        });
      } else {
        this.$Message.warning("请选择一条记录!");
      }
    },
    hdlSingleCreate() {
      let d = {
        tenantId: ""
      };
      this.$refs.createModal.setData(d);
      this.showAddModal = true;
    },
    hdlSingleModified(data) {
      let d = {
        tenantId: data.tenantId
      };
      this.$refs.updateModal.setData(d);
      this.showUpdateModal = true;
    },
    hdlSelectionChange(selection) {
      this.tbSelection = selection;
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
