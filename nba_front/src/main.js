import Vue from 'vue'
import App from './App.vue'
import router from './router'
// import './plugins/element.js'
import './assets/css/global.css' //导入全局样式表

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

//导入进度条依赖
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

//在main.js里配置axios
import axios from 'axios'
// import { config } from 'vue/types/umd';
axios.defaults.baseURL = '/api';


//axios拦截器
//在request拦截器中，展示进度条：Nprogress.start();
axios.interceptors.request.use(config=>{
  NProgress.start();
  config.headers.Authorization = window.sessionStorage.getItem('token');

  //最后必须return config
  return config
})

//在response拦截器中隐藏进度条：Nprogress.done();
axios.interceptors.response.use(config =>{
  NProgress.done();
  return config
})

Vue.prototype.$http =axios //通过$http就用axios访问

Vue.use(ElementUI);

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
