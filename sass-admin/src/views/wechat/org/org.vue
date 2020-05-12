<template>
  <div>
    <Row :gutter="5">
      <Col :xs="8" :sm="8" :md="8" :lg="6">
        <Card style="height:100%">
          <Form inline>
            <FormItem props="departmentName">
              <Input
                placeholder="部门名称"
                v-model="queryForm.departmentName"
              ></Input>
            </FormItem>
            <FormItem>
              <Button type="primary" @click="hdlFilterOrg">搜索</Button>
              <Button @click="reset" style="margin-left:6px">重置</Button>
            </FormItem>
          </Form>
          <Tree
            ref="tree"
            :data="treeData"
            @on-select-change="hdlTreeSelected"
          ></Tree>
        </Card>
      </Col>
      <Col :xs="16" :sm="16" :md="16" :lg="17">
        <Card>
          <Table
            stripe
            border
            ref="tables"
            :data="tableData"
            :columns="columns"
            :loading="isLoading"
          >
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
          </div> </Card
      ></Col>
    </Row>
    <Drawer title="详情" width="80" v-model="showDetail" scrollable transfer>
      <staff-detail
        :items="memberDetail"
        @show-record="hdlShowRecord"
      ></staff-detail>
    </Drawer>
    <msg-record
      v-model="showRecord"
      :user-id="cur_userId"
      ref="record"
    ></msg-record>
  </div>
</template>

<script>
import {
  getOrgList,
  queryOrgList,
  queryMemberByDeptId,
  getMemberDetail
} from "@/api";
import { StaffDetail } from "@/views/wechat/staff/components";
import MsgRecord from "@/components/msg-record/msg-record";
export default {
  name: "org-list",
  components: {
    StaffDetail,
    MsgRecord
  },
  data() {
    return {
      showDetail: false,
      cur_userId: "",
      showRecord: false,

      pageSize: 10,
      total: 0,
      page: 1,
      isLoading: false,
      tableData: [],
      columns: [
        { title: "id", key: "id", width: 80 },
        { title: "账号", key: "userId" },
        { title: "姓名", key: "memberName" },
        { title: "手机", key: "mobile" },
        { title: "邮箱", key: "mail" },
        {
          title: "操作",
          slot: "action",
          width: 150,
          align: "center"
        }
      ],

      queryForm: {
        departmentId: "",
        departmentName: ""
      },
      treeData: [],
      memberDetail: {}
    };
  },
  methods: {
    hdlShowRecord(userId) {
      this.$refs.record.init(userId);
      this.showRecord = true;
    },
    hdlFilterOrg() {
      queryOrgList(this.queryForm).then(res => {
        this.treeData = this.formatData(res.data);
      });
    },
    reset() {
      this.queryForm.departmentId = "";
      this.queryForm.departmentName = "";
      this.hdlFilterOrg();
    },
    hdlTreeSelected() {
      this.changePage(1);
    },
    formatData(data) {
      let arr = [];
      let self = this;
      data.forEach(item => {
        let { departmentName: title, expanded: expand, children: funcs } = item;
        let obj = {
          title,
          expand,
          funcs,
          ...self._.pick(item, "tenantId", "departmentId", "parentId", "id")
        };
        if (item.children && item.children.length > 0) {
          obj.children = this.formatData(item.children);
        }
        arr.push(obj);
      });
      return arr;
    },

    hdlSingleModified(row) {
      getMemberDetail({ id: row.id }).then(res => {
        this.memberDetail = res.data;
        this.cur_userId = row.userId;
        this.showDetail = true;
      });
    },

    changePage(pageIndex) {
      let deptId = this.$refs.tree.getSelectedNodes()[0].departmentId;
      let params = {
        deptId,
        pageIndex,
        pageSize: this.pageSize
      };
      this.isLoading = true;
      queryMemberByDeptId(params)
        .then(res => {
          let { data } = res;
          this.tableData = data.items;
          this.total = data.total;
        })
        .finally(() => (this.isLoading = false));
    },
    changePageSize(pageSize) {
      this.pageSize = pageSize;
      this.changePage(1);
    }
  },
  mounted() {
    let self = this;
    getOrgList().then(res => {
      self.treeData = this.formatData(res.data);
    });
  }
};
</script>

<style lang="less" scoped></style>
