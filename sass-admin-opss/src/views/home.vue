<template>
  <div class="home">
    <mng-home v-if="!showXTenant"></mng-home>
  </div>
</template>

<script>
import mngHome from "@/views/manage/manage";
import { mapGetters } from "vuex";
export default {
  name: "Home",
  components: {
    mngHome
  },
  computed: {
    ...mapGetters(["hasRefreshXTenant", "xTenant"]),
    showXTenant() {
      return this.hasRefreshXTenant && !!this.xTenant;
    }
  }
};
</script>
