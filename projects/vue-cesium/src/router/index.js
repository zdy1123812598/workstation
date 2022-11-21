import Vue from 'vue'
import Router from 'vue-router'
import Cesium from '@/components/Cesium'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Cesium',
      component: Cesium
    }
  ]
})
