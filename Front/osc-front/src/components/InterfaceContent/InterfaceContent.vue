<template>
  <div>
    <span v-if="content">{{ content }}</span>
    <span v-else>- -</span>
  </div>
</template>
<script>
export default {
  name: "InterfaceContent",
  props: ["content"],
};
</script>
<style lang="scss" scoped></style>
