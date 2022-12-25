<template>
  <div>
    <h2>各版本功能列表</h2>
    <div class="version-list">
      <div>
        <h3>V1.5</h3>
        <ul>
          <li>批量文件上传</li>
          <li>定时批量队列任务</li>
          <li>批量队列对应文件异步解析</li>
          <li>批量文件解析后内容分词</li>
          <li>批量文件分词后内容词频统计</li>
          <li>批量队列状态查询</li>
          <li>批量队列对应词云展示</li>
          <li>系统内置停用词</li>
          <li>帮助页面展示版本功能列表</li>
        </ul>
      </div>
      <!-- <div>
        <h3>V2.0</h3>
        <ul>
          <li>批量队列进度展示</li>
          <li>单条队列对应批量文件列表状态展示</li>
          <li>定时任务中心展示词云任务状态</li>
          <li>任务防重守护</li>
          <li>用户自定义停用词上传及启用</li>
        </ul>
      </div> -->
    </div>
  </div>
</template>
<script>
export default {};
</script>
<style lang="scss" scoped>
.version-list {
  padding: 0px 8px;
}
h2 {
  margin-bottom: 8px;
}
ul {
  padding: 2px 30px;
}
</style>
