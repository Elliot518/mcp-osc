<template>
  <div>
    <el-table v-loading="!tableList" :data="tableList" style="width: 100%">
      <el-table-column prop="jobName" label="任务名称" align="center">
      </el-table-column>
      <el-table-column prop="status" label="状态" align="center">
      </el-table-column>
      <el-table-column prop="threadNum" label="线程数" align="center">
      </el-table-column>
      <el-table-column prop="lastStartTime" label="下次启动时间" align="center">
      </el-table-column>
      <el-table-column prop="operate" label="操作" align="center">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleOpen(scope.row)"
            >查看日志</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :page-sizes="[5, 10, 15, 20]"
      :current-page="pagination.page"
      :page-size="pagination.pageSize"
      layout="total,sizes, prev, pager, next, jumper"
      :total="pagination.total"
    >
    </el-pagination>
    <task-dialog
      v-if="viewVisible"
      :visible="viewVisible"
      :dialogTitle="taskName"
      @close="(value) => (viewVisible = value)"
    ></task-dialog>
  </div>
</template>
<script>
import { fetchTaskCenter } from "@/api/file";
import TaskDialog from "./component/taskCenterDialog.vue";
export default {
  name: "taskCenter",
  data() {
    return {
      tableList: null,
      viewVisible: false,
      taskName: "",
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
  components: { TaskDialog },
  methods: {
    handleOpen(rowData) {
      this.viewVisible = true;
      this.taskName = rowData.jobName;
    },
    handleCurrentChange(page) {
      this.pagination.page = page;
      this.getTableList();
    },
    handleSizeChange(pageSize) {
      this.pagination.page = 1;
      this.pagination.pageSize = pageSize;
      this.getTableList();
    },
    getTableList() {
      this.tableList = null;
      fetchTaskCenter({
        pageNumber: this.pagination.page,
        pageSize: this.pagination.pageSize,
      }).then(
        (response) => {
          this.tableList = response.data.data;
          this.pagination.total = response.data.totalCount;
        },
        () => {
          this.tableList = [];
        }
      );
    },
  },
};
</script>
<style lang="scss" scoped></style>
