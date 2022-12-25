<template>
  <div class="dialog">
    <el-dialog
      :title="dialogTitle"
      :visible.sync="visible"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :before-close="beforeClose"
    >
      <el-table v-loading="!tableList" :data="tableList">
        <el-table-column prop="startTime" label="开始时间"></el-table-column>
        <el-table-column prop="endTime" label="结束时间"></el-table-column>
        <el-table-column prop="status" label="状态"></el-table-column>
      </el-table>
      <el-pagination
        @current-change="handleCurrentChange"
        :page-size="pagination.pageSize"
        layout="total, prev, pager, next"
        :total="pagination.total"
      >
      </el-pagination>
    </el-dialog>
  </div>
</template>

<script>
import { fetchTaskViewList } from "@/api/file";
export default {
  name: "task-dialog",
  props: {
    visible: {
      type: Boolean,
      default: false,
    },
    dialogTitle: {
      type: String,
      default: "",
    },
  },
  data() {
    return {
      tableList: null,
      pagination: {
        pageSize: 10,
        page: 1,
        total: 0,
      },
    };
  },
  created() {
    this.getTableList();
  },
  methods: {
    getTableList() {
      this.tableList = null;
      fetchTaskViewList({
        pageNumber: this.pagination.page,
        pageSize: this.pagination.pageSize,
        dialogTitle: this.dialogTitle,
      }).then(
        (response) => {
          this.tableList = response.data.data;
          this.pagination.total = response.data.count;
        },
        () => {
          this.tableList = [];
        }
      );
    },
    handleCurrentChange(page) {
      this.pagination.page = page;
      this.getTableList();
    },
    beforeClose() {
      this.$emit("close", false);
    },
  },
};
</script>
<style lang="scss" scoped></style>
