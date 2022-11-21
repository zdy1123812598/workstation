import { createRouter, createWebHashHistory } from 'vue-router'
import page from '@/page/mainPage.vue'

const routes = [{
  path: '/',
  component: page
}, ]

const router = createRouter({
  history: createWebHashHistory(),
  routes,
})

export default router