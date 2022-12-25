<template>
  <div>
    <span>{{ label }}</span>
  </div>
</template>
<script>
export default {
  name: "transformLabel",
  props: {
    selectValue: {
      type: String,
      default: "",
    },
    selectArray: {
      type: Array,
      default: () => [],
    },
  },
  data() {
    return {
      label: "",
    };
  },
  watch: {
    selectValue: function (newWord, oldWord) {
      if (newWord !== oldWord) {
        this.getLabel();
      }
    },
    selectArray: function () {
      this.getLabel();
    },
  },
  created() {
    this.getLabel();
  },
  methods: {
    getLabel() {
      let findIndex = this.selectArray.findIndex(
        (item) => item.id == this.selectValue
      );
      if (findIndex !== -1) {
        this.label = this.selectArray[findIndex].value;
      }
    },
  },
};
</script>
