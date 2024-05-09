import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Nav',
      component: () => import('@/components/customer/Nav'),
      children: [
        {
          path: '/products',
          component: () => import('@/components/customer/Home')
        },
      ]
    },
    {
      path: '/products/:id',
      component: () => import('@/components/customer/Details')
    }
  ]
})
