<template>
  <div class="con">
    <div class="upload">
      <el-upload
        ref="upload"
        class="upload-demo"
        drag
        multiple
        :action="actionUrl"
        :file-list="fileUploadlist"
        :auto-upload="false"
        :on-change="changeFileList"
        :on-remove="removeFile"
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          {{ $t("lang.file.dragFile") }}<em>{{ $t("lang.file.addFile") }}</em>
        </div>
        <div class="el-upload__tip" slot="tip">
          {{ $t("lang.file.fileTip") }}
        </div>
      </el-upload>
      <el-button size="small" class="button" type="primary" @click="submit">{{
        $t("lang.submit")
      }}</el-button>
    </div>
    <el-tabs
      v-model="activeName"
      type="card"
      @tab-remove="removeTab"
      @tab-click="handleClick"
    >
      <el-tab-pane label="文件列表" name="fileList">
        <el-table v-loading="!fileList" :data="fileList" style="width: 100%">
          <el-table-column
            prop="batchId"
            header-align="center"
            align="center"
            show-overflow-tooltip
            label="批次号"
          >
            <template slot-scope="scope">
              <el-link type="primary" @click="handleOpen(scope.row.batchId)">{{
                scope.row.batchId
              }}</el-link>
            </template>
          </el-table-column>
          <el-table-column
            prop="filesNum"
            align="center"
            show-overflow-tooltip
            width="80px"
            label="文件个数"
          >
          </el-table-column>
          <el-table-column
            prop="status"
            align="center"
            show-overflow-tooltip
            width="80px"
            label="状态"
          >
          </el-table-column>
          <!-- <el-table-column
            show-overflow-tooltip
            align="center"
            prop="completePercentage"
            label="进度"
          >
            <template slot-scope="scope">
              <el-progress
                v-if="scope.row.completePercentage > 50"
                :percentage="
                  scope.row.completePercentage > 100
                    ? 100
                    : scope.row.completePercentage
                "
                color="#67c23a"
              ></el-progress>
              <el-progress
                v-else
                :percentage="scope.row.completePercentage"
                color="#f56c6c"
              ></el-progress>
            </template>
          </el-table-column> -->
          <el-table-column
            show-overflow-tooltip
            align="center"
            prop="createTime"
            label="开始时间"
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
            prop="lastUpdate"
            label="结束时间"
          >
            <template slot-scope="scope">
              <interface-content
                :content="scope.row.lastUpdate"
              ></interface-content>
            </template>
          </el-table-column>
          <el-table-column
            show-overflow-tooltip
            width="120px"
            align="center"
            label="词云分析结果"
          >
            <template slot-scope="scope">
              <el-button
                :disabled="scope.row.status === '待解析'"
                size="mini"
                @click="handleView(scope.$index, scope.row, 'wordCloud')"
                >查看</el-button
              >
            </template>
          </el-table-column>
          <el-table-column
            show-overflow-tooltip
            width="120px"
            align="center"
            label="词频分析结果"
          >
            <template slot-scope="scope">
              <el-button
                :disabled="scope.row.status === '待解析'"
                size="mini"
                @click="handleView(scope.$index, scope.row, 'bar')"
                >查看</el-button
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
      </el-tab-pane>
      <el-tab-pane
        v-if="wordCloudVisibled"
        closable
        label="词云分析"
        name="wordCloud"
      >
        <div style="margin-bottom: 6px">
          <el-link type="primary" @click="handleOpen(wordCloudBatchName)">{{
            wordCloudBatchName
          }}</el-link>
        </div>
        <el-select
          style="width: 80px"
          v-model="wordNumber"
          size="small"
          placeholder="请选择数量"
        >
          <el-option
            v-for="item in numberOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
        <word-cloud
          v-if="activeName === 'wordCloud'"
          :batch="wordCloudBatchName"
          :number="wordNumber"
        ></word-cloud>
      </el-tab-pane>
      <el-tab-pane closable v-if="barVisibled" label="词频分析" name="bar">
        <div style="margin-bottom: 6px">
          <el-link type="primary" @click="handleOpen(barBatchName)">{{
            barBatchName
          }}</el-link>
        </div>
        <el-select
          style="width: 80px"
          size="small"
          v-model="barNumber"
          placeholder="请选择数量"
        >
          <el-option
            v-for="item in numberOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
        <bar
          v-if="activeName === 'bar'"
          :batch="barBatchName"
          :number="barNumber"
        ></bar>
      </el-tab-pane>
    </el-tabs>
    <custom-dialog
      v-if="fileListVisible"
      :visible="fileListVisible"
      :dialogTitle="fileListTitle"
      @close="(value) => (fileListVisible = value)"
    ></custom-dialog>
  </div>
