/** When your routing table is too long, you can split it into small modules **/

import Layout from "@/layout";

const fileRouter = {
  path: "/",
  component: Layout,
  redirect: "/fileUpload",
  name: "FileUpload",
  meta: {
    // title: window.i18n.t("lang.file.fileManager"),
    icon: "iconfont icon-down-circle-fill",
    affix: true,
  },
  children: [
    {
      path: "mockTest",
      hidden: true,
      component: () => import("@/views/modules/example/mockTest"),
      name: "mockTest",
      meta: {
        title: "柱状图",
        affix: true,
        icon: "iconfont icon-caret-right",
      },
    },
    {
      path: "inLineEdit",
      hidden: true,
      component: () => import("@/views/modules/example/inLineEdit"),
      name: "mockTest",
      meta: {
        title: "行内编辑",
        affix: true,
        icon: "iconfont icon-caret-right",
      },
    },
    {
      path: "fileUpload",
      component: () => import("@/views/modules/file/fileUpload"),
      name: "fileUpload",
      meta: {
        title: window.i18n.t("lang.file.fileUpload"),
        icon: "iconfont icon-down-circle-fill",
        affix: true,
      },
    },
    {
      path: "bar",
      hidden: true,
      component: () => import("@/views/modules/file/bar"),
      name: "bar",
      meta: {
        title: "柱状图分析",
        icon: "iconfont icon-caret-right",
        affix: true,
      },
    },
  ],
};
export default fileRouter;
