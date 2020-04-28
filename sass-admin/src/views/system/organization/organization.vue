<template>
    <div>
        <Card>
            <Form :model="formItem" inline label-colon>
                <FormItem>
                    <Input v-model="formItem.code" placeholder="机构编码"></Input>
                </FormItem>
                <FormItem>
                    <Input v-model="formItem.name" placeholder="机构名称"></Input>
                </FormItem>
                <FormItem>
                    <Button type="primary" @click="hdlquery">查询</Button>
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
                stripe
                border
                ref="tables"
                :columns="columns"
                :data="tableData"
                :loading="isLoanding"
                @on-selection-change="hdlSelectionChange"
                >
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
                    <Button type="error" size="small" @click="hdlDelete([row.code])">
                        删除
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
            <organization-update
                    type="create"
                    v-model="showAddModal"
                    @on-cancel="showAddModal = false"
                    @on-add-organization="hdlquery"
            ></organization-update>
            <organization-update
                    type="update"
                    v-model="showUpdateModal"
                    @on-update-role="hdlquery"
                    ref="updateModal"
            ></organization-update>
        </Card>
    </div>
</template>

<script>
    import { getOrganizationList, delOrganization } from "@/api/data_organization";
    import OrganizationUpdate from "./modify";
    export default {
        name: "organization",
        components: {
            OrganizationUpdate,
        },
        computed: {
            deleteOrgCodes() {
                return this.tbSelection.map(item => item.code);
            },
        },
        data(){
            return {
                showAddModal: false,
                showGrantModal: false,
                showUpdateModal: false,
                pageSize: 10,
                total: 0,
                page: 1,
                formItem: {
                    name: "",
                    code: "",
                    orgType: "",
                    state:"",
                },
                columns:[
                    {
                        type:'selection',
                        width:60,
                        align:'center'
                    },
                    {title:'机构编码',key:'code',align:'center'},
                    {title:'机构名称',key:'name',align:'center'},
                    {title:'创建时间',key:'gmtCreated',align:'center', slot: "create_time"},
                    {title:'备注',key:'remark',align:'center'},
                    {title:'状态',key:'state',align:'center'},
                    {title:'操作',key:'action',align:'center',width:150}
                ],
                isLoading: false,
                tableData: [],
                tbSelection:[],
            }
        },
        methods:{
            hdlquery() {
                this.changePage(1);
            },
            changePage(pageNum) {
                this.isLoading = true;
                let pageSize = this.pageSize;
                let pageIndex = pageNum;
                getOrganizationList({ pageIndex, pageSize, ...this.formItem })
                    .then(res => {
                        let { data } = res;
                        this.reset();
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
                this.formItem.name = "";
                this.formItem.orgType = "";
                this.formItem.code = "";
                this.formItem.state= "";
            },
            hdlDelete(codes) {
                let self = this;
                if (codes.length > 0) {
                    this.$Modal.confirm({
                        title: "确认删除？",
                        content: `确定删除选中记录?`,
                        onOk() {
                            delOrganization({ codes }).then(() => {
                                this.$Message.success("删除成功！");
                                self.hdlquery();
                            });
                        }
                    });
                } else {
                    this.$Message.warning("请选择一条记录!");
                }
            },
            hdlSingleCreate() {
                this.showAddModal = true;
            },
            hdlSingleModified(data) {
                this.$refs.updateModal.setData(data);
                this.showUpdateModal = true;
            },
            hdlSelectionChange(selection) {
                this.tbSelection = selection;
            },
        },
        mounted() {
            this.changePage(1);
        }
    }
</script>

<style lang="less" scoped>
    .row-operation {
        padding: 10px 0;
    }
</style>
