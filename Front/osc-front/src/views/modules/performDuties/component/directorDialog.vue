<template>
  <div class="dialog">
    <el-dialog
      :title="dialogTitle"
      :visible.sync="visible"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :before-close="beforeClose"
    >
      <el-table
        :data="tableData"
        @selection-change="handleSelectionChange"
        style="width: 100%"
      >
        <el-table-column align="center" type="selection" width="55">
        </el-table-column>
        <el-table-column align="center" prop="name" label="姓名">
        </el-table-column>
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" type="primary" @click="submit">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
export default {
  name: "director-dialog",
  props: {
    visible: {
      type: Boolean,
      default: false,
    },
    dialogTitle: {
      type: String,
      default: "",
    },
    tableList: {
      type: Array,
    },
  },
  data() {
    return {
      tableData: [
        {
          name: "张三",
        },
        {
          name: "王五",
        },
        {
          name: "李四",
        },
      ],
      multipleSelection: [],
    };
  },
  created() {
    this.tableList ? (this.tableData = this.tableList) : "";
  },
  methods: {
    beforeClose() {
      this.$emit("close", false);
    },
    handleSelectionChange(v) {
      this.multipleSelection = v;
    },
    submit() {
      this.$emit("directorMultipleList", this.multipleSelection);
      this.$emit("close", false);
    },
  },
};
</script>
<style lang="scss" scoped></style>
