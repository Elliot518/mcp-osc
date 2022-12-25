<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: "App",
};
</script>
<style>
.el-form-item.is-required:not(.is-no-asterisk) > .el-form-item__label:after {
  content: "*";
  color: #ff4949;
  margin-left: 4px;
}
.el-form-item.is-required:not(.is-no-asterisk) > .el-form-item__label:before {
  content: "" !important;
}
.el-dialog__footer {
  border-top: 1px solid #d9d9d9;
  text-align: center;
  padding: 15px !important;
  text-align: center !important;
}
.el-dialog__header {
  border-bottom: 1px solid #d9d9d9;
}
.el-dialog__body {
  padding: 15px 20px 30px;
}
</style>
