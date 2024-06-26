import Vue from 'vue'
import Router from 'vue-router'
import { store } from '@/store'
import { jwtDecode } from "jwt-decode";

Vue.use(Router)

export const router = new Router({
  // mode: 'history',
  routes: [
    {
      path: '/',
      component: () => import('@/components/customer/Nav'),
      meta: { requiresAuth: true, roles: ['GUEST'] },
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
      meta: { requiresAuth: true, roles: ['USER'] },
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
      path: '/admin',
      component: () => import('@/components/admin/Nav'),
      meta: { requiresAuth: true, roles: ['ADMIN'] },
      children: [
        {
          name: 'homeAdmin',
          path: '',
          component: () => import('@/components/admin/Home')
        },
        {
          name: 'products',
          path: 'products',
          component: () => import('@/components/admin/Product')
        },
        {
          name: 'AdminProductDetails',
          path: 'products/:id',
          component: () => import('@/components/admin/ProductDetails')
        },
        {
          name: 'orderHistory',
          path: 'order-history',
          component: () => import('@/components/admin/OrderHistory')
        },
        {
          name: 'employees',
          path: 'employees',
          component: () => import('@/components/admin/Employee')
        },
        {
          name: 'branches',
          path: 'branches',
          component: () => import('@/components/admin/Branch')
        }
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
    {
      path: '/:catchAll(.*)',
      component: () => import('@/components/NotFoundComponent'),
      name: 'NotFound'
    }
  ]
})

router.beforeEach((to, from, next) => {
  const publicPages = ['/login', '/register', '/authenticate'];

  if (publicPages.includes(to.path)) {
    next();
    return;
  }

  const jwt = localStorage.getItem('user');
  if (jwt == null) next('/login');

  const role = jwtDecode(jwt).role;
  
  const requiresAuth = to.matched.some(record => 
    record.meta.requiresAuth && record.meta.roles.includes(role)
  )

  if (!requiresAuth) {
    next('/login') // redirect to login page if user is not authenticated
  } else {
    next() // proceed to route
  }
})