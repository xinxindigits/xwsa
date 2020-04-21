<template>
  <div class="page">
    <Card dis-hover>
      <Table
        :data="tableData"
        :columns="tableColumns"
        stripe
        border
        class="table"
        :loading="isLoading"
        @on-row-click="hdlSelect"
        highlight-row
      >
        <template #action="{row,column,index}">
          <Poptip trigger="click" placement="bottom-start" :transfer="true">
            <Button>操作详情</Button>
            <div slot="content">
              <Button
                type="primary"
                size="small"
                style="margin-right: 5px"
                @click="showUpdateModel = true"
                >修改参数
              </Button>
              <Button
                type="info"
                size="small"
                style="margin-right: 5px"
                @click="showRoleQueueModel"
              >
                查看权限详情
              </Button>
            </div>
          </Poptip>
        </template>
      </Table>
      <div style="margin: auto; text-align: right">
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
    </Card>
  </div>
</template>

<script>
import {
  post,
  queryByPage,
  get_user_role_list_url,
  add_user_role_info_url,
  query_role_queue_url,
  update_user_role_info_url,
  update_role_queue_url,
  code_library_url
} from "@/api/data";
import { getDesc, statusEnumMap } from "_c/common.js";

export default {
  name: "role_authority_list",
  components: {},
  data() {
    return {
      isLoading: false,
      formObj: {
        roleId: "",
        roleName: ""
      },
      addRoleFormObj: {
        roleId: "",
        roleName: "",
        status: "",
        level: "",
        describe: ""
      },
      updateRoleFormObj: {
        id: "",
        roleId: "",
        roleName: "",
        status: "",
        level: "",
        describe: ""
      },
      roleRoleQueueObj: {
        id: "",
        roleId: "",
        roleName: "",
        queueNo: []
      },
      ruleValidator1: {
        roleId: [
          { required: true, message: "角色编号不能为空", trigger: "blur" }
        ],
        roleName: [
          { required: true, message: "角色名称不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "角色状态不能为空", trigger: "blur" }
        ],
        level: [
          { required: true, message: "角色等级不能为空", trigger: "blur" }
        ],
        describe: [
          { required: true, message: "角色描述不能为空", trigger: "blur" }
        ]
      },
      ruleValidator2: {
        roleId: [
          { required: true, message: "角色编号不能为空", trigger: "blur" }
        ]
      },
      roleQueueNoEnum: [],
      showAddModel: false,
      showUpdateModel: false,
      roleQueueModel: false,
      selectedTableData: {},
      selectRoleQueueData: {},
      tableData: [],
      statusEnum: [],
      tableColumns: [
        {
          //key：id  记录数据库主键，方便修改
          type: "index",
          key: "id",
          align: "center",
          width: 60
        },
        {
          title: "操作",
          slot: "action",
          align: "center",
          width: 150
        },
        {
          title: "角色编号",
          key: "roleId",
          align: "center",
          minWidth: 150
        },
        {
          title: "角色名称",
          key: "roleName",
          align: "center",
          minWidth: 150
        },
        {
          title: "角色状态",
          key: "statusDesc",
          align: "center",
          minWidth: 150
        },
        {
          title: "角色等级",
          key: "level",
          align: "center",
          minWidth: 120
        },
        {
          title: "角色描述",
          key: "describe",
          align: "center",
          minWidth: 200
        },
        {
          title: "创建时间",
          key: "gmtCreated",
          align: "center",
          minWidth: 200
        },
        {
          title: "更新时间",
          key: "gmtUpdated",
          align: "center",
          minWidth: 150
        },
        {
          title: "更新人",
          key: "gmtUpdater",
          align: "center",
          minWidth: 150
        }
      ],
      pageSize: 10,
      total: 0,
      page: 1
    };
  },
  mounted: function() {
    var __this = this;
    post(code_library_url, { systemId: "XCas", codeNo: "ApprovePower" })
      .then(data => {
        console.log(data);
        for (var i = 0; i < data.length; i++) {
          __this.roleQueueNoEnum.push(data[i]);
        }
      })
      .catch(err => {
        this.$Message.error("查询角色权限列表失败:" + JSON.stringify(err));
      });
    this.statusEnum = statusEnumMap;
  },
  computed: {},
  methods: {
    hdlquery() {
      this.changePage(1);
    },
    reset() {
      this.formObj.roleName = "";
      this.formObj.roleId = "";
    },
    changePage(page) {
      // 清空选择项
      this.selectedTableData = {};
      this.$refs["updateRoleFormObj"].resetFields();
      this.page = page;
      let self = this;
      self.isLoading = true;
      let questData = this.formObj;
      queryByPage(get_user_role_list_url, questData, this.page, this.pageSize)
        .then(res => {
          this.tableData = res.data;
          this.total = Number(res.total);
          this.tableData.forEach(row => {
            row.statusDesc = getDesc(statusEnumMap, row.status);
          });
        })
        .catch(err => {
          console.log(err);
          this.$Message.warning("查询异常:" + err);
        })
        .finally(() => (self.isLoading = false));
    },
    changePageSize(pageSize) {
      this.pageSize = pageSize;
    },
    hdlSelect(selection, row) {
      console.log(row);
      this.selectedTableData = selection;
      this.updateRoleFormObj = selection;
      this.updateRoleFormObj.level = selection.level + "";
    },
    addRole() {
      this.$refs["addRoleFormObj"].validate(valid => {
        if (valid) {
          post(add_user_role_info_url, this.addRoleFormObj)
            .then(data => {
              if (data.count > 0) {
                console.log(data);
                this.$Message.info("新增成功！");
                this.$refs["addRoleFormObj"].resetFields();
                this.changePage(this.page);
              } else {
                this.$Message.info("新增失败！");
              }
            })
            .catch(err => {
              this.$Message.error("新增角色失败:" + JSON.stringify(err));
            });
          this.showAddModel = false;
        }
      });
    },
    cancelAddRole() {
      this.$refs["addRoleFormObj"].resetFields();
      this.showAddModel = false;
    },
    updateRole() {
      this.$refs["updateRoleFormObj"].validate(valid => {
        if (valid) {
          post(update_user_role_info_url, this.updateRoleFormObj)
            .then(data => {
              if (data.count > 0) {
                console.log(data);
                this.$Message.info("修改成功");
                this.changePage(this.page);
              } else {
                this.$Message.info("修改失败");
              }
              this.showUpdateModel = false;
            })
            .catch(err => {
              this.$Message.error("修改角色失败：" + JSON.stringify(err));
            });
        }
      });
    },
    showRoleQueueModel() {
      //进页面，先清空roleRoleQueueObj中缓存的数据
      this.roleRoleQueueObj.queueNo = [];
      this.roleQueueModel = true;
      this.roleRoleQueueObj.roleId = this.selectedTableData.roleId;
      this.roleRoleQueueObj.roleName = this.selectedTableData.roleName;
      post(query_role_queue_url, {
        roleId: this.roleRoleQueueObj.roleId,
        status: "1"
      })
        .then(data => {
          for (var i = 0; i < data.length; i++) {
            this.roleRoleQueueObj.queueNo.push(data[i].queueNo);
          }
        })
        .catch(err => {
          this.$Message.error("查询角色队列:" + JSON.stringify(err));
        });
    },
    updateRoleQueue() {
      this.roleQueueModel = false;
      this.$refs["roleRoleQueueObj"].validate(valid => {
        if (valid) {
          post(update_role_queue_url, this.roleRoleQueueObj)
            .then(data => {
              console.log(data);
              this.$Message.info("修改成功！");
              this.changePage(this.page);
            })
            .catch(err => {
              console.log(err);
              this.$Message.error("修改失败:" + JSON.stringify(err));
            });
        } else {
          this.$Message.info("修改失败！");
        }
      });
    }
  }
};
</script>
