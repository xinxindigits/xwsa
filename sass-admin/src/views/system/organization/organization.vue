<template>
  <div>
    <Card>
      <Form :model="form" inline label-colon>
        <FormItem>
          <Input v-model="form.tenantId" placeholder="租户编码"></Input>
        </FormItem>
        <FormItem>
          <Input v-model="form.orgId" placeholder="机构编码"></Input>
        </FormItem>
        <FormItem>
          <Input v-model="form.orgName" placeholder="机构名称"></Input>
        </FormItem>
        <FormItem>
          <Button type="primary" @click="hdlQuery">查询</Button>
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
        row-key="orgId"
        stripe
        border
        ref="tables"
        :columns="columns"
        :data="tableData"
        :loading="isLoading"
        @on-selection-change="hdlSelectionChange"
      >
        <template slot-scope="{ row }" slot="status">
          <span>{{ $mapd("organizationState", row.status) }}</span>
        </template>
        <template slot-scope="{ row }" slot="create_time">
          <span>{{ row.gmtCreated | timeFilter }}</span>
        </template>
        <template slot-scope="{ row }" slot="action">
          <Button
            type="primary"
            size="small"
            style="margin-right: 5px"
            @click="hdlSingleModified(row)"
          >
            修改
          </Button>
          <Button
            type="error"
            size="small"
            style="margin-right: 5px"
            @click="hdlDelete([row.orgId])"
          >
            删除
          </Button>
          <Button
            type="success"
            size="small"
            @click="hdlSingleCreateChild(row)"
          >
            新增下级
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
      <organization-modify
        :type="modifyType"
        ref="modifyModal"
        v-model="showModifyModal"
        @on-cancel="showModifyModal = false"
        @organization-modified="hdlModified"
      ></organization-modify>
    </Card>
  </div>
</template>

<script>
import { getOrganizationList, delOrganization } from "@/api";
import { OrganizationModify } from "./components";
export default {
  name: "sys-organization",
  components: {
    OrganizationModify
  },
  computed: {
    deleteOrgCodes() {
      return this.tbSelection.map(item => item.orgId);
    }
  },
  data() {
    return {
      modifyType: "create",
      showModifyModal: false,
      isLoading: false,
      pageSize: 10,
      total: 0,
      page: 1,
      form: {
        orgName: "",
        orgId: "",
        tenantId: "",
        orgType: "",
        state: ""
      },
      columns: [
        {
          type: "selection",
          width: 60,
          align: "center"
        },
        { title: "机构编码", key: "code", align: "left", tree: true },
        { title: "机构名称", key: "orgName", align: "center" },
        { title: "机构类型", key: "orgTypeName", align: "center" },
        { title: "租户编码", key: "tenantId", align: "center" },
        {
          title: "创建时间",
          key: "gmtCreated",
          align: "center",
          slot: "create_time"
        },
        { title: "操作", slot: "action", align: "center", width: 225 }
      ],
      tableData: [],
      tbSelection: []
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
      getOrganizationList({ pageIndex, pageSize, ...this.form })
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
    reset() {
      this.form.orgName = "";
      this.form.orgId = "";
      this.form.orgType = "";
      this.form.tenantId = "";
      this.form.status = "";
      this.hdlQuery();
    },
    hdlDelete(ids) {
      let self = this;
      if (ids.length > 0) {
        this.$Modal.confirm({
          title: "确认删除？",
          content: `确定删除选中记录?`,
          onOk() {
            delOrganization({ ids }).then(() => {
              this.$Message.success("删除成功！");
              self.hdlQuery();
            });
          }
        });
      } else {
        this.$Message.warning("请选择一条记录!");
      }
    },
    hdlSingleCreate() {
      this.modifyType = "create";
      let d = {
        no_edit_parentName: true,
        parentName: "0",
        parentId: "0"
      };
      this.$refs.modifyModal.setData(d);
      this.showModifyModal = true;
    },
    hdlSingleCreateChild(data) {
      this.modifyType = "create";
      let d = {
        parentName: data.orgName,
        parentId: data.orgId,
        no_edit_parentName: true
      };
      this.$refs.modifyModal.setData(d);
      this.showModifyModal = true;
    },
    hdlSingleModified(data) {
      this.modifyType = "update";
      let d = {
        orgType: data.orgType,
        code: data.code,
        parentName: data.parentName,
        remark: data.remark,
        name: data.orgName,
        no_edit_parentName: true
      };
      this.$refs.modifyModal.setData(d);
      this.showModifyModal = true;
    },
    hdlSelectionChange(selection) {
      this.tbSelection = selection;
    },
    hdlModified(type) {
      this.$Message.success(`${type == "create" ? "新增" : "修改"}成功！`);
      this.hdlQuery();
    }
  },
  mounted() {
    this.hdlQuery();
  }
};
</script>

<style lang="less" scoped>
.row-operation {
  padding: 10px 0;
}
</style>
