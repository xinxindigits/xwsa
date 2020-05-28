<template>
  <div>
    <Card>
      <Row type="flex" :gutter="20" class="row-operation">
        <Col
          ><Button icon="md-add" type="primary" @click="hdlSingleCreate"
            >新增</Button
          >
        </Col>
      </Row>
      <Table
        stripe
        border
        ref="tables"
        :columns="columns"
        :data="tableData"
        :loading="isLoading"
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
            type="info"
            size="small"
            style="margin-right: 5px"
            @click="hdlShowRecord(row.taskType)"
          >
            日志
          </Button>
          <Button
            type="warning"
            size="small"
            @click="hdlSingleExecuteJob(row.taskType)"
          >
            触发
          </Button>
        </template>
      </Table>
      <task-update
        type="create"
        ref="createModal"
        v-model="showAddModal"
        @on-cancel="showAddModal = false"
        @on-add-task="hdlquery"
      ></task-update>
      <task-update
        type="update"
        v-model="showUpdateModal"
        @on-update-task="hdlquery"
        ref="updateModal"
      ></task-update>
      <task-log v-model="showRecord" ref="record"></task-log>
    </Card>
  </div>
</template>

<script>
import { queryTenantConfig, executeJob } from "@/api";
import TaskUpdate from "./modify";
import TaskLog from "./taskLog";
export default {
  name: "task",
  props: {
    value: Boolean,
    type: {
      validator: function(value) {
        return ["create", "update"].indexOf(value) !== -1;
      }
    }
  },
  components: {
    TaskUpdate,
    TaskLog
  },
  computed: {},
  data() {
    return {
      showRecord: false,
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
      tableData: []
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
    hdlSingleCreate() {
      let d = {
        tenantId: "",
        countCeiling: 1000,
        timeInterval: 3
      };
      this.$refs.createModal.setData(d);
      this.showAddModal = true;
    },
    hdlSingleModified(data) {
      let d = {
        id: data.id,
        tenantId: data.tenantId,
        taskType: data.taskType,
        cronExpression: data.cronExpression,
        countCeiling: data.countCeiling,
        timeInterval: data.timeInterval,
        deleted: data.deleted
      };
      this.$refs.updateModal.setData(d);
      this.showUpdateModal = true;
    },
    hdlSingleExecuteJob(taskType) {
      executeJob({ taskType }).then(() => {
        this.$Message.success("执行成功！");
      });
      this.hdlquery();
    },
    hdlShowRecord(taskType) {
      this.$refs.record.init(taskType);
      this.showRecord = true;
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
