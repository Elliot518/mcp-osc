import Vue from "vue";
import "./utils/global"; //多语言
import App from "./App.vue";
import store from "./store";
import router from "./router";
import Cookies from "js-cookie";
import "element-ui/lib/theme-chalk/index.css";
import "@/styles/index.scss"; // global css
import "@/iconfont/iconfont.css";
import ElementUI from "element-ui";
import VueI18n from "vue-i18n";
import enLocale from "element-ui/lib/locale/lang/en";
import zhLocale from "element-ui/lib/locale/lang/zh-CN";
import InterfaceContent from "@/components/InterfaceContent/index";
import TransformLabel from "@/components/select/transformLabel";

Vue.use(InterfaceContent);
Vue.component(TransformLabel.name, TransformLabel);
Vue.config.productionTip = false;
let elementuiLocale = {
  en: enLocale,
  zh: zhLocale,
};
let localeCookie = Cookies.get("locale") || "zh";
Vue.use(ElementUI, { locale: elementuiLocale[localeCookie] });

//mock配置
//if (process.env.NODE_ENV === "dev") {
// const { mockXHR } = require("../src/mock");
// mockXHR();
//}
Vue.use(VueI18n);
const i18n = new VueI18n({
  locale: Cookies.get("locale") || "zh",
  messages: {
    zh: require("./VueI18n/language/zh"), //
    en: require("./VueI18n/language/en"),
  },
});
new Vue({
  i18n,
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
