<template>
  <div>
    <Row :gutter="10" type="flex">
      <Col span="6">
        <Card style="height:100%">
          <Button type="primary" @click="hdlClick">新增</Button>

          <Tree
            ref="tree"
            :data="treedata"
            show-checkbox
            check-strictly
            check-directly
            @on-check-change="hdlTreeSelected"
          ></Tree>
        </Card>
      </Col>
      <Col span="8">
        <Card>
          <Form
            ref="form1"
            :model="form1"
            label-colon
            :label-width="100"
            :rules="rules"
          >
            <FormItem label="上级ID" prop="parentId">
              <Input v-model="form1.parentId" disabled></Input>
            </FormItem>
            <FormItem label="编号" prop="code">
              <Input v-model="form1.code" :disabled="isUpdate"></Input>
            </FormItem>
            <FormItem label="名称" prop="name">
              <Input v-model="form1.name"></Input>
            </FormItem>
            <FormItem label="资源权限值" prop="authority">
              <Input v-model="form1.authority"></Input>
            </FormItem>
            <FormItem label="资源类型" prop="resourceType">
              <Select v-model="form1.resourceType" disabled>
                <Option value="menu">菜单</Option>
                <Option value="function">功能</Option>
              </Select>
            </FormItem>
            <FormItem label="组件URI" prop="url">
              <Input v-model="form1.url"></Input>
            </FormItem>
            <FormItem label="描述" prop="extension">
              <Input v-model="form1.extension"></Input>
            </FormItem>
            <FormItem>
              <Button type="primary" @click="hdlqModifyMenu">{{
                modifyButtonText
              }}</Button>
              <Button
                type="error"
                @click="hdlDelete"
                style="margin-left:8px"
                v-if="isUpdate"
                >删除</Button
              >
            </FormItem>
          </Form>
        </Card>
      </Col>
      <Col span="8">
        <resource-list-function
          :table-data="tableData"
          @on-delete-resource="query"
          :cur-tree-data="selectedTreeData"
          @resource-modified="hdlResourceModified"
        >
        </resource-list-function>
      </Col>
    </Row>
  </div>
</template>

<script>
import ResourceListFunction from "./function";
import {
  getResourceMenuTree,
  getResourceQueryTree,
  updateResource,
  createResource,
  deleteResource
} from "@/api/data";
export default {
  name: "resource-list",
  components: {
    ResourceListFunction
  },
  data() {
    return {
      isUpdate: false,
      modifyButtonText: "新增",
      submit: createResource,

      treedata: [],
      selectedTreeData: {},
      tableData: [],
      form1: {
        id: "",
        parentId: "",
        name: "",
        code: "",
        url: "",
        authority: "",
        resourceType: "",
        extension: ""
      },
      rules: {
        parentId: [
          { required: true, message: "上级ID不能为空", trigger: "blur" }
        ],
        code: [{ required: true, message: "编号不能为空", trigger: "blur" }],
        name: [{ required: true, message: "名称不能为空", trigger: "blur" }],
        resourceType: [
          { required: true, message: "权限类型不能为空", trigger: "blur" }
        ],
        url: [{ required: true, message: "URI不能为空", trigger: "blur" }],
        authority: [
          { required: true, message: "权限值不能为空", trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    hdlClick() {
      let arr = this.$refs.tree.getCheckedNodes();
      if (arr.length > 1) {
        this.$Message.warning("只能勾选一个父节点进行新增操作");
      } else {
        this.form1 = {
          parentId: arr.length == 1 ? arr[0].id : "0",
          code: "",
          authority: "/",
          name: "",
          extension: "",
          url: "/",
          resourceType: "menu"
        };
        this.tableData = [];
        this.selectedTreeData = {};
        this.isUpdate = false;
      }
    },
    reset() {
      this.form1 = {
        parentId: "0",
        code: "",
        authority: "/",
        name: "",
        extension: "",
        url: "/",
        resourceType: "menu"
      };
      this.tableData = [];
      this.selectedTreeData = {};
      this.isUpdate = false;
    },
    hdlDelete() {
      if (this.isUpdate) {
        return;
      }
      let arr = this.$refs.tree.getCheckedNodes();
      let self = this;
      if (arr.length > 1) {
        this.$Message.warning("只能勾选一个节点进行新增操作");
      } else if (arr.length > 0) {
        this.$Modal.confirm({
          title: "确认删除？",
          content: `确定删除选中记录?`,
          onOk() {
            deleteResource({ id: this.form1.id }).then(() => {
              this.$Message.success("删除成功！");
              self.init().then(() => {
                self.reset();
              });
            });
          }
        });
      } else {
        this.$Message.warning("请选择一条记录!");
      }
    },
    query(data) {
      this.tableData = [];
      if (!data) {
        let arr = this.$refs.tree.getSelectedNodes();
        if (arr.length == 0) {
          return;
        }
        data = this.$refs.tree.getSelectedNodes()[0];
      }
      getResourceQueryTree({ parentId: data.id }, { silent: true })
        .then(res => {
          this.tableData = res.data;
          this.selectedTreeData = data;
        })
        .catch(() => {
          this.tableData = [];
          this.selectedTreeData = data;
        });
    },
    hdlTreeSelected(i, n) {
      console.log(n);
      n.extension = n.extension || "";
      n.resourceType = "menu";
      this.form1 = this._.pick(
        n,
        "code",
        "parentId",
        "authority",
        "name",
        "url",
        "extension",
        "id",
        "resourceType"
      );
      this.isUpdate = true;
      this.query(n);
    },
    hdlqModifyMenu() {
      let data = this.selectedTreeData;
      this.$refs["form1"].validate(valid => {
        if (valid) {
          this.submit({ ...this.form1, resourceType: "menu" }).then(res => {
            this.curValue = false;
            let cbkdata = this.isUpdate ? data : res.data;
            this.init().then(() => {
              if (this.isUpdate) {
                this.query(cbkdata);
              } else {
                this.reset();
              }
            });
          });
        }
      });
    },
    formatData(data) {
      let arr = [];
      let self = this;
      data.forEach(item => {
        let { text: title, expanded: expand, children: funcs } = item;
        let obj = {
          title,
          name: title,
          expand,
          funcs,
          ...self._.pick(
            item,
            "code",
            "parentId",
            "authority",
            "url",
            "id",
            "resourceType"
          )
        };
        if (item.children && item.children.length > 0) {
          obj.children = this.formatData(item.children);
        }
        arr.push(obj);
      });
      return arr;
    },
    hdlResourceModified(type) {
      this.$Message.success(
        `${type == "create" ? "新增成功！" : "修改成功！"}`
      );
      let data = this.selectedTreeData;
      this.init().then(() => {
        this.query(data);
      });
    },
    init() {
      return new Promise(resolve => {
        getResourceMenuTree({}).then(res => {
          this.treedata = this.formatData(res.data);
          resolve();
        });
      });
    }
  },
  mounted() {
    this.reset();
    this.init();
  },
  watch: {
    isUpdate(isUpdate) {
      debugger;
      this.submit = isUpdate ? updateResource : createResource;
      this.modifyButtonText = isUpdate ? "修改" : "新增";
    }
  }
};
</script>

<style lang="less" scoped>
.row-operation {
  padding: 10px 0;
}
</style>
