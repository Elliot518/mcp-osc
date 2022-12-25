<template>
  <div>
    <el-button @click="addHandle()">新增</el-button>
    <el-table :data="tableList" style="width: 100%">
      <el-table-column label="名字">
        <template slot-scope="scope">
          <div v-if="scope.row.isSet === 'init'">{{ scope.row.name }}</div>
          <div v-else>
            <el-input v-model="scope.row.name"></el-input>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.isSet === 'init'"
            @click="editHandle(scope.$index, scope.row)"
            >编辑</el-button
          >
          <el-button
            v-if="scope.row.isSet === 'edit' || scope.row.isSet === 'add'"
            @click="saveHandle(scope.$index, scope.row)"
            >保存</el-button
          >
          <el-button
            v-if="scope.row.isSet === 'edit' || scope.row.isSet === 'add'"
            @click="cancelHandle(scope.$index, scope.row)"
            >取消</el-button
          >
          <el-button
            v-if="scope.row.isSet === 'init'"
            @click="delHandle(scope.$index, scope.row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script>
export default {
  name: "stopWord",
  data() {
    return {
      tableList: [
        {
          name: "测试",
          isSet: "init",
        },
      ],
    };
  },
  methods: {
    addHandle() {
      this.tableList.push({
        name: "",
        isSet: "add",
      });
    },
    editHandle(index, rowData) {
      let { name } = rowData;
      let rowTem = { name, isSet: "edit" };
      this.tableList.splice(index, 1, rowTem);
    },
    delHandle(index) {
      this.tableList.splice(index, 1);
    },
    cancelHandle(index, rowData) {
      let { name } = rowData;
      if (rowData.isSet === "edit") {
        let rowTem = { name, isSet: "init" };
        this.tableList.splice(index, 1, rowTem);
      } else {
        this.tableList.splice(index, 1);
      }
    },
    saveHandle(index, rowData) {
      let { name } = rowData;
      let rowTem = { name, isSet: "init" };
      if (rowData.isSet === "edit") {
        this.tableList.splice(index, 1, rowTem);
      } else {
        this.tableList.splice(index, 1, rowTem);
      }
    },
  },
};
</script>
<style lang="scss" scoped></style>
