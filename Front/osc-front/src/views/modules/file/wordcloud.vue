<template>
  <div class="wordcloud">
    <div v-if="dataList.length > 0" class="wordcloud" v-loading="progress">
      <div class="wordcloud" ref="wordcloud"></div>
    </div>
    <div class="no-text" v-else>
      <i class="iconfont icon-zanwushuju"></i>
      <p>暂无数据</p>
    </div>
  </div>
</template>

<script>
import * as echarts from "echarts";
import "echarts-wordcloud/dist/echarts-wordcloud";
import "echarts-wordcloud/dist/echarts-wordcloud.min";
import { fetchWordCloud } from "@/api/file";
export default {
  name: "word-cloud",
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
    this.getWordCloudData(this.batch, this.number);
  },
  watch: {
    number: function (newWord, oldWord) {
      if (newWord !== oldWord) {
        this.getWordCloudData(this.batch, newWord);
      }
    },
  },
  methods: {
    getWordCloudData(batch, number) {
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
      let myChart = echarts.init(this.$refs.wordcloud);
      myChart.setOption({
        series: [
          {
            type: "wordCloud",
            //用来调整词之间的距离
            gridSize: 10,
            //用来调整字的大小范围
            // Text size range which the value in data will be mapped to.
            // Default to have minimum 12px and maximum 60px size.
            sizeRange: [14, 60],
            // Text rotation range and step in degree. Text will be rotated randomly in range [-90,                                                                             90] by rotationStep 45
            //用来调整词的旋转方向，，[0,0]--代表着没有角度，也就是词为水平方向，需要设置角度参考注释内容
            // rotationRange: [-45, 0, 45, 90],
            // rotationRange: [ 0,90],
            rotationRange: [0, 0],
            //随机生成字体颜色
            // maskImage: maskImage,
            textStyle: {
              normal: {
                color: function () {
                  return (
                    "rgb(" +
                    Math.round(Math.random() * 255) +
                    ", " +
                    Math.round(Math.random() * 255) +
                    ", " +
                    Math.round(Math.random() * 255) +
                    ")"
                  );
                },
              },
            },
            //位置相关设置
            // Folllowing left/top/width/height/right/bottom are used for positioning the word cloud
            // Default to be put in the center and has 75% x 80% size.
            left: "center",
            top: "center",
            right: null,
            bottom: null,
            width: "100%",
            height: "100%",
            //数据
            data: data,
          },
        ],
      });
      myChart.on("finished", function () {
        that.progress = false;
      });
    },
  },
};
</script>
<style lang="scss" scoped>
.wordcloud {
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
