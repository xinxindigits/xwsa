<template>
  <Card>
    <Button @click="hdlCancel">返回</Button>
    <List>
      <ListItem>
        <ListItemMeta :title="detail.memberName" :description="description">
          <div class="test" slot="avatar">
            <img :src="detail.avatar" class="avatar" alt="成员图片" />
          </div>
        </ListItemMeta>
        <template slot="action"> </template>
      </ListItem>
    </List>
    <CellGroup>
      <Cell>
        <img :src="detail.qrCode" class="avatar" alt="微信二维码" />
      </Cell>
      <Divider dashed></Divider>
      <Cell>
        <span>手机：{{ detail.mobile }}</span>
      </Cell>
      <Cell>
        <span>座机：{{ detail.telephone }}</span>
      </Cell>
      <Cell>
        <span>邮箱：{{ detail.mail }}</span>
      </Cell>
      <Cell>
        <span>地址：{{ detail.address }}</span>
      </Cell>
      <Divider dashed></Divider>
      <Cell>
        <span>部门：{{ detail.mainDepartmentName }}</span>
      </Cell>
      <Cell>
        <span>职位：{{ detail.memberPosition }}</span>
      </Cell>
      <Cell>
        <span>状态：{{ detail.memberStatus }}</span>
      </Cell>
      <Divider dashed></Divider>
    </CellGroup>
  </Card>
</template>

<script>
export default {
  name: "member-detail",
  props: {
    items: Object
  },
  computed: {
    description() {
      return `账号：${this.detail.userId}`;
    }
  },
  data() {
    return {
      detail: {
        avatar: "",
        memberName: "",
        userId: "",
        mobile: "",
        address: "",
        mail: "",
        mainDepartmentName: "",
        telephone: "",
        memberPosition: "",
        extension: "",
        qrCode: "",
        memberStatus: ""
      }
    };
  },
  methods: {
    hdlCancel() {
      this.$emit("on-cancel");
    }
  },
  watch: {
    items: {
      immediate: true,
      deep: true,
      handler(newValue) {
        this.detail = newValue;
      }
    }
  }
};
</script>
<style lang="less" scoped>
.test {
  position: relative;
  float: left;
}
.avatar {
  vertical-align: top;
  width: 100px;
  height: 100px;
  border: none;
}
.test:after {
  content: " ";
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  border: 1px solid rgba(0, 0, 0, 0.1);
}
</style>
