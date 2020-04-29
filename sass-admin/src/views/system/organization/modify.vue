<template>
    <Modal
        v-model="curValue"
        :title="title"
        :loading="true"
        :mask-closable="false">
        <Form
            ref="formObj"
            :model="formObj"
            label-position="right"
            :label-width="150"
            :rules="rules"
        >
            <FormItem label="机构编号" prop="code" v-if="type == 'update'">
                <Input
                        v-model="formObj.code"
                        style="width: 280px"
                        :disabled="type == 'update'"
                ></Input>
            </FormItem>
            <FormItem label="机构名称" prop="name">
                <Input v-model="formObj.name" style="width: 280px"></Input>
            </FormItem>
            <FormItem label="企业微信corpId" prop="corpId">
                <Input v-model="formObj.corpId" style="width: 280px"></Input>
            </FormItem>
            <FormItem label="私钥" prop="privateKey">
                <Input v-model="formObj.privateKey" style="width: 280px"></Input>
            </FormItem>
            <FormItem label="通讯录应用secret" prop="addressListSecret">
                <Input v-model="formObj.addressListSecret" style="width: 280px"></Input>
            </FormItem>
            <FormItem label="联系人应用secret" prop="customerContactSecret">
                <Input v-model="formObj.customerContactSecret" style="width: 280px"></Input>
            </FormItem>
            <FormItem label="会话应用secret" prop="chatRecordSecret">
                <Input v-model="formObj.chatRecordSecret" style="width: 280px"></Input>
            </FormItem>
            <FormItem label="状态" prop="state">
                <RadioGroup v-model="formObj.state">
                    <Radio label="Y">启用</Radio>
                    <Radio label="N">禁用</Radio>
                </RadioGroup>
            </FormItem>
            <FormItem label="机构描述" prop="remark">
                <Input
                        v-model="formObj.remark"
                        type="textarea"
                        :maxlength="50"
                        style="width: 280px"
                        :show-word-limit="true"
                        :rows="5"
                ></Input>
            </FormItem>
        </Form>
        <div slot="footer">
            <Button @click="hdlCancel">取消</Button>
            <Button
                    style="margin-left: 8px"
                    type="primary"
                    @click="hdlSubmit('formObj')"
            >确认</Button
            >
        </div>
    </Modal>
</template>

<script>
    import { addOrganization, updateOrganization } from "@/api/data_organization";
    const _config = {
        create: {
            title: "新增组织机构",
            success_evt: "on-add-organization",
            submit: addOrganization
        },
        update: {
            title: "更新组织机构",
            success_evt: "on-update-organization",
            submit: updateOrganization
        }
    };
    export default {
        name: "organization-update",
        props: {
            value: Boolean,
            type: {
                validator: function(value) {
                    return ["create", "update"].indexOf(value) !== -1;
                }
            }
        },
        computed: {
            title() {
                return _config[this.type].title;
            }
        },
        data() {
            const validateCode = (rule, value, callback) => {
                if (value === '' && !this.addFlowFlag) {
                    callback(new Error('角色编号不能为空'));
                } else {
                    callback();
                }
            };

            return{
                curValue:false,
                formObj: {
                    name: "",
                    code: "",
                    privateKey: "",
                    corpId:"",
                    addressListSecret:"",
                    customerContactSecret:"",
                    chatRecordSecret:"",
                    state:"Y",
                    remark: "",
                    parentId:0,
                    orgType:"COMP",
                },
                rules: {
                    code: [
                        { validator:validateCode, trigger: "blur" }
                    ],
                    name: [
                        { required: true, message: "角色名称不能为空", trigger: "blur" }
                    ],
                    corpId: [
                        { required: true, message: "企业微信corpId不能为空", trigger: "blur" }
                    ],
                    state: [
                        { required: true, message: '状态不能为空', trigger: 'change' }
                    ],

                }
            }
        },
        methods:{
            setData({obj,remark,state}) {
                this.formObj.code = obj.code;
                this.formObj.name = obj.name;
                this.formObj.remark = remark;
                this.formObj.state = state;
                this.formObj.privateKey = obj.privateKey;
                this.formObj.corpId = obj.corpId;
                this.formObj.addressListSecret = obj.addressListSecret;
                this.formObj.customerContactSecret = obj.customerContactSecret;
                this.formObj.chatRecordSecret = obj.chatRecordSecret;
            },
            hdlSubmit(name) {
                this.$refs[name].validate(valid => {
                    if (valid) {
                        _config[this.type].submit(this.formObj).then(() => {
                            this.curValue = false;
                            this.$emit(_config[this.type].success_evt, this.formObj);
                        });
                    }
                });
            },
            hdlCancel() {
                this.curValue = false;
                this.$emit("on-cancel");
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
    }
</script>

<style scoped>

</style>