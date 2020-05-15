<template>
  <div>
    <Drawer title="任务日志" :closable="false" v-model="showRecord" width="80">
      <Form :model="formObj" inline label-colon>
        <FormItem>
          <Select v-model="formObj.taskStatus" style="width: 250px">
            <Option v-for="item in statusEnum" :value="item[0]" :key="item[0]">
              {{ item[1] }}
            </Option>
          </Select>
        </FormItem>
        <FormItem>
          <DatePicker
            v-model="daterange"
            type="daterange"
            placement="bottom-start"
            split-panels
            confirm
            :options="rangeOption"
            placeholder="日期范围"
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
        :columns="columns"
        :data="tableData"
        :loading="isLoading"
      >
        <template slot-scope="{ row }" slot="taskType">
          <span>{{ $mapd("taskType", row.taskType) }}</span>
        </template>
        <template slot-scope="{ row }" slot="taskStatus">
          <span>{{ $mapd("taskResult", row.taskStatus) }}</span>
        </template>
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
    </Drawer>
    <task-detail v-model="showDetail" ref="detailModal"> </task-detail>
  </div>
</template>
<script>
import { queryTaskLog } from "@/api";
import { taskResult } from "@/libs/dic";
import TaskDetail from "./taskDetail";
export default {
  name: "task-log",
  props: {
    value: Boolean
  },
  components: {
    TaskDetail
  },
  data() {
    return {
      showDetail: false,
      isLoading: false,
      showRecord: false,
      statusEnum: Object.entries(taskResult),
      taskType: "",
      total: 0,
      page: 1,
      pageSize: 10,
      daterange: [],
      rangeOption: {
        disabledDate(date) {
          return date && date.valueOf() > Date.now();
        }
      },
      detail: "",
      formObj: {
        taskType: "",
        taskStatus: "",
        startTime: "",
        endTime: ""
      },
      columns: [
        { title: "任务流水号", key: "taskId", align: "center" },
        { title: "任务类型", slot: "taskType", align: "center" },
        {
          title: "租户id",
          key: "tenantId",
          align: "center"
        },
        { title: "任务时间", key: "taskTime", align: "center" },
        { title: "任务状态", slot: "taskStatus", align: "center" },
        { title: "错误原因(秒)", key: "errorDesc", align: "center" },
        { title: "操作", slot: "action", align: "center", width: 100 }
      ],
      tableData: []
    };
  },
  methods: {
    init(taskType) {
      this.taskType = taskType;
      this.formObj.taskType = taskType;
      this.hdlquery();
    },
    hdlquery() {
      this.changePage(1);
    },
    changePage(pageNum) {
      this.isLoading = true;
      let pageSize = this.pageSize;
      let pageIndex = pageNum;

      queryTaskLog({ pageIndex, pageSize, ...this.formObj })
        .then(res => {
          let { data } = res;
          //this.reset();
          this.tableData = data.items;
          this.total = Number(data.total);
        })
        .finally(() => (this.isLoading = false));
    },
    changePageSize(pageSize) {
      this.pageSize = pageSize;
      this.changePage(1);
    },
    hdlSingleModified(data) {
      this.$refs.detailModal.init(data);
      this.showDetail = true;
    },
    reset() {
      this.daterange = [];
      this.formObj.taskStatus = "";
      this.formObj.startTime = "";
      this.formObj.endTime = "";
      this.hdlquery();
    }
  },
  watch: {
    value(newValue) {
      this.showRecord = newValue;
    },
    showRecord(newValue) {
      this.$emit("input", newValue);
    },
    daterange(newValue) {
      if (
        newValue.length === 2 &&
        newValue[0] instanceof Date &&
        newValue[0] instanceof Date
      ) {
        this.formObj.startTime = newValue[0].getTime() + "";
        this.formObj.endTime =  newValue[1].getTime() + 24 * 60 * 60 * 1000 - 1000 + "";
      } else {
        this.formObj.startTime = "";
        this.formObj.endTime = "";
      }
    }
  }
};
</script>

<style scoped></style>
