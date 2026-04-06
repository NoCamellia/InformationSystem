<template>
  <div class="layout-container">
    <el-container>
      <el-aside width="220px" class="sidebar">
        <div class="logo">
          <h2>咨询管理系统</h2>
        </div>
        <el-menu
          :default-active="activeMenu"
          router
          background-color="#2c3e50"
          text-color="#ecf0f1"
          active-text-color="#3498db"
        >
          <el-menu-item 
            v-for="route in menuRoutes" 
            :key="route.path"
            :index="route.path"
          >
            <el-icon><component :is="route.meta.icon" /></el-icon>
            <span>{{ route.meta.title }}</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      
      <el-container>
        <el-header class="header">
          <div class="header-left">
            <el-breadcrumb separator="/">
              <el-breadcrumb-item>首页</el-breadcrumb-item>
              <el-breadcrumb-item v-if="currentRoute">{{ currentRoute.meta?.title }}</el-breadcrumb-item>
            </el-breadcrumb>
          </div>
          <div class="header-right">
            <el-dropdown @command="handleCommand">
              <span class="user-info">
                <el-avatar :size="35" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" />
                <span class="username">{{ adminInfo?.realName || adminInfo?.username || '管理员' }}</span>
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item command="logout">退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </el-header>
        
        <el-main class="main-content">
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'

const router = useRouter()
const route = useRoute()

const adminInfo = computed(() => {
  const raw = localStorage.getItem('adminInfo')
  return raw ? JSON.parse(raw) : null
})

const menuRoutes = computed(() => {
  const routes = router.getRoutes()
  return routes.find(r => r.path === '/')?.children?.filter(r => !r.meta?.hidden) || []
})

const activeMenu = computed(() => {
  return route.path
})

const currentRoute = computed(() => {
  return route
})

const handleCommand = (command) => {
  if (command === 'logout') {
    localStorage.removeItem('token')
    localStorage.removeItem('adminInfo')
    ElMessage.success('退出成功')
    router.push('/login')
  }
}
</script>

<style scoped>
.layout-container {
  height: 100vh;
  background: #f5f7fa;
}

.sidebar {
  background: #2c3e50;
  height: 100vh;
  box-shadow: 2px 0 8px rgba(0, 0, 0, 0.1);
}

.logo {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #1a252f;
  color: #fff;
  font-size: 18px;
  font-weight: bold;
}

.logo h2 {
  font-size: 18px;
  margin: 0;
}

.header {
  background: #fff;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 30px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.header-right {
  display: flex;
  align-items: center;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 10px;
  cursor: pointer;
}

.username {
  font-size: 14px;
  color: #333;
}

.main-content {
  padding: 20px;
  background: #f5f7fa;
  min-height: calc(100vh - 60px);
}
</style>
