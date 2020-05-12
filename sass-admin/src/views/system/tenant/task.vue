<template>
    <Modal
        v-model="curValue"
        :title="title"
        :loading="true"
        :mask-closable="false"
    >
    </Modal>
</template>

<script>
    export default {
        name: "operate",
        methods: {
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
    };

</script>

<style scoped>

</style>