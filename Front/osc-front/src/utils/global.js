import Vue from "vue";
import VueI18n from "vue-i18n";
import Cookies from "js-cookie";
Vue.use(VueI18n);
const i18n = new VueI18n({
  locale: Cookies.get("locale") || "zh",
  messages: {
    zh: require("@/VueI18n/language/zh"), //
    en: require("@/VueI18n/language/en"),
  },
});
window.i18n = i18n;
