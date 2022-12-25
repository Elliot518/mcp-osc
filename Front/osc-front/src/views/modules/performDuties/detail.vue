<template>
  <div style="width: 100%">
    <el-form
      class="search"
      ref="form"
      :inline="true"
      :model="form"
      label-width="80px"
    >
      <el-form-item label="时间段">
        <el-date-picker
          size="small"
          v-model="form.date"
          type="daterange"
          value-format="yyyy-MM-dd"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item
        label="会议类型"
        prop="discussionType"
        style="display: inline-block"
      >
        <el-select
          size="small"
          v-model="form.discussionType"
          clearable
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
          placeholder="请输入会议名称"
          size="small"
          v-model="form.name"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item label="参会董事">
        <el-select
          size="small"
          v-model="form.directorSelected"
          multiple
          collapse-tags
          placeholder="请选择"
        >
          <el-option
            v-for="item in directorOptionsList"
            :key="item.id"
            :label="item.value"
            :value="item.id"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button size="small" type="primary">查询</el-button>
        <el-button size="small">导出</el-button>
      </el-form-item>
    </el-form>
    <el-table
      style="width: 100%"
      :span-method="objectSpanMethod"
      :data="performDutiesList"
      v-if="performDutiesList.length > 0"
    >
      <el-table-column align="center" width="95px" prop="date" label="日期">
      </el-table-column>
      <el-table-column align="center" width="95px" prop="name" label="名称">
      </el-table-column>
      <el-table-column align="center" width="78px" label="会议类型">
        <template slot-scope="scope">
          <transform-label
            :selectValue="scope.row.discussionType"
            :selectArray="mettingTypeList"
          ></transform-label>
        </template>
      </el-table-column>
      <el-table-column align="center" label="参会董事">
        <template slot-scope="scope">
          <span v-for="(el, index) in scope.row.directorList" :key="index">
            <span v-if="index !== scope.row.directorList.length - 1"
              >{{ transformName(el) }}、</span
            >
            <span v-else>{{ transformName(el) }}</span>
          </span>
        </template>
      </el-table-column>
      <el-table-column label="审议事项" align="center">
        <el-table-column align="center" width="78px" label="事项类型">
          <template slot-scope="scope">
            <transform-label
              :selectValue="scope.row.questionInfo.matter"
              :selectArray="matterSelect"
            ></transform-label>
          </template>
        </el-table-column>
        <el-table-column header-align="center" align="left" label="附件">
          <template slot-scope="scope">
            <p
              v-for="(el, index) in scope.row.questionInfo.fileList"
              :key="index"
            >
              {{ el.name }}
            </p>
          </template>
        </el-table-column>
        <el-table-column align="center" width="78px" label="参会董事">
          <template slot-scope="scope">
            <div>
              {{ transformName(scope.row.questionInfo.opinionInfo.director) }}
            </div>
          </template>
        </el-table-column>
        <el-table-column header-align="center" align="left" label="意见">
          <template slot-scope="scope">
            <div>{{ scope.row.questionInfo.opinionInfo.opinion }}</div>
          </template>
        </el-table-column>
        <el-table-column header-align="center" align="left" label="建议">
          <template slot-scope="scope">
            <div>{{ scope.row.questionInfo.opinionInfo.suggest }}</div>
          </template>
        </el-table-column>
        <el-table-column align="center" width="92px" label="是否被采纳">
          <template slot-scope="scope">
            <div>
              <span v-if="scope.row.questionInfo.opinionInfo.adoption">是</span>
              <span v-else>否</span>
            </div>
          </template>
        </el-table-column>
      </el-table-column>
      <el-table-column align="center" width="58px" prop="status" label="状态">
      </el-table-column>
      <el-table-column align="center" width="68px" label="操作">
        <template slot-scope="scope">
          <el-tooltip class="item" effect="dark" content="编辑" placement="top">
            <i
              @click="handleEdit(scope.$index, scope.row)"
              class="iconfont icon-edit"
            ></i>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="删除" placement="top">
            <i
              @click="handleDel(scope.$index, scope.row)"
              class="iconfont icon-delete"
            ></i>
          </el-tooltip>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @current-change="handleCurrentChange"
      :current-page="pagination.page"
      :page-size="pagination.pageSize"
      layout="total, prev, pager, next, jumper"
      :total="pagination.total"
    >
    </el-pagination>
  </div>
