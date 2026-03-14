<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card class="stat-card card-1">
          <div class="stat-content">
            <div class="stat-icon">
              <el-icon :size="40"><Document /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">1,234</div>
              <div class="stat-label">文章总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card card-2">
          <div class="stat-content">
            <div class="stat-icon">
              <el-icon :size="40"><User /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">5,678</div>
              <div class="stat-label">用户总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card card-3">
          <div class="stat-content">
            <div class="stat-icon">
              <el-icon :size="40"><View /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">89,012</div>
              <div class="stat-label">浏览总量</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card card-4">
          <div class="stat-content">
            <div class="stat-icon">
              <el-icon :size="40"><ChatDotRound /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">3,456</div>
              <div class="stat-label">评论总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>热门文章</span>
            </div>
          </template>
          <el-table :data="hotArticles" style="width: 100%">
            <el-table-column prop="title" label="标题" />
            <el-table-column prop="viewCount" label="浏览量" width="100" />
          </el-table>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>最新用户</span>
            </div>
          </template>
          <el-table :data="recentUsers" style="width: 100%">
            <el-table-column prop="nickname" label="昵称" />
            <el-table-column prop="createTime" label="注册时间" width="180" />
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const hotArticles = ref([
  { title: '人工智能技术迎来新突破', viewCount: 1580 },
  { title: '国内经济稳中向好', viewCount: 2340 },
  { title: '新能源汽车销量创新高', viewCount: 1890 },
  { title: '教育改革深入推进', viewCount: 1456 },
  { title: '5G技术赋能千行百业', viewCount: 2120 }
])

const recentUsers = ref([
  { nickname: '张三', createTime: '2026-03-14 10:30:00' },
  { nickname: '李四', createTime: '2026-03-14 09:15:00' },
  { nickname: '王五', createTime: '2026-03-13 16:20:00' },
  { nickname: '赵六', createTime: '2026-03-13 14:10:00' },
  { nickname: '钱七', createTime: '2026-03-12 11:30:00' }
])
</script>

<style scoped>
.dashboard {
  animation: fadeIn 0.5s;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(20px); }
  to { opacity: 1; transform: translateY(0); }
}

.stat-card {
  border-radius: 12px;
  transition: all 0.3s;
  border: none;
  overflow: hidden;
}

.stat-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.15);
}

.stat-content {
  display: flex;
  align-items: center;
  gap: 20px;
}

.stat-icon {
  width: 70px;
  height: 70px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
}

.card-1 .stat-icon { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); }
.card-2 .stat-icon { background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%); }
.card-3 .stat-icon { background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%); }
.card-4 .stat-icon { background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%); }

.stat-info {
  flex: 1;
}

.stat-value {
  font-size: 28px;
  font-weight: bold;
  color: #333;
  margin-bottom: 5px;
}

.stat-label {
  font-size: 14px;
  color: #999;
}

.card-header {
  font-weight: bold;
  font-size: 16px;
}
</style>
