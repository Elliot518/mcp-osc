const Mock = require("mockjs");

const List = [];
const count = 100;

for (let i = 0; i < count; i++) {
  List.push(
    Mock.mock({
      //   id: "@increment",
      //   timestamp: +Mock.Random.date("T"),
      //   author: "@first",
      //   reviewer: "@first",
      //   title: "@title(5, 10)",
      //   content_short: "mock data",
      //   content: baseContent,
      //   forecast: "@float(0, 100, 2, 2)",
      //   importance: "@integer(1, 3)",
      //   "type|1": ["CN", "US", "JP", "EU"],
      //   "status|1": ["published", "draft"],
      //   display_time: "@datetime",
      //   comment_disabled: true,
      //   pageviews: "@integer(300, 5000)",
      //   image_uri,
      //   platforms: ["a-platform"],
      name: Mock.mock("@first"),
      value: Mock.mock({
        "number|100-200": 150,
      }).number,
    })
  );
}

module.exports = [
  {
    url: "/vue-element-admin/article/list",
    type: "get",
    response: (config) => {
      const {
        importance,
        type,
        title,
        page = 1,
        limit = 20,
        sort,
      } = config.query;

      let mockList = List.filter((item) => {
        if (importance && item.importance !== +importance) return false;
        if (type && item.type !== type) return false;
        if (title && item.title.indexOf(title) < 0) return false;
        return true;
      });

      if (sort === "-id") {
        mockList = mockList.reverse();
      }

      const pageList = mockList.filter(
        (item, index) => index < limit * page && index >= limit * (page - 1)
      );

      return {
        code: "0",
        data: {
          total: mockList.length,
          items: pageList,
        },
      };
    },
  },

  {
    url: "/vue-element-admin/article/detail",
    type: "get",
    response: (config) => {
      const { id } = config.query;
      for (const article of List) {
        if (article.id === +id) {
          return {
            code: 0,
            data: article,
          };
        }
      }
    },
  },

  {
    url: "/vue-element-admin/article/pv",
    type: "get",
    response: () => {
      return {
        code: 0,
        data: {
          pvData: [
            { key: "PC", pv: 1024 },
            { key: "mobile", pv: 1024 },
            { key: "ios", pv: 1024 },
            { key: "android", pv: 1024 },
          ],
        },
      };
    },
  },

  {
    url: "/vue-element-admin/article/create",
    type: "post",
    response: () => {
      return {
        code: 0,
        data: "success",
      };
    },
  },

  {
    url: "/vue-element-admin/article/update",
    type: "post",
    response: () => {
      return {
        code: 0,
        data: "success",
      };
    },
  },
];
