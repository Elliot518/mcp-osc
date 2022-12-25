<template>
  <div>
    <div id="histogram"></div>
  </div>
</template>

<script>
import * as echarts from "echarts";
import { fetchList } from "@/api/article";
export default {
  name: "App",
  data() {
    return {
      list: null,
      total: 0,
      listQuery: {
        page: 1,
        limit: 20,
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      fetchList(this.listQuery).then((response) => {
        this.list = response.data.items;
        this.total = response.data.total;
      });
    },
  },
  mounted() {
    let option = {
      xAxis: {
        type: "category",
        data: ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"],
      },
      yAxis: {
        type: "value",
      },
      series: [
        {
          data: [120, 200, 150, 80, 70, 110, 130],
          type: "bar",
        },
      ],
    };
    let chartDom = document.getElementById("histogram");
    let myChart = echarts.init(chartDom);
    myChart.setOption(option);
  },
};
</script>
<style scoped>
#histogram {
  width: 500px;
  height: 380px;
}
</style>
