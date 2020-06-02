<template>
  <Drawer title="详情" v-model="showDetail" width="50">
    <CellGroup>
      <Cell>
        <span>id：{{ detail.id }}</span>
      </Cell>
      <Cell>
        <span>任务编码：{{ detail.taskId }}</span>
      </Cell>
      <Cell>
        <span>租户编码：{{ detail.tenantId }}</span>
      </Cell>
      <Cell>
        <span>任务类型：{{ $mapd("taskType", detail.taskType) }}</span>
      </Cell>
      <Cell>
        <span>任务时间：{{ detail.taskTime }}</span>
      </Cell>
      <Cell>
        <span>任务状态：{{ $mapd("taskResult", detail.taskStatus) }}</span>
      </Cell>
      <Cell>
        <span>错误码：{{ detail.errorCode }}</span>
      </Cell>

      <p style="margin-left: 12pt">错误原因：{{ detail.errorDesc }}</p>

      <Divider dashed></Divider>
      <div v-if="detail.taskType == this.MESSAGE_SYNC">
        <Cell>
          <span>此次同步消息数量：{{ detail.messageCount }}</span>
        </Cell>
      </div>
      <div v-if="detail.taskType == this.CONTACT_SYNC">
        <Cell>
          <span>此次同步部门数量：{{ detail.departmentCount }}</span>
        </Cell>
        <Cell>
          <span>此次同步成员数量：{{ detail.memberCount }}</span>
        </Cell>
        <Cell>
          <span>客户数量：{{ detail.customerCount }}</span>
        </Cell>
      </div>
    </CellGroup>
  </Drawer>
</template>

<script>
export default {
  name: "task-detail",
  props: {
    value: Boolean
  },
  data() {
    return {
      showDetail: false,
      MESSAGE_SYNC: "MESSAGE_SYNC",
      CONTACT_SYNC: "CONTACT_SYNC",
      pStyle: {
        fontSize: "16px",
        color: "rgba(0,0,0,0.85)",
        lineHeight: "24px",
        display: "block",
        marginBottom: "16px"
      },
      detail: ""
    };
  },
  methods: {
    init(data) {
      this.detail = data;
    }
  },
  watch: {
    value(newValue) {
      this.showDetail = newValue;
    },
    showDetail(newValue) {
      this.$emit("input", newValue);
    }
  }
};
</script>
.demo-drawer-profile{ font-size: 14px; } .demo-drawer-profile .ivu-col{
margin-bottom: 12px; }
<style scoped></style>
