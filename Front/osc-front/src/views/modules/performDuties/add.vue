<template>
  <div class="add">
    <el-form ref="form" :rules="rules" :model="form" label-width="95px">
      <el-form-item label="日期" prop="date" style="display: inline-block">
        <el-date-picker
          size="small"
          type="date"
          placeholder="选择日期"
          v-model="form.date"
          value-format="yyyy-MM-dd"
          style="width: 220.4px"
        ></el-date-picker>
      </el-form-item>
      <el-form-item
        label="会议类型"
        prop="discussionType"
        style="display: inline-block"
      >
        <el-select
          size="small"
          v-model="form.discussionType"
          placeholder="请选择会议类型"
        >
          <el-option
            v-for="(item, index) in mettingTypeList"
            :key="index"
            :label="item.value"
            :value="item.id"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="会议名称" prop="name">
        <el-input
          size="small"
          placeholder="请输入会议名称"
          v-model="form.name"
          clearable
        ></el-input>
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
              :disabled="item.disabled"
              >{{ item.value }}</el-checkbox
            >
          </el-checkbox-group>
        </div>
      </el-form-item>
      <el-form-item class="required" label="审议事项" prop="questionList">
        <div>
          <div>
            <i @click="choiceQuestion" class="iconfont icon-add"></i>
          </div>
          <div v-if="questionList.length > 0">
            <table class="laura-table" cellspacing="0">
              <thead>
                <tr>
                  <th style="width: 50px" class="header"></th>
                  <th style="width: 78px" class="header">事项类型</th>
                  <th class="header">附件</th>
                  <th class="header">参会董事</th>
                  <th style="width: 68px" class="header">操作</th>
                </tr>
              </thead>
              <template v-for="(row, index) in questionList">
                <tr :key="index" class="laura-table__row expanded">
                  <td>
                    <i
                      @click="clickExpanded($event)"
                      class="iconfont icon-jiantou"
                    ></i>
                  </td>
                  <td>
                    <transform-label
                      :selectValue="row.matter"
                      :selectArray="matterSelect"
                    ></transform-label>
                  </td>
                  <td>
                    <p v-for="(el, fileindex) in row.fileList" :key="fileindex">
                      {{ el.name }}
                    </p>
                  </td>
                  <td>
                    <span
                      v-for="(el, directorindex) in row.directorList"
                      :key="directorindex"
                    >
                      <span v-if="directorindex !== row.directorList.length - 1"
                        >{{ transformName(el) }}、</span
                      >
                      <span v-else>{{ transformName(el) }}</span>
                    </span>
                  </td>
                  <td>
                    <el-tooltip
                      class="item"
                      effect="dark"
                      content="编辑"
                      placement="top"
                    >
                      <i
                        @click="questionEdit(index, row)"
                        class="iconfont icon-edit"
                      ></i>
                    </el-tooltip>
                    <el-tooltip
                      class="item"
                      effect="dark"
                      content="删除"
                      placement="top"
                    >
                      <i
                        @click="questionDel(index, row)"
                        class="iconfont icon-delete"
                      ></i>
                    </el-tooltip>
                  </td>
                </tr>
                <tr
                  :key="'children' + index"
                  class="laura-table__row laura-table__expanded-cell-none"
                >
                  <td colSpan="5">
                    <table
                      style="width: 80%; margin: auto"
                      class="laura-table"
                      cellspacing="0"
                    >
                      <thead>
                        <tr>
                          <th style="width: 78px" class="header">参会董事</th>
                          <th class="header">意见</th>
                          <th class="header">建议</th>
                          <th style="width: 92px" class="header">是否被采纳</th>
                        </tr>
                      </thead>
                      <tr
                        class="laura-table__row"
                        v-for="(opinion, opinionIndex) in row.opinionInfo"
                        :key="opinionIndex"
                      >
                        <td>{{ transformName(opinion.director) }}</td>
                        <td class="padding10">
                          <el-input
                            type="textarea"
                            :rows="2"
                            placeholder="请输入意见"
                            v-model="opinion.opinion"
                          >
                          </el-input>
                        </td>
                        <td class="padding10">
                          <el-input
                            type="textarea"
                            :rows="2"
                            placeholder="请输入建议"
                            v-model="opinion.suggest"
                          >
                          </el-input>
                        </td>
                        <td class="lineheight20">
                          <div>
                            <div>
                              <el-radio v-model="opinion.adoption" :label="true"
                                >是</el-radio
                              >
                            </div>
                            <div>
                              <el-radio
                                v-model="opinion.adoption"
                                :label="false"
                                >否</el-radio
                              >
                            </div>
                          </div>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
              </template>
            </table>
          </div>
        </div>
      </el-form-item>
      <el-form-item>
        <el-button
          size="small"
          v-if="objectKeys(this.$route.params).length > 0"
          @click="HandleCancel()"
          >取消</el-button
        >
        <el-button size="small" type="primary" @click="onSubmit('form')"
          >提交</el-button
        >
        <el-button size="small" @click="onSubmit('form')">保存</el-button>
      </el-form-item>
    </el-form>
    <question-dialog
      v-if="questionVisible"
      :directorListOption="directorList"
      :visible="questionVisible"
      :dialogTitle="questionTitle"
      @questionData="questionListTransform"
      @close="(value) => (questionVisible = value)"
      :modeValue="editQuestionData"
    ></question-dialog>
  </div>
