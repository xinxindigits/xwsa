<template>
  <Card>
    <Row :gutter="20" class="row-operation">
      <Col
        ><Button
          icon="md-add"
          type="primary"
          @click="hdlCreate"
          :disabled="!curTreeData.id"
          >新增</Button
        >
      </Col>
    </Row>
    <Table
      stripe
      border
      ref="tables"
      :data="currentData"
      :columns="columns"
      @on-selection-change="hdlSelectionChange"
    >
      <template slot-scope="{ row }" slot="action">
        <Button
          type="primary"
          size="small"
          style="margin-right: 5px"
          @click="hdlEdit(row)"
        >
          修改
        </Button>
        <Button
          type="error"
          size="small"
          @click="hdlDelete({ id: row.parentId, list: [row.id] })"
        >
          删除
        </Button>
      </template>
    </Table>
    <resource-func-modify
      ref="modifyModal"
      v-model="showModal"
      :type="modifyType"
      @resource-modified="hdlModified"
    ></resource-func-modify
  ></Card>
</template>

<script>
import { deleteResource } from "@/api";
import ResourceFuncModify from "./modify";
export default {
  name: "resource-operation",
  components: {
    ResourceFuncModify
  },
  props: {
    tableData: {
      type: Array
    },
    curTreeData: {
      type: Object,
      default: function() {
        return {
          id: ""
        };
      }
    }
  },
  data() {
    return {
      modifyType: "create",
      showModal: false,
      currentData: [],
      columns: [
        { title: "权限值", key: "authority", align: "center" },
        { title: "名称", key: "name", align: "center" },
        {
          title: "操作",
          slot: "action",
          width: 150,
          align: "center"
        }
      ]
    };
  },
  methods: {
    hdlSelectionChange() {},
    hdlEdit(data) {
      this.modifyType = "update";
      this.$refs.modifyModal.setData(data);
      this.showModal = true;
    },
    hdlCreate() {
      let self = this;
      this.modifyType = "create";
      this.$refs.modifyModal.setData({
        parentId: self.curTreeData.id,
        authority: "/",
        code: "",
        extension: "",
        name: "",
        resourceType: "function",
        url: "/"
      });
      this.showModal = true;
    },
    hdlDelete(data) {
      let self = this;
      if (data.list.length > 0) {
        this.$Modal.confirm({
          title: "确认删除？",
          content: `确定删除选中记录?`,
          onOk() {
            deleteResource({ id: data.list[0] }).then(() => {
              this.$Message.success("删除成功！");
              self.$emit("on-delete-resource", data);
            });
          }
        });
      } else {
        this.$Message.warning("请选择一条记录!");
      }
    },
    hdlModified(type) {
      this.$emit("resource-modified", type);
    }
  },
  watch: {
    tableData: {
      deep: true,
      immediate: true,
      handler(newValue) {
        this.currentData = newValue;
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
