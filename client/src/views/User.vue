<template>
  <div class="user-page">
    <el-card>
      <div class="search-bar">
        <el-input 
          v-model="searchKeyword" 
          placeholder="搜索用户昵称"
          style="width: 300px; margin-right: 10px;"
          clearable
        />
        <el-button type="primary" @click="handleSearch">搜索</el-button>
      </div>

      <el-table :data="tableData" style="width: 100%; margin-top: 20px;" v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="avatar" label="头像" width="80">
          <template #default="{ row }">
            <el-avatar :src="row.avatar" />
          </template>
        </el-table-column>
        <el-table-column prop="nickname" label="昵称" />
        <el-table-column prop="gender" label="性别" width="80">
          <template #default="{ row }">
            <span v-if="row.gender === 1">男</span>
            <span v-else-if="row.gender === 2">女</span>
            <span v-else>未知</span>
          </template>
        </el-table-column>
        <el-table-column prop="city" label="城市" width="120" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag v-if="row.status === 1" type="success">正常</el-tag>
            <el-tag v-else type="danger">禁用</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="注册时间" width="180" />
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button size="small" :type="row.status === 1 ? 'danger' : 'success'" @click="handleToggleStatus(row)">
              {{ row.status === 1 ? '禁用' : '启用' }}
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-model:current-page="pagination.current"
        v-model:page-size="pagination.size"
        :total="pagination.total"
        :page-sizes="[10, 20, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSearch"
        @current-change="handleSearch"
        style="margin-top: 20px; justify-content: flex-end;"
      />
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'

const loading = ref(false)
const searchKeyword = ref('')
const tableData = ref([
  { id: 1, openid: 'oTest001', nickname: '张三', avatar: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', gender: 1, province: '广东省', city: '深圳市', status: 1, createTime: '2026-03-10 10:30:00' },
  { id: 2, openid: 'oTest002', nickname: '李四', avatar: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', gender: 2, province: '北京市', city: '北京市', status: 1, createTime: '2026-03-11 09:15:00' },
  { id: 3, openid: 'oTest003', nickname: '王五', avatar: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', gender: 1, province: '上海市', city: '上海市', status: 1, createTime: '2026-03-12 14:20:00' },
  { id: 4, openid: 'oTest004', nickname: '赵六', avatar: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', gender: 2, province: '浙江省', city: '杭州市', status: 1, createTime: '2026-03-13 11:45:00' },
  { id: 5, openid: 'oTest005', nickname: '钱七', avatar: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', gender: 1, province: '江苏省', city: '南京市', status: 1, createTime: '2026-03-14 08:30:00' }
])

const pagination = reactive({
  current: 1,
  size: 10,
  total: 5
})

const fetchData = async () => {
  loading.value = true
  setTimeout(() => {
    loading.value = false
  }, 500)
}

const handleSearch = () => {
  fetchData()
}

const handleToggleStatus = (row) => {
  row.status = row.status === 1 ? 0 : 1
  ElMessage.success('操作成功')
}

onMounted(() => {
  fetchData()
})
</script>

<style scoped>
.user-page {
  animation: fadeIn 0.5s;
}

@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}

.search-bar {
  display: flex;
  align-items: center;
}
</style>
