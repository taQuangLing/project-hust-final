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
          path: '/',
          component: () => import('@/components/customer/Product')
        },
        {
          path: '/products/:id',
          component: () => import('@/components/customer/Details')
        }
      ],
    },
    {
      path: '/cashier',
      component: () => import('@/components/cashier/Nav'),
      children: [
        {
          path: '',
          component: () => import('@/components/cashier/Home')
        },
        {
          path: 'order',
          component: () => import('@/components/cashier/Order')
        },
      ]
    },
    
  ]
})
