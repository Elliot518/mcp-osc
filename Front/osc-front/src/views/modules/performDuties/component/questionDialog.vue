<template>
  <div class="dialog">
    <el-dialog
      :title="dialogTitle"
      :modal-append-to-body="false"
      :visible.sync="visible"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :before-close="beforeClose"
    >
      <el-form ref="form" :rules="rules" :model="form" label-width="120px">
        <el-form-item label="事项类型" prop="matter">
          <el-select
            size="small"
            v-model="form.matter"
            placeholder="请选择事项类型"
          >
            <el-option
              v-for="(item, index) in matterTypeList"
              :key="index"
              :label="item.value"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="附件上传">
          <div>
            <el-upload
              class="upload-demo"
              action="https://jsonplaceholder.typicode.com/posts/"
              :auto-upload="false"
              :on-change="changeFileList"
              :on-remove="removeFile"
              multiple
              :file-list="fileList"
            >
              <i class="iconfont icon-shangchuanfujian"></i>
              <div slot="tip" class="el-upload__tip">
                只能上传doc/docx/wps文件
              </div>
            </el-upload>
          </div>
        </el-form-item>
        <el-form-item class="required" label="参会董事" prop="directorList">
          <div class="chds-checkbox-group">
            <el-checkbox
              :indeterminate="isIndeterminate"
              v-model="checkAll"
              @change="handleCheckAllChange"
              >全选</el-checkbox
            >
            <el-checkbox-group
              v-model="directorList"
              @change="handleCheckedDirectorChange"
            >
              <el-checkbox
                v-for="(item, index) in directors"
                :label="item.id"
                :key="index"
                >{{ item.value }}</el-checkbox
              >
            </el-checkbox-group>
          </div>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="beforeClose">取 消</el-button>
        <el-button size="small" type="primary" @click="submit('form')"
          >确 定</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { fetchDirectors, fetchMatterType } from "@/api/performDuties";
export default {
  name: "question-dialog",
  props: {
    visible: {
      type: Boolean,
      default: false,
    },
    dialogTitle: {
      type: String,
      default: "",
    },
    directorListOption: {
      type: Array,
      default: () => [],
    },
    modeValue: {
      type: Object,
      default: () => {
        return {};
      },
    },
  },
  data() {
    let directorListValidator = (rule, value, callback) => {
      if (this.directorList && this.directorList.length > 0) {
        callback();
      } else {
        callback(new Error("请选择参会董事"));
      }
    };
    return {
      checkAll: false,
      directorList:
        Object.keys(this.modeValue).length > 0
          ? this.modeValue.directorList
          : [],
      directors: [],
      directorOptions: [],
      isIndeterminate: false,
      form: {
        matter:
          Object.keys(this.modeValue).length > 0 ? this.modeValue.matter : "",
      },
      matterTypeList: [],
      fileList:
        Object.keys(this.modeValue).length > 0 ? this.modeValue.fileList : [],
      rules: {
        matter: [{ required: true, message: "请选择日期", trigger: "change" }],
        directorList: [{ validator: directorListValidator, trigger: "change" }],
      },
    };
  },
  async created() {
    this.getMatterType();
    this.directorOptions = (await fetchDirectors()).data || [];
    console.log(this.directorOptions);
    //初始化参会董事
    let array = [];
    this.directorListOption.forEach((item) => {
      let optionItem = this.directorOptions.find(
        (option) => option.id === item
      );
      array.push(optionItem);
    });
    this.directors = array;
    //编辑的时候参会董事样式
    let keys = Object.keys(this.modeValue);
    if (keys.length > 0) {
      if (
        this.modeValue.directorList.length === this.directorListOption.length
      ) {
        this.checkAll = true;
      } else {
        this.isIndeterminate = true;
      }
    }
  },
  methods: {
    getMatterType() {
      fetchMatterType().then(
        (response) => {
          this.matterTypeList = response.data || [];
        },
        () => {
          this.matterTypeList = [];
        }
      );
    },
    handleCheckAllChange(val) {
      this.directorList = val ? this.directorListOption : [];
      this.isIndeterminate = false;
    },
    handleCheckedDirectorChange(value) {
      let checkedCount = value.length;
      this.checkAll = checkedCount === this.directors.length;
      this.isIndeterminate =
        checkedCount > 0 && checkedCount < this.directors.length;
    },
    beforeClose() {
      this.$emit("close", false);
    },
    beforeUpload(fileList) {
      let agree = fileList.some((item) => {
        let fileType = item.name.split(".");
        let index = ["doc", "docx", "wps"].indexOf(
          fileType[fileType.length - 1]
        );
        if (index === -1) {
          return true;
        } else {
          return false;
        }
      });

      if (agree) {
        this.$message({
          message: "请选择正确的文件类型",
          type: "warning",
        });
      }
      return !agree;
    },
    changeFileList(file, fileList) {
      this.fileList = [];
      this.fileList = fileList;
    },
    removeFile(file, fileList) {
      this.fileList = [];
      this.fileList = fileList;
    },
    submit(formName) {
      let that = this;
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let vailated = that.beforeUpload(that.fileList);
          if (vailated) {
            let obj = {
              matter: that.form.matter,
              directorList: that.directorList,
              fileList: that.fileList,
              id:
                that.modeValue.id || that.modeValue.id === 0
                  ? that.modeValue.id
                  : undefined,
            };
            console.log(obj);
            that.$emit("questionData", obj);
            that.$emit("close", false);
          }
        } else {
          return false;
        }
      });
    },
  },
};
</script>
<style lang="scss" scoped>
.dialog {
  .chds-checkbox-group {
    padding: 5px 10px;
    border: 1px solid #d9d9d9;
  }
  ::v-deep {
    .el-checkbox {
      line-height: 25px;
    }
    .el-upload.el-upload--text {
      line-height: 20px;
    }
    .el-upload__tip {
      line-height: 20px;
      margin-top: -8px;
    }
  }
  .icon-shangchuanfujian {
    font-size: 22px;
    color: #9d9d9d;
  }
  .icon-add {
    font-size: 20px;
    padding-left: 8px;
    color: #9d9d9d;
    cursor: pointer;
  }
  .icon-delete {
    font-size: 22px;
    color: #9d9d9d;
    cursor: pointer;
  }
}
</style>
