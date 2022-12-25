import InterfaceContent from "./InterfaceContent";
// 为组件提供 install 安装方法，供按需引入
InterfaceContent.install = (Vue) => {
  Vue.component(InterfaceContent.name, InterfaceContent);
};
export default InterfaceContent;
