<template>
  <div class="app-wrapper">
    <app-header class="app-header" />
    <div :class="classObj" class="appContent">
      <sidebar class="sidebar-container" />
      <div class="main-container">
        <div>
          <navbar />
        </div>
        <app-main />
      </div>
    </div>
  </div>
</template>

<script>
import { AppMain, Sidebar, Navbar, AppHeader } from "./components";
import { mapState } from "vuex";

export default {
  name: "Layout",
  components: {
    AppMain,
    Navbar,
    Sidebar,
    AppHeader,
  },
  computed: {
    ...mapState({
      sidebar: (state) => state.app.sidebar,
    }),
    classObj() {
      return {
        hideSidebar: !this.sidebar.opened,
        openSidebar: this.sidebar.opened,
      };
    },
  },
};
</script>

<style lang="scss" scoped>
@import "~@/styles/mixin.scss";

.app-wrapper {
  @include clearfix;
  position: relative;
  height: 100%;
  width: 100%;
  .appContent {
    height: calc(100% - 60px);
    overflow: hidden;
  }
  .app-header {
    height: 60px;
    border-bottom: 1px solid #d9d9d9;
  }
}
</style>
