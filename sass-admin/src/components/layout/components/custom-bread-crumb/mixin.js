import customBreadCrumb from "./custom-bread-crumb";
import { mapMutations } from "vuex";
export default {
  components: {
    customBreadCrumb
  },
  computed: {
    breadCrumbList() {
      return this.$store.state.app.breadCrumbList;
    }
  },
  methods: {
    ...mapMutations(["setBreadCrumb"])
  },
  mounted() {
    this.setBreadCrumb(this.$route);
  },
  watch: {
    $route(newRoute) {
      this.setBreadCrumb(newRoute);
    }
  }
};