</template>
<script>
import {
  fetchMeetingType,
  fetchMatterType,
  fetchDirectors,
} from "@/api/performDuties";
export default {
  data() {
    return {
      pagination: {
        page: 1,
        pageSize: 5,
        total: 1,
      },
      directorOptionsList: [],
      form: {
        date: "",
        directorSelected: [],
        discussionType: "",
        name: "",
      },
      typeArray: [],
      typePos: 0,
      questionobSpanArray: [],
      questionobSpanPos: 0,
      performDutiesList: [],
      performDutiesListInit: [],
      matterSelect: [],
      mettingTypeList: [],
    };
  },
  async created() {
    await this.getMatterType();
    await this.getMettingType();
    await this.getDirectors();
    let array = [];
    let cs = [
      {
        discussionType: "3",
        name: "战略会议1",
        date: "2021-11-04",
        status: "草稿",
        directorList: [1, 3],
        questionList: [
          {
            id: 0,
            matter: "1",
            directorList: [1, 3],
            fileList: [
              {
                status: "ready",
                name: "123.docx",
                size: 11068,
                percentage: 0,
                uid: 1635995725080,
                raw: {
                  uid: 1635995725080,
                },
              },
            ],
            opinionInfo: [
              {
                director: 1,
                opinion: "1",
                suggest: "2",
                adoption: true,
              },
              {
                director: 3,
                opinion: "3",
                suggest: "4",
                adoption: false,
              },
            ],
          },
          {
            id: 1,
            matter: "2",
            directorList: [3],
            fileList: [
              {
                status: "ready",
                name: "cs.docx",
                size: 11167,
                percentage: 0,
                uid: 1635995738688,
                raw: {
                  uid: 1635995738688,
                },
              },
            ],
            opinionInfo: [
              {
                director: 3,
                opinion: "5",
                suggest: "6",
                adoption: true,
              },
            ],
          },
        ],
        id: 0,
      },
      {
        id: 1,
        name: "战略会议2",
        discussionType: "1",
        status: "正式",
        date: "2021-11-02",
        directorList: [2, 4],
        questionList: [
          {
            id: 0,
            matter: "3",
            directorList: [2, 4],
            fileList: [
              {
                status: "ready",
                name: "cs.docx",
                size: 11167,
                percentage: 0,
                uid: 1635995870328,
                raw: {
                  uid: 1635995870328,
                },
              },
            ],
            opinionInfo: [
              {
                director: 2,
                opinion: "4",
                suggest: "5",
                adoption: true,
              },
              {
                director: 4,
                opinion: "6",
                suggest: "7",
                adoption: false,
              },
            ],
          },
        ],
      },
    ];
    this.performDutiesListInit = cs;
    console.log(JSON.stringify(cs));
    cs.forEach((item, index) => {
      console.log(index);
      item.questionList.forEach((quesItem, quesIndex) => {
        console.log(quesItem, quesIndex);
        quesItem.opinionInfo.forEach((option) => {
          let obj = {
            discussionType: item.discussionType,
            name: item.name,
            status: item.status,
            date: item.date,
            directorList: item.directorList,
            id: index,
            questionInfo: {
              matter: quesItem.matter,
              directorList: quesItem.directorList,
              fileList: quesItem.fileList,
              id: quesIndex,
              opinionInfo: {
                director: option.director,
                opinion: option.opinion,
                suggest: option.suggest,
                adoption: option.adoption,
              },
            },
          };
          array.push(obj);
        });
      });
    });
    this.performDutiesList = this.performDutiesList.concat(array);
    this.merage();
  },
  methods: {
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
          this.mettingTypeList = response.data || [];
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
          this.directorOptionsList = response.data || [];
        },
        () => {
          this.directorOptionsList = [];
        }
      );
    },
    transformName(id) {
      let directorName = this.directorOptionsList.find(
        (option) => option.id === id
      );
      return directorName && directorName.value;
    },
    handleCurrentChange(page) {
      this.pagination.page = page;
    },
    merageInit() {
      this.typeArray = [];
      this.typePos = 0;
      this.questionobSpanArray = [];
      this.questionobSpanPos = 0;
    },
    merage() {
      this.merageInit();
      //   typeArray: [],
      //   typePos: 0,
      //   questionobSpanArray: [],
      //   questionobSpanPos: 0,
      for (let i = 0; i < this.performDutiesList.length; i += 1) {
        if (i === 0) {
          // 第一行必须存在
          this.typeArray.push(1);
          this.typePos = 0;
          this.questionobSpanArray.push(1);
          this.questionobSpanPos = 0;
        } else {
          // 判断当前元素与上一个元素是否相同
          // 第一列
          if (
            this.performDutiesList[i].id === this.performDutiesList[i - 1].id
          ) {
            this.typeArray[this.typePos] += 1;
            this.typeArray.push(0);
          } else {
            this.typeArray.push(1);
            this.typePos = i;
          }
          //判断当前元素与上一个元素是否相同,eg：this.questionobSpanPos 是 this.questionobSpanArray序号
          //第二列
          if (
            this.performDutiesList[i].questionInfo.id ===
            this.performDutiesList[i - 1].questionInfo.id
          ) {
            this.questionobSpanArray[this.questionobSpanPos] += 1;
            this.questionobSpanArray.push(0);
          } else {
            this.questionobSpanArray.push(1);
            this.questionobSpanPos = i;
          }
        }
      }
    },
    objectSpanMethod({ rowIndex, columnIndex }) {
      if (
        columnIndex === 0 ||
        columnIndex === 1 ||
        columnIndex === 2 ||
        columnIndex === 3 ||
        columnIndex === 10 ||
        columnIndex === 11
      ) {
        const row1 = this.typeArray[rowIndex];
        const col1 = row1 > 0 ? 1 : 0;
        return {
          rowspan: row1,
          colspan: col1,
        };
      }
      if (columnIndex === 4 || columnIndex === 5) {
        const row1 = this.questionobSpanArray[rowIndex];
        const col1 = row1 > 0 ? 1 : 0;
        return {
          rowspan: row1,
          colspan: col1,
        };
      }
    },
    handleDel(index, rowData) {
      console.log(rowData);
    },
    handleEdit(index, rowData) {
      console.log(rowData);
      let editQuestion = this.performDutiesListInit.find(
        (item) => item.id === rowData.id
      );
      this.$router.push({
        name: "performDutiesEdit",
        params: editQuestion,
      });
    },
  },
};
</script>
<style lang="scss" scoped>
.icon-delete,
.icon-edit {
  font-size: 22px;
  color: #9d9d9d;
  cursor: pointer;
}
.search {
  ::v-deep {
    .el-form-item {
      width: 33%;
      margin-right: 0px;
    }
    .el-date-editor--daterange.el-input__inner {
      width: 300px;
    }
    .el-date-editor .el-range-separator {
      width: 10%;
    }
  }
}
</style>
