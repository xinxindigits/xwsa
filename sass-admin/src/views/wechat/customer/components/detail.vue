<template>
  <Drawer
    title="客户详情"
    width="80"
    v-model="showDetail"
    scrollable
    transfer
    :styles="styles"
  >
    <List>
      <ListItem>
        <ListItemMeta :title="detail.customerName" :description="description">
          <div class="avatar-wrapper" slot="avatar">
            <img :src="detail.avatar" class="avatar" alt="头像" />
          </div>
        </ListItemMeta>
      </ListItem>
    </List>
    <CellGroup>
      <Cell>
        <span>状态：{{ detail.status }}</span>
      </Cell>
      <Cell>
        <span>性别：{{ detail.gender }}</span>
      </Cell>
      <Cell>
        <span>客户类型：{{ $mapd("customerType", detail.customerType) }}</span>
      </Cell>
      <Form label-colon>
        <FormItem label="标签" :label-width="60">
          <Select v-model="detail.tagList" filterable multiple>
            <Option v-for="n in tagList" :key="n.id" :value="n.id">{{
              n.name
            }}</Option>
          </Select>
        </FormItem>
      </Form>

      <Divider dashed></Divider>
      <Cell>
        <span>公司名称：{{ detail.corpFullName }}</span>
      </Cell>
      <Cell>
        <span>职位：{{ detail.customerPosition }}</span>
      </Cell>
      <Divider dashed></Divider>
      <Cell>
        <span>添加人：{{ detail.memberUserId }}</span>
      </Cell>
      <Cell>
        <span>跟进人：{{ detail.followUser }}</span>
      </Cell>

      <Divider orientation="left" dashed> 其他</Divider>
      <Cell>
        <span>扩展信息：{{ detail.externalProfile }}</span>
      </Cell>
      <Divider dashed></Divider>
      <Button type="primary" @click="hdlClick">会话信息</Button>
    </CellGroup>
    <div class="drawer-footer">
      <Button style="margin-right: 8px" @click="hdlCancel">取消</Button>
      <Button type="primary" @click="hdlSubmit">确认</Button>
    </div>
  </Drawer>
</template>

<script>
import { setTagByKeyId } from "@/api";
export default {
  name: "customer-detail",
  props: {
    value: Boolean,
    items: Object,
    tagList: Array
  },
  computed: {
    description() {
      return `账号：${this.detail.userId || ""}`;
    }
  },
  data() {
    return {
      styles: {
        height: "calc(100% - 55px)",
        overflow: "auto",
        paddingBottom: "53px",
        position: "static"
      },
      showDetail: false,
      detail: {
        avatar: "",
        corpFullName: "",
        customerPosition: "",
        customerName: "",
        customerType: "",
        externalProfile: "",
        gender: "",
        id: "",
        memberUserId: "",
        status: "",
        unionId: "",
        userId: "",
        tagList: []
      }
    };
  },
  methods: {
    hdlCancel() {
      this.$emit("on-cancel");
    },
    hdlClick() {
      this.$emit("show-record", this.detail.userId);
    },
    hdlSubmit() {
      let tagIds = this.detail.tagList.map(n => {
        return n.toString();
      });
      setTagByKeyId({ keyId: this.detail.userId, tagIds, keyName: "customer" });
    }
  },
  watch: {
    value(newValue) {
      this.showDetail = newValue;
    },
    showDetail(newValue) {
      this.$emit("input", newValue);
    },
    items: {
      immediate: true,
      deep: true,
      handler(newValue) {
        let tag_list = [];
        if (newValue.tags && newValue.tags.length > 0) {
          tag_list = newValue.tags.map(n => {
            return n.id;
          });
        }
        this.detail = newValue;
        this.detail.tagList = tag_list;
      }
    }
  }
};
</script>
<style lang="less" scoped>
img:after {
  content: "\f1c5""" attr(alt);
  font-size: 12px;
  color: rgb(100, 100, 100);
  display: block;
  position: absolute;
  z-index: 2;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: #fff;
}
img[src=""],
img:not([src]) {
  opacity: 0;
}
fieldset,
img {
  border: none;
  background-color: white;
}
.avatar-wrapper {
  position: relative;
  float: left;
  width: 60px;
  height: 60px;
}
.qrcode {
  vertical-align: top;
  width: 80px;
  height: 80px;
  border: none;
}
.avatar {
  vertical-align: top;
  width: 100%;
  height: 100%;
  border: none;
}
.avatar-wrapper:after {
  content: " ";
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  border: 1px solid rgba(0, 0, 0, 0.1);
}
.drawer-footer {
  width: 100%;
  position: absolute;
  bottom: 0;
  left: 0;
  border-top: 1px solid #e8e8e8;
  padding: 10px 16px;
  text-align: right;
  background: #fff;
}
</style>
