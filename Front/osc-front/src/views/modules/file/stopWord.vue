<template>
  <div class="con">
    <div class="upload">
      <el-upload
        ref="upload"
        class="upload-demo"
        drag
        :multiple="false"
        :show-file-list="false"
        :action="actionUrl"
        :auto-upload="true"
        :on-error="handleError"
        :on-success="handleSuccess"
        :before-upload="beforeUpload"
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖拽到此处，或<em>点击导入停用词</em>
        </div>
        <div class="tip-text" slot="tip">
          <div class="template-down">
            <el-link
              href="./stopWord.csv"
              type="primary"
              download="stopWord.csv"
            >
              停用词模板下载
            </el-link>
          </div>
          <div class="el-upload__tip">只能导入csv文件,仅支持全量导入</div>
        </div>
      </el-upload>
    </div>
    <el-table v-loading="!fileList" :data="fileList" style="width: 100%">
      <el-table-column
        prop="fileName"
        header-align="center"
        align="center"
        show-overflow-tooltip
        label="文件名称"
      >
        <template slot-scope="scope">
          <el-link type="primary" @click="handleOpen(scope.row)">{{
            scope.row.fileName
          }}</el-link>
        </template>
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        align="center"
        prop="createTime"
        label="导入时间"
      >
        <template slot-scope="scope">
          <interface-content
            :content="scope.row.createTime"
          ></interface-content>
        </template>
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        align="center"
        prop="createTime"
        label="状态"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="success"
            v-if="scope.row.stopWordsStatus === 1"
            >使用中</el-button
          >
          <el-button
            size="mini"
            type="primary"
            v-else
            @click="handleUse(scope.row.sid, '确认使用此文件?')"
            >设置</el-button
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
  </div>
</template>

<script>
import { fetchStopWordFileList, openStopWord } from "@/api/file";

export default {
  name: "fileUpload",
  data() {
    return {
      fileList: null,
      pagination: {
        page: 1,
        pageSize: 5,
        total: 0,
      },
      fileUploadlist: [],
    };
  },
  created() {
    this.getFileList();
  },
  computed: {
    actionUrl() {
      return process.env.VUE_APP_GVA_BASE_API
        ? process.env.VUE_APP_GVA_BASE_API + "/stop-words/upload"
        : "/stop-words/upload";
    },
  },
  methods: {
    handleUse(id, message) {
      this.$confirm(message, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        openStopWord(id).then(
          (response) => {
            console.log(response);
            this.getFileList();
          },
          () => {}
        );
      });
    },
    handleOpen(rowData) {
      //点击下载文件
      console.log(rowData);
    },
    handleError() {
      this.$message({
        message: "上传失败",
        type: "error",
      });
    },
    async handleSuccess() {
      this.pagination.page = 1;
      let flog = await fetchStopWordFileList({
        pageNumber: this.pagination.page,
        pageSize: this.pagination.pageSize,
      });
      if (flog.code === "0") {
        this.fileList = flog.data.data;
        this.pagination.total = flog.data.totalCount;
        this.handleUse(this.fileList[0].sid, "确认使用新上传的文件?");
      }
    },
    beforeUpload(file) {
      let fileType = file.name.split(".");
      let index = ["csv"].indexOf(fileType[fileType.length - 1]);
      if (index === -1) {
        this.$message({
          message: "请选择正确的文件类型",
          type: "warning",
        });
        return false;
      } else {
        return true;
      }
    },
    getFileList() {
      this.fileList = null;
      fetchStopWordFileList({
        pageNumber: this.pagination.page,
        pageSize: this.pagination.pageSize,
      }).then(
        (response) => {
          this.fileList = response.data.data;
          this.pagination.total = response.data.totalCount || 0;
          return true;
        },
        () => {
          this.fileList = [];
          return false;
        }
      );
    },
    handleCurrentChange(page) {
      this.pagination.page = page;
      this.getFileList();
    },
    handleSizeChange(pageSize) {
      this.pagination.page = 1;
      this.pagination.pageSize = pageSize;
      this.getFileList();
    },
  },
};
</script>
<style lang="scss" scoped>
.con {
  width: 100%;
  margin: auto;
  .upload {
    width: 85%;
    text-align: right;
    margin: 0px auto 10px;
    .upload-demo {
      .tip-text {
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        .template-down {
          margin-top: 7px;
        }
      }
      width: 100%;
      ::v-deep {
        .el-upload {
          width: 100%;
        }
        .el-upload-dragger {
          width: 100%;
        }
      }
    }
  }

  .button {
    margin-top: 15px;
  }
}
</style>
