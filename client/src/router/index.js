import { createRouter, createWebHistory } from 'vue-router'
import Layout from '@/layout/index.vue'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login.vue')
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('@/views/Dashboard.vue'),
        meta: { title: '首页', icon: 'DataLine' }
      },
      {
        path: 'article',
        name: 'Article',
        component: () => import('@/views/article/List.vue'),
        meta: { title: '文章管理', icon: 'Document' }
      },
      {
        path: 'article/add',
        name: 'ArticleAdd',
        component: () => import('@/views/article/Edit.vue'),
        meta: { title: '新增文章', hidden: true }
      },
      {
        path: 'article/edit/:id',
        name: 'ArticleEdit',
        component: () => import('@/views/article/Edit.vue'),
        meta: { title: '编辑文章', hidden: true }
      },
      {
        path: 'category',
        name: 'Category',
        component: () => import('@/views/Category.vue'),
        meta: { title: '分类管理', icon: 'Folder' }
      },
      {
        path: 'tag',
        name: 'Tag',
        component: () => import('@/views/Tag.vue'),
        meta: { title: '标签管理', icon: 'PriceTag' }
      },
      {
        path: 'user',
        name: 'User',
        component: () => import('@/views/User.vue'),
        meta: { title: '用户管理', icon: 'User' }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
