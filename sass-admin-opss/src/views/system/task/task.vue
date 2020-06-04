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
        <template slot-scope="{ row }" slot="status">
          <span>{{ $mapd("taskState", row.status) }}</span>
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
      <task-modify
        :type="modifyType"
        v-model="showModifyModal"
        @task-modified="hdlModified"
        ref="modifyModal"
      ></task-modify>
      <task-log v-model="showRecord" ref="record"></task-log>
    </Card>
  </div>
</template>

<script>
import { queryTenantConfig, executeJob } from "@/api";
import { TaskModify, TaskLog } from "./components";
export default {
  name: "sys-task",
  components: {
    TaskModify,
    TaskLog
  },
  computed: {},
  data() {
    return {
      modifyType: "create",
      showModifyModal: false,
      showRecord: false,
      isLoading: false,
      pageSize: 10,
      total: 0,
      page: 1,
      form: {
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
          key: "status",
          align: "center",
          slot: "status"
        },
        { title: "操作", slot: "action", align: "center", width: 250 }
      ],
      tableData: []
    };
  },
  methods: {
    hdlQuery() {
      this.changePage(1);
    },
    changePage(pageNum) {
      this.isLoading = true;
      let pageSize = this.pageSize;
      let pageIndex = pageNum;
      queryTenantConfig({ pageIndex, pageSize, ...this.form })
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
      this.form.tenantName = "";
      this.form.tenantId = "";
      this.form.state = "";
    },
    hdlSingleCreate() {
      this.modifyType = "create";
      let d = {
        tenantId: "",
        countCeiling: 1000,
        timeInterval: 3
      };
      this.$refs.modifyModal.setData(d);
      this.showModifyModal = true;
    },
    hdlSingleModified(data) {
      this.modifyType = "update";
      let d = {
        id: data.id,
        tenantId: data.tenantId,
        taskType: data.taskType,
        cronExpression: data.cronExpression,
        countCeiling: data.countCeiling,
        timeInterval: data.timeInterval,
        status: data.status
      };
      this.$refs.modifyModal.setData(d);
      this.showModifyModal = true;
    },
    hdlSingleExecuteJob(taskType) {
      executeJob({ taskType }).then(() => {
        this.$Message.success("执行成功！");
      });
      this.hdlQuery();
    },
    hdlShowRecord(taskType) {
      this.$refs.record.init(taskType);
      this.showRecord = true;
    },
    hdlModified(type) {
      this.$Message.success(`${type == "create" ? "新增" : "修改"}成功！`);
      this.hdlQuery();
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
