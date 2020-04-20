import customBreadCrumb from "./custom-bread-crumb";
export default {
  components: {
    customBreadCrumb
  },
  computed: {
    breadCrumbList() {
      return this.$store.state.app.breadCrumbList;
    }
  },
  mounted() {
    this.setBreadCrumb(this.$route);
  }
};
