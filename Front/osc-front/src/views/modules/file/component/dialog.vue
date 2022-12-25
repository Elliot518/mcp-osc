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
        <el-table-column property="fileName" label="文件名"></el-table-column>
        <el-table-column property="status" label="状态"></el-table-column>
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
import { fetchFileListView } from "@/api/file";
export default {
  name: "custom-dialog",
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
      fileList: null,
      tableList: null,
      pagination: {
        page: 1,
        pageSize: 5,
        total: 0,
      },
    };
  },
  created() {
    fetchFileListView({
      batchId: this.dialogTitle,
    }).then(
      (response) => {
        this.fileList = response.data;
        this.pagination.total = this.fileList.length;
        this.tableList = this.getTableList(1);
      },
      () => {
        this.fileList = [];
        this.tableList = [];
      }
    );
  },
  methods: {
    getTableList(currentPage) {
      let page = currentPage - 1;
      if (this.fileList.length <= 0) {
        return [];
      } else {
        return this.fileList.slice(
          page * this.pagination.pageSize,
          (page + 1) * this.pagination.pageSize
        );
      }
    },
    handleCurrentChange(page) {
      this.pagination.page = page;
      this.tableList = this.getTableList(page);
    },
    beforeClose() {
      this.$emit("close", false);
    },
  },
};
</script>
<style lang="scss" scoped></style>
