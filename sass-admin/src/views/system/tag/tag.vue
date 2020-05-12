<template>
  <div>
    <Card>
      <query
        ref="query"
        v-model="formItem"
        @on-tag-query="changePage(1)"
        @on-tag-reset="changePage(1)"
      ></query>
      <operation @on-tag-create="hdlSingleModified('create')"></operation>
      <Table
        stripe
        border
        ref="tables"
        :data="tableData"
        :columns="columns"
        :loading="isLoading"
        @on-selection-change="hdlSelectionChange"
      >
        <template slot-scope="{ row }" slot="gmtCreated">
          <span>{{ row.gmtCreated | timeFilter }}</span>
        </template>
        <template slot-scope="{ row }" slot="gmtUpdated">
          <span>{{ row.gmtUpdated | timeFilter }}</span>
        </template>
        <template slot-scope="{ row }" slot="tagType">
          <span>{{ $mapd("tagType", row.tagType) }}</span>
        </template>

        <template slot-scope="{ row }" slot="action">
          <Button
            type="primary"
            size="small"
            style="margin-right: 5px"
            @click="hdlSingleModified('update', row)"
          >
            修改
          </Button>
          <Button type="error" size="small" @click="hdlDelete(row.id)">
            删除
          </Button>
        </template>
      </Table>
      <div style="margin: auto; text-align: right;padding-top:10px">
        <Page
          :total="total"
          :current="page"
          :page-size="pageSize"
          :page-size-opts="[10, 20, 50, 100]"
          @on-change="changePage"
          @on-page-size-change="changePageSize"
          show-sizer
          show-elevator
          show-total
          transfer
        ></Page>
      </div>
      <update
        :type="modify"
        v-model="showModifyModal"
        @on-cancel="showModifyModal = false"
        @tag-modified="hdlqueryAfterReset"
        ref="modifyModal"
      ></update>
    </Card>
  </div>
</template>

<script>
import { getTagList, queryTagList, deleteTag } from "@/api";
import { Update, Query, Operation } from "./components";
export default {
  name: "sys-tag",
  components: {
    Update,
    Query,
    Operation
  },
  computed: {
    delteTagIds() {
      return this.tbSelection.map(item => item.id);
    }
  },
  data() {
    return {
      modify: "create",
      showModifyModal: false,

      isLoading: false,
      pageSize: 10,
      total: 0,
      page: 1,
      formItem: {
        name: "",
        tagType: ""
      },
      tableData: [],
      columns: [
        { title: "id", key: "id", align: "center" },
        { title: "编号", key: "code", align: "center" },
        { title: "名称", key: "name", align: "center" },
        { title: "类别", key: "tagType", align: "center", slot: "tagType" },
        {
          title: "创建时间",
          key: "gmtCreated",
          align: "center",
          slot: "gmtCreated"
        },
        {
          title: "更新时间",
          key: "gmtUpdated",
          align: "center",
          slot: "gmtUpdated"
        },
        { title: "描述", key: "description", align: "center" },
        {
          title: "操作",
          slot: "action",
          width: 150,
          align: "center"
        }
      ],
      tbSelection: []
    };
  },
  methods: {
    hdlqueryAfterReset() {
      this.$refs.query.reset();
      this.changePage(1);
    },
    changePage(pageIndex = 1, isInit) {
      this.isLoading = true;
      let pageSize = this.pageSize;
      let api = isInit ? getTagList : queryTagList;
      this.page = isInit ? 1 : pageIndex;
      api({ pageIndex, pageSize, ...this.formItem })
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
    hdlDelete(tagId) {
      let self = this;
      if (tagId) {
        this.$Modal.confirm({
          title: "确认删除？",
          content: `确定删除选中记录?`,
          onOk() {
            deleteTag({ tagId }).then(() => {
              this.$Message.success("删除成功！");
              self.hdlqueryAfterReset();
            });
          }
        });
      } else {
        this.$Message.warning("请选择一条记录!");
      }
    },
    hdlSelectionChange(selection) {
      this.tbSelection = selection;
    },
    hdlSingleModified(type = "create", data) {
      this.modify = type;
      let d = data || {
        id: "",
        name: "",
        code: "",
        tagType: "",
        description: ""
      };
      this.$refs.modifyModal.setData(d);
      this.showModifyModal = true;
    }
  },
  mounted() {
    this.changePage(1, true);
  }
};
</script>

<style lang="less" scoped>
.row-operation {
  padding: 10px 0;
}
</style>
