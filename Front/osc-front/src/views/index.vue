<template>
  <div>
    <div class="menu" v-for="(item, index) in menu" :key="index">
      <router-link :to="item.url">{{ item.name }}</router-link>
    </div>
    <router-view></router-view>
  </div>
</template>
<script>
export default {
  name: "index",
  data() {
    return {
      menu: [
        {
          name: "云词",
          url: "/wordcloud",
        },
        {
          name: "mock",
          url: "/mockTest",
        },
        {
          name: "上传文件",
          url: "/fileUpload",
        },
      ],
    };
  },
};
</script>
<style scoped>
.menu {
  width: 150px;
  padding: 5px;
  font-size: 20px;
}
</style>
