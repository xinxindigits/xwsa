<template>
  <Modal
    v-model="curValue"
    title="权限控制"
    :loading="loading"
    :mask-closable="false"
    @on-ok="hdlSubmit"
    @on-cancel="hdlCancel"
  >
    <Tree ref="tree" :data="treedata" show-checkbox></Tree>
  </Modal>
</template>
<script>
import { getGrantTree } from "@/api";
import { grantRole } from "@/api";
export default {
  name: "grant-tree",
  props: { value: Boolean },
  data() {
    return {
      loading: true,
      roleCode: "",
      roleName: "",
      curValue: false,
      treedata: []
    };
  },

  methods: {
    queryGrantTree({ roleCode, roleName }) {
      getGrantTree({ roleCode, roleName }).then(res => {
        this.roleCode = roleCode;
        this.roleName = roleName;
        this.treedata = this.formatData(res.data);
        this.curValue = true;
      });
    },
    hdlSubmit() {
      let arr = this.$refs.tree.getCheckedAndIndeterminateNodes();
      let resources = arr.map(item => {
        return {
          resourceCode: item.code,
          resourceName: item.title
        };
      });
      grantRole({
        roleCode: this.roleCode,
        roleName: this.roleName,
        resources
      })
        .then(() => {
          this.$Message.success("更新成功");
          this.curValue = false;
          this.$emit("on-grant-role");
        })
        .catch(() => {
          this.loading = false;
          this.loading = true;
        });
    },
    hdlCancel() {
      this.$emit("on-cancel");
    },
    formatData(data) {
      let arr = [];
      data.forEach(item => {
        let { text: title, expanded: expand, checked, code } = item;
        let obj = {
          title,
          expand,
          code
        };
        if (item.children && item.children.length > 0) {
          obj.children = this.formatData(item.children);
        } else {
          obj.checked = checked;
        }
        arr.push(obj);
      });
      return arr;
    }
  },
  watch: {
    value: {
      handler(newValue) {
        this.curValue = newValue;
      },
      immediate: true
    },
    curValue(newValue) {
      this.$emit("input", newValue);
    }
  }
};
</script>