</template>

<script>
import { fetchFileList, updateFile } from "@/api/file";
import CustomDialog from "./component/dialog.vue";
import WordCloud from "./wordcloud.vue";
import bar from "./bar.vue";

export default {
  name: "fileUpload",
  data() {
    return {
      wordCloudVisibled: false,
      barVisibled: false,
      fileListVisible: false,
      wordCloudBatchName: "",
      barBatchName: "",
      fileListTitle: "",
      fileList: null,
      activeName: "fileList",
      pagination: {
        page: 1,
        pageSize: 5,
        total: 0,
      },
      fileUploadlist: [],
      batchName: "",
      completePercentage: 100,
      numberOptions: [
        {
          value: 20,
          label: "20",
        },
        {
          value: 40,
          label: "40",
        },
        {
          value: 60,
          label: "60",
        },
        {
          value: 80,
          label: "80",
        },
      ],
      wordNumber: 40,
      barNumber: 40,
    };
  },
  components: { CustomDialog, WordCloud, bar },
  created() {
    this.getFileList();
  },
  computed: {
    actionUrl() {
      return process.env.VUE_APP_GVA_BASE_API
        ? process.env.VUE_APP_GVA_BASE_API + "/upload/word"
        : "/api/upload/word";
    },
  },
  methods: {
    removeTab(targetName) {
      this.showMain(targetName, false);
      this.activeName = "fileList";
    },
    handleView(index, rowData, activeName) {
      this.activeName = activeName;
      this.showMain(activeName, true, rowData.batchId);
    },
    showMain(type, flog, batchId) {
      switch (type) {
        case "wordCloud":
          flog ? (this.wordCloudBatchName = batchId) : "";
          this.wordCloudVisibled = flog;
          break;
        case "bar":
          flog ? (this.barBatchName = batchId) : "";
          this.barVisibled = flog;
          break;
        default:
          break;
      }
    },
    handleOpen(batchId) {
      this.fileListTitle = batchId;
      this.fileListVisible = true;
    },
    handleClick(tab) {
      this.activeName = tab.name;
    },
    beforeUpload(fileList) {
      let agree = fileList.some((item) => {
        let fileType = item.name.split(".");
        let index = ["xls", "xlsx"].indexOf(fileType[fileType.length - 1]);
        console.log(fileType[fileType.length - 2].length);
        let strLength = fileType[fileType.length - 2].length;
        if (strLength > 100) {
          this.$message({
            message: "文件名应该小于等于100,请修改后上传",
            type: "warning",
          });
          return true;
        } else if (index === -1) {
          this.$message({
            message: "请选择正确的文件类型",
            type: "warning",
          });
          return true;
        } else {
          return false;
        }
      });

      return !agree;
    },
    changeFileList(file, fileList) {
      this.fileUploadlist = [];
      this.fileUploadlist = fileList;
    },
    removeFile(file, fileList) {
      this.fileUploadlist = [];
      this.fileUploadlist = fileList;
    },
    submit() {
      if (this.fileUploadlist.length <= 0) {
        this.$message({
          message: "请选择文件",
          type: "warning",
        });
      } else {
        let vailated = this.beforeUpload(this.fileUploadlist);
        if (vailated) {
          let formData = new FormData();
          this.fileUploadlist.forEach((files) => {
            formData.append("file", files.raw);
          });
          updateFile(formData).then(
            (response) => {
              console.log(response);
              this.$refs.upload.clearFiles();
              this.fileUploadlist = [];
              this.pagination.page = 1;
              this.getFileList();
            },
            () => {
              this.$refs.upload.clearFiles();
              this.fileUploadlist = [];
            }
          );
        } else {
          this.$refs.upload.clearFiles();
          this.fileUploadlist = [];
        }
      }
    },
    getFileList() {
      this.fileList = null;
      fetchFileList({
        pageNumber: this.pagination.page,
        pageSize: this.pagination.pageSize,
      }).then(
        (response) => {
          this.fileList = response.data.data;
          this.pagination.total = response.data.totalCount;
        },
        () => {
          this.fileList = [];
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
    margin: auto;
    .upload-demo {
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
