import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export const router = new Router({
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
    {
      path: '/login',
      component: () => import('@/components/Login')
    }
  ]
})

router.beforeEach((to, from, next) => {
  // chuyển đến trang login nếu chưa được login
  const publicPages = ['/login', '/register'];
  const authRequired = !publicPages.includes(to.path);
  const loggedIn = localStorage.getItem('user');

  if (authRequired && !loggedIn) {
    return next('/login');
  }
  next();
})