<template>
  <div>
    <Card>
      <Row type="flex" :gutter="20" class="row-operation">
        <Col
          ><Button
            icon="md-add"
            type="primary"
            @click="hdlSingleCreate({ parentId: 0 })"
            >新增</Button
          >
        </Col>
      </Row>
      <Table
        row-key="id"
        stripe
        border
        ref="tables"
        :columns="columns"
        :data="tableData"
        :loading="isLoading"
      >
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
            @click="hdlDelete([row.id])"
          >
            删除
          </Button>
          <Button
            type="success"
            size="small"
            @click="hdlSingleCreate({ parentId: row.id })"
          >
            新增下级
          </Button>
        </template>
      </Table>
      <modify
        :type="modifyType"
        ref="modifyModal"
        v-model="showModifyModal"
        @on-cancel="showModifyModal = false"
        @auths-modified="hdlModified"
      ></modify>
    </Card>
  </div>
</template>

<script>
import { mngDelAuth, mngGetAuthsTree } from "@/api";
import { Modify } from "./components";
export default {
  name: "mng-auths",
  components: {
    Modify
  },
  data() {
    return {
      modifyType: "create",
      showModifyModal: false,
      isLoading: false,
      columns: [
        { title: "资源名称", key: "text", align: "left", tree: true },
        { title: "组件URI", key: "url", align: "center" },
        { title: "操作", slot: "action", align: "center", width: 225 }
      ],
      tableData: []
    };
  },
  methods: {
    hdlQuery() {
      this.isLoading = true;
      mngGetAuthsTree()
        .then(({ data }) => {
          this.tableData = data;
        })
        .finally(() => (this.isLoading = false));
    },
    hdlDelete(ids) {
      let self = this;
      if (ids.length > 0) {
        this.$Modal.confirm({
          title: "确认删除？",
          content: `确定删除选中记录?`,
          onOk() {
            mngDelAuth({ id: ids[0] }).then(() => {
              this.$Message.success("删除成功！");
              self.hdlQuery();
            });
          }
        });
      } else {
        this.$Message.warning("请选择一条记录!");
      }
    },
    hdlSingleCreate(row) {
      this.modifyType = "create";
      const _defData = {
        authority: "",
        name: "",
        code: "",
        resourceType: "",
        url: "",
        root: false,
        extension: "",
        id: "",
        parentId: 0
      };
      this.$refs.modifyModal.setData({ ..._defData, ...row });
      this.showModifyModal = true;
    },
    hdlSingleModified(row) {
      this.modifyType = "update";
      this.$refs.modifyModal.setData({
        ...row,
        resourceType: row.type,
        parentId: parseInt(row.parentId)
      });
      this.showModifyModal = true;
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
