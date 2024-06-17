import Vue from 'vue'
import Router from 'vue-router'
import {store} from '@/store'
import { jwtDecode } from "jwt-decode";

Vue.use(Router)

export const router = new Router({
  routes: [
    {
      path: '/',
      component: () => import('@/components/customer/Nav'),
      meta: { requiresAuth: true, roles: ['guest'] },
      children: [
        {
          path: '/',
          name: 'home',
          component: () => import('@/components/customer/Product'),
        },
        {
          path: '/products/:id',
          name: 'productDetails',
          component: () => import('@/components/customer/Details')
        },
        {
          path: 'carts',
          name: 'carts',
          component: () => import('@/components/customer/Cart')
        },
        {
          name: 'my-orders',
          path: 'history',
          component: () => import('@/components/customer/MyOrder')
        },
        {
          path: '/order-success',
          component: () => import('@/components/customer/OrderSuccess')
        }
      ],
    },
    {
      path: '/cashier',
      component: () => import('@/components/cashier/Nav'),
      meta: { requiresAuth: true, roles: ['user'] },
      children: [
        {
          path: '',
          component: () => import('@/components/cashier/Home')
        },
        {
          name: 'order',
          path: 'order',
          component: () => import('@/components/cashier/Order')
        },
      ]
    },
    {
      path: '/login',
      component: () => import('@/components/Login')
    },
    {
      path: '/authenticate',
      component: () => import('@/components/customer/Authen')
    },
  ]
})

router.beforeEach((to, from, next) => {
  const publicPages = ['/login', '/register', '/authenticate'];
  const requiresAuth = to.matched.some(record => {
    record.meta.requiresAuth && record.meta.roles.includes(store.state.role)
  })

  if (publicPages.includes(to.path)) {
    next();
    return;
  }

  const jwt = localStorage.getItem('user');
  if (jwt == null)next('/login');

  const role = jwtDecode(jwt).role;

  if (!requiresAuth && !role){
    next('/login') // redirect to login page if user is not authenticated
  } else {
    next() // proceed to route
  }
})