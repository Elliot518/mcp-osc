const Mock = require("mockjs");
const list = [];
for (let i = 0; i < 5; i++) {
  list.push(
    Mock.mock({
      id: i,
      value: Mock.mock("@first"),
    })
  );
}
module.exports = [
  {
    url: "/duty-meeting-type/list",
    type: "get",
    response: () => {
      return {
        code: "0",
        message: "操作成功",
        data: list,
      };
    },
  },
  {
    url: "/duty-dob/names",
    type: "get",
    response: () => {
      return {
        code: "0",
        message: "操作成功",
        data: list,
      };
    },
  },
  {
    url: "/duty-issue-type/list",
    type: "get",
    response: () => {
      return {
        code: "0",
        message: "操作成功",
        data: list,
      };
    },
  },
];
