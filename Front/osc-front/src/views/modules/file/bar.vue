<template>
  <div class="bar">
    <div v-if="dataList.length > 0" class="bar" v-loading="progress">
      <div class="bar" id="bar"></div>
    </div>
    <div class="no-text" v-else>
      <i class="iconfont icon-zanwushuju"></i>
      <p>暂无数据</p>
    </div>
  </div>
</template>

<script>
import * as echarts from "echarts";
import { fetchWordCloud } from "@/api/file";
export default {
  name: "bar",
  data() {
    return {
      progress: true,
      dataList: [],
    };
  },
  props: {
    batch: {
      type: String,
      default: "1",
    },
    number: {
      type: Number,
      default: 40,
    },
  },
  mounted() {
    this.getBarData(this.batch, this.number);
  },
  watch: {
    number: function (newWord, oldWord) {
      if (newWord !== oldWord) {
        this.getBarData(this.batch, newWord);
      }
    },
  },
  methods: {
    getBarData(batch, number) {
      this.progress = true;
      fetchWordCloud(batch).then(
        (response) => {
          this.dataList = response.data;
          if (this.dataList.length > 0) {
            this.$nextTick(() => {
              this.initchart(response.data.slice(0, number));
            });
          }
        },
        () => {
          this.progress = false;
        }
      );
    },
    initchart(data) {
      let that = this;
      let names = [];
      let values = [];
      data.forEach((item) => {
        let { name, value } = item;
        names.push(name);
        values.push(value);
      });
      let option = {
        xAxis: {
          type: "category",
          data: names,
          show: true,
          axisTick: {
            alignWithLabel: true,
          },
        },
        yAxis: {
          type: "value",
        },
        series: [
          {
            name: "出现次数",
            data: values,
            type: "bar",
          },
        ],
        tooltip: {
          trigger: "axis",
          axisPointer: {
            // 坐标轴指示器，坐标轴触发有效
            type: "shadow", // 默认为直线，可选为：'line' | 'shadow'
          },
        },
        dataZoom: [
          {
            show: true,
            start: 0,
            end: 20,
          },
        ],
      };
      let chartDom = document.getElementById("bar");
      let myChart = echarts.init(chartDom);
      myChart.setOption(option);
      myChart.on("finished", function () {
        that.progress = false;
      });
    },
  },
};
</script>
<style lang="scss" scoped>
.bar {
  width: 100%;
  height: 300px;
  margin: auto;
}
.no-text {
  text-align: center;
  margin-top: 100px;
  color: #9d9d9d;
  i {
    font-size: 50px;
    margin-bottom: 8px;
    display: inline-block;
  }
}
</style>
