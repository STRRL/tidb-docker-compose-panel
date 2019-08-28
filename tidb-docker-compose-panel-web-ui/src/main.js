import Vue from 'vue'
import VueRouter from 'vue-router'
import App from './App.vue'
import './plugins/element.js'
import router from './router'

Vue.use(VueRouter)

Vue.config.productionTip = false
new Vue({
  render: h => h(App),
  router
}).$mount('#app')
