<template>
  <div>
    <Card>
      <Row type="flex" :gutter="20" class="row-operation">
        <Col
          ><Button icon="md-add" type="primary" @click="hdlCreate">新增</Button>
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
        <template slot-scope="{ row }" slot="state">
          <span>{{ $mapd("organizationState", row.state) }}</span>
        </template>
        <template slot-scope="{ row }" slot="create_time">
          <span>{{ row.gmtCreated | timeFilter }}</span>
        </template>
        <template slot-scope="{ row }" slot="action">
          <Button
            type="primary"
            size="small"
            style="margin-right: 5px"
            @click="hdlClick(row)"
          >
            管理
          </Button>
          <Button
            type="warning"
            size="small"
            style="margin-right: 5px"
            @click="hdlDelete(row)"
          >
            删除
          </Button>
        </template>
      </Table>
      <tenant-modify
        type="create"
        ref="modifyModal"
        v-model="showModifyModal"
        @on-cancel="showModifyModal = false"
        @tenant-modified="hdlModified"
      ></tenant-modify>
    </Card>
  </div>
</template>

<script>
import { mngGetTenantRoutes } from "@/api";
import { tenantModify } from "@/views/system/tenant/components";
export default {
  name: "mng-home",
  components: {
    tenantModify
  },
  data() {
    return {
      showModifyModal: false,
      isLoading: false,
      form: {
        tenantId: "",
        tenantName: "",
        state: ""
      },
      columns: [
        { title: "租户编码", key: "tenantId", align: "center" },
        { title: "租户名称", key: "tenantName", align: "center" },
        { title: "备注", key: "remark", align: "center" },
        { title: "状态", slot: "state", align: "center" },
        {
          title: "创建时间",
          key: "gmtCreated",
          align: "center",
          slot: "create_time"
        },
        { title: "操作", slot: "action", align: "center", width: 150 }
      ],
      tableData: []
    };
  },
  methods: {
    hdlCreate() {
      this.$refs.modifyModal.setData({
        obj: {},
        remark: "",
        state: ""
      });
      this.showModifyModal = true;
    },
    hdlModified(type) {
      this.$Message.success(`${type == "create" ? "新增" : "修改"}成功！`);
      this.init();
    },
    hdlDelete() {},
    hdlClick(row) {
      this.$store.commit(
        "setTagNavList",
        this.$store.state.app.tagNavList.filter(
          item => item.name === this.$config.homeName
        )
      );
      this.$store.dispatch("upateXtenant", row.tenantId);
    },
    init() {
      this.isLoading = true;
      mngGetTenantRoutes()
        .then(({ data }) => {
          this.tableData = data;
        })
        .finally(() => (this.isLoading = false));
    }
  },
  mounted() {
    this.init();
  }
};
</script>

<style lang="less" scoped>
.row-operation {
  padding: 10px 0;
}
</style>
