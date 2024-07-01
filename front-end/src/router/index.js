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
      component: () => import('@/pages/guest/Nav'),
      meta: { requiresAuth: true, roles: ['GUEST'] },
      children: [
        {
          path: '/',
          name: 'home',
          component: () => import('@/pages/guest/Product'),
        },
        {
          path: '/products/:id',
          name: 'productDetails',
          component: () => import('@/pages/guest/Details')
        },
        {
          path: 'carts',
          name: 'carts',
          component: () => import('@/pages/guest/Cart')
        },
        {
          name: 'my-orders',
          path: 'history',
          component: () => import('@/pages/guest/MyOrder')
        },
        {
          path: '/order-success',
          component: () => import('@/pages/guest/OrderSuccess')
        }
      ],
    },
    {
      path: '/cashier',
      component: () => import('@/pages/cashier/Nav'),
      meta: { requiresAuth: true, roles: ['USER'] },
      children: [
        {
          path: '',
          component: () => import('@/pages/cashier/Home')
        },
        {
          name: 'order',
          path: 'order',
          component: () => import('@/pages/cashier/Order')
        },
      ]
    },
    {
      path: '/admin',
      component: () => import('@/pages/admin/Nav'),
      meta: { requiresAuth: true, roles: ['ADMIN'] },
      children: [
        {
          name: 'homeAdmin',
          path: '',
          component: () => import('@/pages/admin/Home')
        },
        {
          name: 'products',
          path: 'products',
          component: () => import('@/pages/admin/Product')
        },
        {
          name: 'AdminProductDetails',
          path: 'products/:id',
          component: () => import('@/pages/admin/ProductDetails')
        },
        {
          name: 'orderHistory',
          path: 'order-history',
          component: () => import('@/pages/admin/OrderHistory')
        },
        {
          name: 'employees',
          path: 'employees',
          component: () => import('@/pages/admin/Employee')
        },
        {
          name: 'branches',
          path: 'branches',
          component: () => import('@/pages/admin/Branch')
        },
        {
          name: 'branchDetails',
          path: 'branches/:id',
          component: () => import('@/pages/admin/BranchDetails')
        }
      ]
    },
    {
      path: '/login',
      component: () => import('@/pages/Login')
    },
    {
      path: '/authenticate',
      component: () => import('@/pages/guest/Authen')
    },
    {
      path: '/:catchAll(.*)',
      component: () => import('@/pages/NotFoundComponent'),
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
