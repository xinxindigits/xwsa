<template>
  <div>
    <Row :gutter="10" type="flex">
      <Col span="6">
        <Card>
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
            ref="form"
            :model="form"
            label-colon
            :label-width="100"
            :rules="rules"
          >
            <FormItem label="上级ID" prop="parentId">
              <Input v-model="form.parentId" disabled></Input>
            </FormItem>
            <FormItem label="编号" prop="code">
              <Input v-model="form.code" :disabled="isUpdate"></Input>
            </FormItem>
            <FormItem label="名称" prop="name">
              <Input v-model="form.name"></Input>
            </FormItem>
            <FormItem label="资源权限值" prop="authority">
              <Select v-model="form.authority" filterable>
                <Option
                  v-for="(n, index) in allGrantsList"
                  :value="n.code"
                  :key="`${n.code}_${index}`"
                  >{{ n.name }}({{ n.code }})</Option
                >
              </Select>
            </FormItem>
            <FormItem label="资源类型" prop="resourceType">
              <Select v-model="form.resourceType" disabled>
                <Option value="menu">菜单</Option>
                <Option value="function">功能</Option>
              </Select>
            </FormItem>
            <FormItem label="组件URI" prop="url">
              <Input v-model="form.url"></Input>
            </FormItem>
            <FormItem label="描述" prop="extension">
              <Input v-model="form.extension"></Input>
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
        <Operate
          :table-data="tableData"
          @on-delete-resource="query"
          :cur-tree-data="selectedTreeData"
          @resource-modified="hdlResourceModified"
        >
        </Operate>
      </Col>
    </Row>
  </div>
</template>

<script>
import { Operate } from "./components";

import {
  getGrantList,
  getResourceMenuTree,
  getResourceQueryTree,
  updateResource,
  createResource,
  deleteResource
} from "@/api";
export default {
  name: "sys-resource",
  components: {
    Operate
  },
  data() {
    return {
      allGrantsList: [],

      isUpdate: false,
      modifyButtonText: "新增",
      submit: createResource,

      treedata: [],
      selectedTreeData: {},
      tableData: [],
      form: {
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
        code: [
          { required: true, message: "编号不能为空", trigger: "blur" },
          {
            pattern: /^[a-zA-Z0-9]{4,16}$/,
            message: "请输入4～16位英文字母或数字的组合",
            trigger: "blur"
          }
        ],
        name: [
          { required: true, message: "名称不能为空", trigger: "blur" },
          { max: 128, message: "长度超出限制!", trigger: "blur" }
        ],
        resourceType: [
          { required: true, message: "权限类型不能为空", trigger: "blur" }
        ],
        url: [
          { required: true, message: "URI不能为空", trigger: "blur" },
          { max: 128, message: "长度超出限制!", trigger: "blur" }
        ],
        authority: [
          { required: true, message: "权限值不能为空", trigger: "blur" },
          { max: 128, message: "长度超出限制!", trigger: "blur" }
        ],
        extension: [
          { max: 4000, message: "不能超过4000个字符!", trigger: "blur" }
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
        this.form = {
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
      this.form = {
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
      if (!this.isUpdate) {
        return;
      }
      let self = this;
      this.$Modal.confirm({
        title: "确认删除？",
        content: `确定删除当前资源?`,
        onOk() {
          deleteResource({ id: self.form.id }).then(() => {
            this.$Message.success("删除成功！");
            self.init().then(() => {
              self.reset();
            });
          });
        }
      });
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
      getResourceQueryTree(
        { parentId: data.id, resourceType: "function" },
        { silent: true }
      )
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
      n.extension = n.extension || "";
      n.resourceType = "menu";
      this.form = this._.pick(
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
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submit({ ...this.form, resourceType: "menu" }).then(res => {
            this.curValue = false;
            let cbkdata = this.isUpdate ? data : res.data;
            this.$Message.success(this.isUpdate ? "更新成功" : "新增成功");
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
            "resourceType",
            "extension"
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
    getGrantList().then(({ data }) => {
      this.allGrantsList = data;
    });
    this.reset();
    this.init();
  },
  watch: {
    isUpdate(isUpdate) {
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
