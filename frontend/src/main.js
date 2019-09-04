// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import VueRouter from 'vue-router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import axios from 'axios'
import index from "./components/index";
import fixup from "./components/fixup";
import houseDetail from "./components/house-detail"
import longrent from "./components/longrent";
import rentDetails from "./userCenter/rentDetails";
import serverLogin from "./components/serverLogin";
import SlideVerify from "vue-monoplasty-slide-verify";
import clientservice from "./services/clientservice";
import GalleryTest from "./components/house-detail-test"
import master from "./services/master";
import md5 from 'js-md5'

Vue.prototype.$md5 = md5;

Vue.use(SlideVerify)
Vue.prototype.$http = axios;
Vue.prototype.getNowFormatDate = function() {
  var date = new Date();
  var seperator1 = "-";
  var year = date.getFullYear();
  var month = date.getMonth() + 1;
  var strDate = date.getDate();
  if (month >= 1 && month <= 9) {
    month = "0" + month;
  }
  if (strDate >= 0 && strDate <= 9) {
    strDate = "0" + strDate;
  }
  var currentdate = year + seperator1 + month + seperator1 + strDate;
  return currentdate;
};
Vue.prototype.getDays = function(date1, date2){
    let date1Str = date1.split("-");
    let date1Obj = new Date(date1Str[0], (date1Str[1] - 1), date1Str[2]);
    let date2Str = date2.split("-");
    let date2Obj = new Date(date2Str[0], (date2Str[1] - 1), date2Str[2]);
    let t1 = date1Obj.getTime();
    let t2 = date2Obj.getTime();
    let dateTime = 1000 * 60 * 60 * 24; //每一天的毫秒
    let minusDays = Math.floor(((t2 - t1) / dateTime));//计算出两个日期的天数差
    return Math.abs(minusDays);//取绝对值
};
Vue.use(ElementUI);
Vue.prototype.checkLogin = function(val){
  if(val === 2){
    if(!sessionStorage.getItem('id')){
      this.$alert('您未登陆', '请登陆', {
        confirmButtonText: '确定',
        callback: action => {
          window.location.href = "/services"
        }
      });
    }
  } else{
    if(!sessionStorage.getItem('id')){
      this.$alert('您未登陆', '请登陆', {
        confirmButtonText: '确定',
        callback: action => {
          window.location.href = "/"
        }
      });
    }
  }
}

Vue.config.productionTip = false;
Vue.use(VueRouter);

const router = new VueRouter({
  routes: [
    {path: "/", component: index},
    {path:"/fixup", component: fixup},
    {path:"/gallery", component:houseDetail},
    {path:"/longrent", component:longrent},
    {path:"/services", component: serverLogin},
    {path:"/usercenter",component:rentDetails},
    {path:"/services/clientService", component:clientservice},
    {path:"/services/fixerService", component:master},
    {path:"/GalleryTest", component:GalleryTest}

  ],
  mode: "history",
  scrollBehavior (to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else {
      return {x: 0, y: 0}
    }
  }
});

/* eslint-disable no-new */
new Vue({
  router,
  ElementUI,
  axios,
  el: '#app',
  components: {App},
  template: '<App/>'
});
