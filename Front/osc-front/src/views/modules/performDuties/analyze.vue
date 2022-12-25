<template>
  <div>
    <el-form ref="form" :inline="true" :model="form" label-width="80px">
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
          <el-option label="董事会" value="1"></el-option>
          <el-option label="专委会" value="2"></el-option>
          <el-option label="沟通会" value="3"></el-option>
          <el-option label="股东大会" value="4"></el-option>
          <el-option label="调研" value="5"></el-option>
          <el-option label="培训" value="6"></el-option>
          <el-option label="座谈" value="7"></el-option>
          <el-option label="其他" value="8"></el-option>
        </el-select>
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
            :key="item.name"
            :label="item.name"
            :value="item.name"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button size="small" type="primary">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table border :data="summaryDate" style="width: 500px">
      <el-table-column prop="name" label="名称"> </el-table-column>
      <el-table-column prop="value" label="值"> </el-table-column>
    </el-table>
    <el-table border :data="performTypeDate" style="width: 500px">
      <el-table-column prop="name" label="会议类型"> </el-table-column>
      <el-table-column prop="value" label="值"> </el-table-column>
    </el-table>
    <el-table border :data="matterTypeDate" style="width: 500px">
      <el-table-column prop="name" label="事项类型"> </el-table-column>
      <el-table-column prop="value" label="值"> </el-table-column>
    </el-table>
    <el-table border :data="matterTypeSuggestDate" style="width: 500px">
      <el-table-column prop="name" label="事项类型"> </el-table-column>
      <el-table-column prop="opinionNumber" label="意见数"> </el-table-column>
      <el-table-column prop="suggestNumber" label="建议数"> </el-table-column>
    </el-table>
    <el-table border :data="matterTypeSuggestAduptDate" style="width: 500px">
      <el-table-column prop="name" label="事项类型"> </el-table-column>
      <el-table-column prop="opinionNumber" label="被采纳意见数">
      </el-table-column>
      <el-table-column prop="suggestNumber" label="被采纳建议数">
      </el-table-column>
    </el-table>
    <div class="pre" id="pre"></div>
    <div class="bar" id="bar"></div>
  </div>
</template>
<script>
import * as echarts from "echarts";
const directorOptions = [
  { name: "张三" },
  { name: "王五" },
  { name: "李四" },
  { name: "小明" },
];
export default {
  data() {
    return {
      directorOptionsList: directorOptions,
      form: {
        date: "",
        directorSelected: [],
        discussionType: "",
      },
      matterTypeSuggestDate: [
        {
          name: "议案",
          opinionNumber: "300",
          suggestNumber: "200",
        },
        {
          name: "汇报",
          opinionNumber: "200",
          suggestNumber: "300",
        },
      ],
      matterTypeSuggestAduptDate: [
        {
          name: "议案",
          opinionNumber: "150",
          suggestNumber: "100",
        },
        {
          name: "汇报",
          opinionNumber: "100",
          suggestNumber: "150",
        },
      ],
      performTypeDate: [
        {
          name: "董事会",
          value: "100",
        },
        {
          name: "专委会",
          value: "100",
        },
        {
          name: "沟通会",
          value: "100",
        },
        {
          name: "股东大会",
          value: "100",
        },
        {
          name: "调研会",
          value: "100",
        },
        {
          name: "培训会",
          value: "100",
        },
        {
          name: "座谈会",
          value: "50",
        },
        {
          name: "其他",
          value: "150",
        },
      ],
      matterTypeDate: [
        {
          name: "议案",
          value: "300",
        },
        {
          name: "汇报",
          value: "600",
        },
      ],
      summaryDate: [
        {
          name: "会议召开次数",
          value: "1000",
        },
        {
          name: "参会会议次数",
          value: "800",
        },
      ],
      summaryDatePic: [
        {
          name: "未参会会议次数",
          value: "200",
        },
        {
          name: "参会会议次数",
          value: "800",
        },
      ],
    };
  },
  mounted() {
    this.getpreData();
    this.getbarData();
  },
  methods: {
    init() {
      let keys = this.objectKeys(this.$route.params);
      if (keys.length > 0) {
        let { discussionType, date, directorList, questionList, name } =
          this.$route.params;
        this.directorList.splice(0, 0, ...directorList);
        this.form.discussionType = discussionType;
        this.form.date = date;
        this.form.name = name;
        let array = [];
        questionList.forEach((item, index) => {
          console.log(item, index);
          item.opinionInfo.forEach((opinionItem) => {
            let obj = {
              matter: item.matter,
              name: item.name,
              directorList: item.directorList,
              fileList: item.fileList,
              flogIndex: index,
              opinionInfoSingle: {
                director: opinionItem.director,
                opinion: opinionItem.opinion,
                suggest: opinionItem.suggest,
                adoption: opinionItem.adoption,
              },
            };
            array.push(obj);
          });
        });
        this.questionList = this.questionList.concat(array);
        this.merage();
      }
    },
    getpreData() {
      let names = [];
      this.summaryDatePic.forEach((item) => {
        let { name } = item;
        names.push(name);
      });
      let option = {
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b}: {c} ({d}%)",
        },
        legend: {
          orient: "vertical",
          left: 10,
          top: 20,
          data: names,
        },
        series: [
          {
            name: "统计",
            type: "pie",
            radius: ["0%", "70%"],
            avoidLabelOverlap: false,
            label: {
              show: false,
            },
            emphasis: {
              label: {
                show: false,
              },
            },
            labelLine: {
              show: false,
            },
            data: this.summaryDatePic,
          },
        ],
      };
      let chartDom = document.getElementById("pre");
      let myChart = echarts.init(chartDom);
      myChart.setOption(option);
    },
    getbarData() {
      let names = [];
      this.summaryDatePic.forEach((item) => {
        let { name } = item;
        names.push(name);
      });
      let option = {
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "shadow",
          },
        },
        legend: {},
        grid: {
          left: "3%",
          right: "4%",
          bottom: "3%",
          containLabel: true,
        },
        yAxis: {
          type: "value",
          boundaryGap: [0, 0.01],
        },
        xAxis: {
          type: "category",
          data: ["议案", "汇报"],
        },
        series: [
          {
            name: "意见数",
            type: "bar",
            data: [300, 200],
          },
          {
            name: "建议数",
            type: "bar",
            data: [200, 300],
          },
        ],
      };
      let chartDom = document.getElementById("bar");
      let myChart = echarts.init(chartDom);
      myChart.setOption(option);
    },
  },
};
</script>
<style lang="scss" scoped>
.pre {
  width: 500px;
  height: 300px;
}
.bar {
  width: 500px;
  height: 300px;
}
.el-table {
  margin: 0 0 10px;
}
</style>
