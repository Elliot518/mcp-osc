const Mock = require("mockjs");
const list = [];
const fileList = [];
const taskList = [];
const taskViewList = [];
for (let i = 0; i < 5; i++) {
  list.push(
    Mock.mock({
      id: "@increment",
      batchId: "B20210923110406669421" + i,
      filesNum: Mock.mock({
        "number|1-100": 100,
      }).number,
      completePercentage: Mock.mock("@float(10, 100, 1, 1)"),
      createTime: Mock.mock("@datetime"),
      lastUpdate: null,
      uploadAuthor: Mock.mock("@cname"),
      fileName: Mock.mock("@first") + ".csv",
      status: "已解析",
      stopWordsStatus: i === 0 ? 1 : 0,
      sid: i,
    })
  );
}
for (let i = 0; i < 40; i++) {
  fileList.push(
    Mock.mock({
      id: "@increment",
      fileName: Mock.mock("@first") + ".doc",
      status: "待解析",
    })
  );
}
for (let i = 0; i < 10; i++) {
  taskList.push(
    Mock.mock({
      lastStartTime: Mock.mock("@datetime"),
      jobName: Mock.mock("@first"),
      threadNum: 1,
      status: "待启动",
    })
  );
}
for (let i = 0; i < 5; i++) {
  taskViewList.push(
    Mock.mock({
      startTime: Mock.mock("@datetime"),
      endTime: Mock.mock("@datetime"),
      status: "成功",
    })
  );
}
const litsWordCloud = [];
for (let i = 0; i < 80; i++) {
  litsWordCloud.push(
    Mock.mock({
      value: Mock.mock({
        "number|1-10000": 400,
      }).number,
      name: Mock.mock("@cname"),
    })
  );
}
module.exports = [
  //   {
  //     url: "/upload/word",
  //     type: "post",
  //     response: () => {
  //       return {
  //         code: 0,
  //         data: {
  //           list: list,
  //         },
  //       };
  //     },
  //   },
  {
    url: "/word-cloud/1",
    type: "get",
    response: () => {
      return {
        code: "0",
        data: litsWordCloud,
      };
    },
  },
  {
    url: "/queue/batches",
    type: "get",
    response: () => {
      return {
        code: "0",
        data: {
          data: list,
          totalCount: 5,
        },
      };
    },
  },
  //
  {
    url: "/queue/batches/B20210923110406669421/files",
    type: "get",
    response: () => {
      return {
        code: "0",
        data: fileList,
      };
    },
  },
  {
    url: "/schedule/jobs",
    type: "get",
    response: () => {
      return {
        code: "0",
        data: {
          data: taskList,
          totalCount: 10,
        },
      };
    },
  },
  {
    url: "/task/taskViewList",
    type: "get",
    response: () => {
      return {
        code: "0",
        data: {
          data: taskViewList,
          count: 1,
        },
      };
    },
  },
  //停用词wordFileList
  {
    url: "/stop-words/list",
    type: "get",
    response: () => {
      return {
        code: "0",
        data: {
          data: list,
          totalCount: 5,
        },
      };
    },
  },
];