</template>
<script>
import {
  fetchMeetingType,
  fetchDirectors,
  fetchMatterType,
  fetchGenericId,
} from "@/api/performDuties";
import TransformLabel from "../../../components/select/transformLabel.vue";
import questionDialog from "./component/questionDialog.vue";
export default {
  name: "directorEdit",
  data() {
    let directorListValidator = (rule, value, callback) => {
      if (this.directorList && this.directorList.length > 0) {
        callback();
      } else {
        callback(new Error("请选择参会董事"));
      }
    };
    let questionListValidator = (rule, value, callback) => {
      if (this.questionList && this.questionList.length > 0) {
        callback();
      } else {
        callback(new Error("请添加审议事项"));
      }
    };
    return {
      rules: {
        date: [{ required: true, message: "请选择日期", trigger: "change" }],
        name: [
          { required: true, message: "请输入会议名称", trigger: "change" },
        ],
        discussionType: [
          { required: true, message: "请选择会议类型", trigger: "change" },
        ],
        directorList: [{ validator: directorListValidator, trigger: "change" }],
        questionList: [{ validator: questionListValidator, trigger: "change" }],
      },
      checkAll: false,
      directorList: [],
      directors: [],
      directorsId: [],
      isIndeterminate: false,
      form: {
        discussionType: "",
        date: "",
        name: "",
      },
      questionVisible: false,
      questionTitle: "审议事项",
      questionList: [],
      editQuestionData: {},
      matterSelect: [],
      mettingTypeList: [],
      genericId: "",
    };
  },
  components: { questionDialog, TransformLabel },
  async created() {
    this.genericId = (await fetchGenericId()).data || "";
    this.getMettingType();
    this.getDirectors();
    this.getMatterType();
  },
  methods: {
    init() {
      let keys = this.objectKeys(this.$route.params);
      if (keys.length > 0) {
        let { discussionType, date, directorList, questionList, name } =
          this.$route.params;
        this.directorList.splice(0, 0, ...directorList);
        this.form.discussionType = discussionType;
        this.isIndeterminate = true;
        this.form.date = date;
        this.form.name = name;
        this.questionList = questionList;
        this.directorDisabled();
      }
    },
    //获取事项类型
    getMatterType() {
      fetchMatterType().then(
        (response) => {
          this.matterSelect = response.data || [];
        },
        () => {
          this.matterSelect = [];
        }
      );
    },
    //获取会议类型
    getMettingType() {
      fetchMeetingType().then(
        (response) => {
          if (response.data && response.data.length > 0) {
            this.mettingTypeList = response.data.filter(
              (item) => item.id !== "1" && item.id !== "2"
            );
          } else {
            this.mettingTypeList = [];
          }
        },
        () => {
          this.mettingTypeList = [];
        }
      );
    },
    //获取参会董事
    getDirectors() {
      fetchDirectors().then(
        (response) => {
          this.directors = response.data || [];
          this.directors.forEach((item) => {
            this.directorsId.push(item.id);
          });
        },
        () => {
          this.directors = [];
        }
      );
    },
    //根据审议事项禁用参会董事
    directorDisabled() {
      let matterDirector = this.getArrayNorepeat(
        this.questionList,
        "directorList"
      );
      let array = [];
      let self = this;
      matterDirector.forEach((item) => {
        let findIndex = self.directors.findIndex((direcItem) => {
          return item === direcItem.id;
        });
        if (findIndex !== -1) {
          array.push(findIndex);
        }
      });
      self.directors.map((item, index) => {
        let keyIndex = array.indexOf(index);
        if (keyIndex !== -1) {
          item.disabled = true;
          return item;
        } else {
          item.disabled = false;
          return item;
        }
      });
    },
    getArrayNorepeat(newArray, key) {
      let array = [];
      newArray.forEach((item) => {
        array = array.concat(item[key]);
      });
      return Array.from(new Set(array));
    },
    transformName(id) {
      let directorName = this.directors.find((option) => option.id === id);
      return directorName && directorName.value;
    },
    clickExpanded(event) {
      let currentTarget = event.currentTarget;
      let target = currentTarget.parentNode.parentNode.nextSibling;
      let classList = target.classList;
      console.log(classList);
      let flog = classList.contains("laura-table__expanded-cell-none");
      if (flog) {
        classList.remove("laura-table__expanded-cell-none");
        classList.add("laura-table__expanded-cell-block");
        currentTarget.classList.remove("icon-jiantou");
        currentTarget.classList.add("icon-xiajiantou");
      } else {
        classList.remove("laura-table__expanded-cell-block");
        classList.add("laura-table__expanded-cell-none");
        currentTarget.classList.remove("icon-xiajiantou");
        currentTarget.classList.add("icon-jiantou");
      }
      if (target === "none") {
        target = "block";
      } else {
        target = "none";
      }
    },
    objectKeys(data) {
      return Object.keys(data);
    },
    HandleCancel() {
      this.$router.push({
        name: "performDutiesDetail",
      });
    },
    handleCheckAllChange(val) {
      let matterDirector = this.getArrayNorepeat(
        this.questionList,
        "directorList"
      );
      this.directorList = val ? this.directorsId : matterDirector;
      this.isIndeterminate = false;
    },
    handleCheckedDirectorChange(value) {
      let checkedCount = value.length;
      this.checkAll = checkedCount === this.directors.length;
      this.isIndeterminate =
        checkedCount > 0 && checkedCount < this.directors.length;
    },
    questionListTransform(value) {
      let questItem = this.questionList.find((item) => item.id === value.id);
      let questItemIndex = this.questionList.findIndex(
        (item) => item.id === value.id
      );
      let obj = {
        matter: value.matter,
        directorList: value.directorList,
        fileList: value.fileList,
      };
      obj.opinionInfo = [];
      value.directorList.forEach((item) => {
        let that = this;
        let opinionInfoSingle = {};
        opinionInfoSingle.director = item;
        if (value.id || value.id === 0) {
          obj.id = value.id;
          let opinionInfoItem = questItem.opinionInfo.find(
            (opinionitem) => opinionitem.director === item
          );
          opinionInfoSingle.opinion = opinionInfoItem
            ? opinionInfoItem.opinion
            : "";
          opinionInfoSingle.suggest = opinionInfoItem
            ? opinionInfoItem.suggest
            : "";
          opinionInfoSingle.adoption = opinionInfoItem
            ? opinionInfoItem.adoption
            : "";
        } else {
          opinionInfoSingle.opinion = "";
          opinionInfoSingle.suggest = "";
          opinionInfoSingle.adoption = "";
          obj.id = that.questionList.length;
        }
        obj.opinionInfo.push(opinionInfoSingle);
      });
      if (value.id || value.id === 0) {
        this.questionList.splice(questItemIndex, 1, obj);
      } else {
        this.questionList.push(obj);
      }
      this.directorDisabled();
    },
    onSubmit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let array = [];
          this.questionList.forEach((item, index) => {
            if (index === 0) {
              let questObj = item;
              questObj.opinionInfo = [];
              questObj.opinionInfo.push(item.opinionInfoSingle);
              array.push(questObj);
            } else {
              let temIndex = array.findIndex((element) => {
                return element.flogIndex === item.flogIndex;
              });
              if (temIndex !== -1) {
                array[temIndex].opinionInfo.push(item.opinionInfoSingle);
              } else {
                let questObj = item;
                questObj.opinionInfo = [];
                questObj.opinionInfo.push(item.opinionInfoSingle);
                array.push(questObj);
              }
            }
          });
          console.log(JSON.stringify(array));
          let questionTemList = [];
          array.forEach((item) => {
            let { matter, directorList, fileList, opinionInfo } = item;
            questionTemList.push({
              matter,
              directorList,
              fileList,
              opinionInfo,
            });
          });
          let obj = {
            discussionType: this.form.discussionType,
            date: this.form.date,
            directorList: this.directorList,
            questionList: questionTemList,
          };
          console.log(JSON.stringify(obj));
          this.$router.push({
            name: "performDutiesDetail",
          });
        } else {
          return false;
        }
      });
    },
    choiceQuestion() {
      if (this.directorList.length > 0) {
        this.editQuestionData = {};
        this.questionVisible = true;
      } else {
        this.questionVisible = false;
        this.$message({
          message: "请选择参会董事",
          type: "warning",
        });
      }
    },
    questionDel(index, rowData) {
      console.log(index, rowData);
      let flogIndex = rowData.flogIndex;
      this.questionList = this.questionList.filter(
        (item) => item.flogIndex !== flogIndex
      );
    },
    questionEdit(index, rowData) {
      let obj = {
        matter: rowData.matter,
        fileList: rowData.fileList,
        directorList: rowData.directorList,
        id: index,
      };
      this.editQuestionData = obj;
      this.questionVisible = true;
    },
  },
};
</script>
<style lang="scss" scoped>
.add {
  .laura-table {
    width: 100%;
    border-top: 1px solid #dfe6ec;
    border-left: 1px solid #dfe6ec;
    tr:hover > td {
      background-color: #f5f7fa;
    }
    .laura-table__row > td:hover {
      background-color: transparent !important;
    }
    .header {
      line-height: 18px;
      padding: 12px 10px;
      text-align: center;
      background: #f5f7fa;
      color: #909399;
    }
    th,
    td {
      border-bottom: 1px solid #dfe6ec;
      border-right: 1px solid #dfe5ec;
    }
    .laura-table__row {
      td {
        padding: 1.5px 10px;
        text-align: center;
        color: #606266;
      }
    }
    .laura-table__expanded-cell-block > td {
      padding: 20px 15px;
    }
    .laura-table__row .lineheight20 div {
      line-height: 20px;
    }
    .laura-table__row .padding10 {
      padding: 10px;
    }
    .icon-jiantou,
    .icon-xiajiantou {
      cursor: pointer;
    }
    .laura-table__expanded-cell-none {
      display: none;
    }
    .laura-table__expanded-cell-block {
      display: table-row;
    }
  }
  .chds-checkbox-group {
    padding: 5px 10px;
    border: 1px solid #d9d9d9;
  }
  ::v-deep {
    .el-checkbox {
      line-height: 25px;
    }
    .el-table__header th {
      background: #f5f7fa;
    }
    .el-table__fixed-right {
      height: 100% !important;
    }
    .el-form-item.required > .el-form-item__label:after {
      content: "*";
      color: #ff4949;
      margin-left: 4px;
    }
  }
  .questionListChild {
    ::v-deep {
      .el-table__header {
        border-left: 1px solid #dfe6ec;
        border-top: 1px solid #dfe6ec;
      }
      .el-table__body-wrapper {
        border-left: 1px solid #dfe6ec;
        border-right: 1px solid #dfe6ec;
      }
      .el-table__fixed-right {
        height: 100% !important;
      }
    }
  }
  .icon-add {
    font-size: 20px;
    padding-left: 8px;
    color: #9d9d9d;
    cursor: pointer;
  }
  .icon-delete,
  .icon-edit {
    font-size: 22px;
    color: #9d9d9d;
    cursor: pointer;
  }
  .icon-baocun {
    font-size: 15px;
    color: #9d9d9d;
    cursor: pointer;
  }
}
</style>
