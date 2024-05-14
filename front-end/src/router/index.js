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
          name: 'home',
          component: () => import('@/components/customer/Product')
        },
        {
          path: '/products/:id',
          name: 'productDetails',
          component: () => import('@/components/customer/Details')
        },
        {
          path: '/carts',
          component: () => import('@/components/customer/Cart')
        },
        {
          path: '/my-orders',
          component: () => import('@/components/customer/MyOrder')
        },
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
