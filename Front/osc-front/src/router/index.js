import Vue from "vue";
import Router from "vue-router";
import Layout from "@/layout";
Vue.use(Router);

import fileRouter from "./modules/file";

export const constantRoutes = [
    //   {
    //     path: "/401",
    //     component: () => import("@/views/error-page/401"),
    //     hidden: true,
    //   },
    //   {
    //     path: "/",
    //     component: Layout,
    //     redirect: "/wordcloud",
    //     children: [
    //       {
    //         path: "wordcloud",
    //         component: () => import("@/views/modules/example/wordcloud"),
    //         name: "wordcloud",
    //         meta: {
    //           title: "wordcloud",
    //           icon: "iconfont icon-wenjianguanli",
    //           affix: true,
    //         },
    //       },
    //     ],
    //   },
    fileRouter,
    {
        path: "/stopWord",
        component: Layout,
        redirect: "/index",
        name: "",
        meta: {
            // title: window.i18n.t("lang.file.fileManager"),
            icon: "iconstopWordfont icon-down-circle-fill",
            affix: true,
        },
        children: [{
            path: "index",
            component: () =>
                import ("@/views/modules/file/stopWord"),
            name: "stopWord",
            meta: {
                title: "停用词维护",
                icon: "iconfont icon-down-circle-fill",
                affix: true,
            },
        }, ],
    },
    //   {
    //     path: "/taskCenter",
    //     component: Layout,
    //     redirect: "/index",
    //     name: "taskCenter",
    //     meta: {
    //       // title: window.i18n.t("lang.file.fileManager"),
    //       icon: "iconfont icon-down-circle-fill",
    //       affix: true,
    //     },
    //     children: [
    //       {
    //         path: "index",
    //         component: () => import("@/views/modules/file/taskCenter"),
    //         name: "taskCenter",
    //         meta: {
    //           title: "任务中心",
    //           icon: "iconfont icon-down-circle-fill",
    //           affix: true,
    //         },
    //       },
    //     ],
    //   },
    // {
    //   path: "/performDuties",
    //   component: Layout,
    //   redirect: "/performDuties/detail",
    //   name: "performDuties",
    //   meta: {
    //     title: "履职信息管理",
    //     icon: "iconfont icon-down-circle-fill",
    //     affix: true,
    //   },
    //   children: [
    //     {
    //       path: "add",
    //       component: () => import("@/views/modules/performDuties/add"),
    //       name: "performDutiesAdd",
    //       meta: {
    //         title: "新建履职信息",
    //         icon: "iconfont icon-down-circle-fill",
    //         affix: true,
    //       },
    //     },
    //     {
    //       path: "edit",
    //       hidden: true,
    //       component: () => import("@/views/modules/performDuties/edit"),
    //       name: "performDutiesEdit",
    //       meta: {
    //         title: "履职信息编辑",
    //         icon: "iconfont icon-down-circle-fill",
    //         affix: true,
    //       },
    //     },
    //     {
    //       path: "detail",
    //       component: () => import("@/views/modules/performDuties/detail"),
    //       name: "performDutiesDetail",
    //       meta: {
    //         title: "履职信息查询",
    //         icon: "iconfont icon-down-circle-fill",
    //         affix: true,
    //       },
    //     },
    //     {
    //       path: "analyze",
    //       component: () => import("@/views/modules/performDuties/analyze"),
    //       name: "performDutiesAnalyze",
    //       meta: {
    //         title: "履职信息分析",
    //         icon: "iconfont icon-down-circle-fill",
    //         affix: true,
    //       },
    //     },
    //   ],
    // },
    //   {
    //     path: "/version",
    //     component: Layout,
    //     redirect: "/index",
    //     name: "version",
    //     meta: {
    //       // title: window.i18n.t("lang.file.fileManager"),
    //       icon: "iconfont icon-down-circle-fill",
    //       affix: true,
    //     },
    //     children: [
    //       {
    //         path: "index",
    //         component: () => import("@/views/modules/version/index"),
    //         name: "version",
    //         meta: {
    //           title: "版本介绍",
    //           icon: "iconfont icon-down-circle-fill",
    //           affix: true,
    //         },
    //       },
    //     ],
    //   },
];

const createRouter = () =>
    new Router({
        // mode: 'history', // require service support
        scrollBehavior: () => ({ y: 0 }),
        routes: constantRoutes,
    });

const router = createRouter();

export default router;