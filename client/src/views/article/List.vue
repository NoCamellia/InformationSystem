<template>
  <div class="article-list">
    <el-card>
      <div class="search-bar">
        <el-input 
          v-model="searchForm.keyword" 
          placeholder="搜索文章标题"
          style="width: 300px; margin-right: 10px;"
          clearable
        />
        <el-select 
          v-model="searchForm.categoryId" 
          placeholder="选择分类"
          style="width: 200px; margin-right: 10px;"
          clearable
        >
          <el-option label="全部分类" :value="null" />
          <el-option label="新闻" :value="1" />
          <el-option label="科技" :value="5" />
          <el-option label="财经" :value="9" />
        </el-select>
        <el-select 
          v-model="searchForm.status" 
          placeholder="文章状态"
          style="width: 150px; margin-right: 10px;"
          clearable
        >
          <el-option label="全部状态" :value="null" />
          <el-option label="草稿" :value="0" />
          <el-option label="已发布" :value="1" />
          <el-option label="已下架" :value="2" />
        </el-select>
        <el-button type="primary" @click="handleSearch">搜索</el-button>
        <el-button type="success" @click="handleAdd">新增文章</el-button>
      </div>

      <el-table :data="tableData" style="width: 100%; margin-top: 20px;" v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="title" label="标题" min-width="200" />
        <el-table-column prop="categoryId" label="分类" width="120">
          <template #default="{ row }">
            <el-tag>{{ getCategoryName(row.categoryId) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="authorName" label="作者" width="120" />
        <el-table-column prop="viewCount" label="浏览量" width="100" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag v-if="row.status === 0" type="info">草稿</el-tag>
            <el-tag v-else-if="row.status === 1" type="success">已发布</el-tag>
            <el-tag v-else type="danger">已下架</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="publishTime" label="发布时间" width="180" />
        <el-table-column label="操作" width="250" fixed="right">
          <template #default="{ row }">
            <el-button size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button size="small" type="success" @click="handlePublish(row)" v-if="row.status === 0">发布</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row)">删除</el-button>
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
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getArticleList, deleteArticle, publishArticle } from '@/api/article'

const router = useRouter()
const loading = ref(false)
const tableData = ref([])

const searchForm = reactive({
  keyword: '',
  categoryId: null,
  status: null
})

const pagination = reactive({
  current: 1,
  size: 10,
  total: 0
})

const getCategoryName = (id) => {
  const map = { 1: '新闻', 2: '国内', 3: '国际', 5: '科技', 9: '财经' }
  return map[id] || '其他'
}

const fetchData = async () => {
  loading.value = true
  try {
    const res = await getArticleList({
      current: pagination.current,
      size: pagination.size,
      ...searchForm
    })
    tableData.value = res.data.records
    pagination.total = res.data.total
  } catch (error) {
    ElMessage.error('获取数据失败')
  } finally {
    loading.value = false
  }
}

const handleSearch = () => {
  pagination.current = 1
  fetchData()
}

const handleAdd = () => {
  router.push('/article/add')
}

const handleEdit = (row) => {
  router.push(`/article/edit/${row.id}`)
}

const handlePublish = async (row) => {
  try {
    await publishArticle(row.id)
    ElMessage.success('发布成功')
    fetchData()
  } catch (error) {
    ElMessage.error('发布失败')
  }
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定要删除这篇文章吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await deleteArticle(row.id)
      ElMessage.success('删除成功')
      fetchData()
    } catch (error) {
      ElMessage.error('删除失败')
    }
  })
}

onMounted(() => {
  fetchData()
})
</script>

<style scoped>
.article-list {
  animation: fadeIn 0.5s;
}

@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}

.search-bar {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  gap: 10px;
}
</style>
