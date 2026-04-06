<template>
  <div class="tag-page">
    <el-card>
      <div class="toolbar">
        <el-input
          v-model="searchForm.keyword"
          placeholder="搜索标签名称"
          clearable
          style="width: 260px"
          @keyup.enter="handleSearch"
        />
        <div class="toolbar-actions">
          <el-button @click="handleSearch">搜索</el-button>
          <el-button type="primary" @click="handleAdd">新增标签</el-button>
        </div>
      </div>

      <el-table :data="tableData" style="width: 100%; margin-top: 20px;" v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="tagName" label="标签名称" />
        <el-table-column prop="color" label="颜色" width="150">
          <template #default="{ row }">
            <el-tag :color="row.color" style="border: none; color: #fff;">{{ row.tagName }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="useCount" label="使用次数" width="120" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag v-if="row.status === 1" type="success">正常</el-tag>
            <el-tag v-else type="danger">禁用</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-model:current-page="pagination.current"
        v-model:page-size="pagination.size"
        :total="pagination.total"
        :page-sizes="[10, 20, 50]"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSearch"
        @current-change="fetchData"
        style="margin-top: 20px; justify-content: flex-end;"
      />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="500px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="标签名称" prop="tagName">
          <el-input v-model="form.tagName" placeholder="请输入标签名称" />
        </el-form-item>
        <el-form-item label="标签颜色" prop="color">
          <el-color-picker v-model="form.color" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-switch v-model="form.status" :active-value="1" :inactive-value="0" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getTagList, addTag, updateTag, deleteTag } from '@/api/tag'

const loading = ref(false)
const tableData = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('新增标签')
const formRef = ref(null)

const searchForm = reactive({
  keyword: ''
})

const pagination = reactive({
  current: 1,
  size: 10,
  total: 0
})

const form = reactive({
  id: null,
  tagName: '',
  color: '#1890ff',
  useCount: 0,
  status: 1
})

const rules = {
  tagName: [{ required: true, message: '请输入标签名称', trigger: 'blur' }]
}

const fetchData = async () => {
  loading.value = true
  try {
    const res = await getTagList({
      current: pagination.current,
      size: pagination.size,
      keyword: searchForm.keyword
    })
    tableData.value = res.data.records
    pagination.total = res.data.total
  } catch (error) {
    ElMessage.error('获取标签失败')
  } finally {
    loading.value = false
  }
}

const handleSearch = () => {
  pagination.current = 1
  fetchData()
}

const resetForm = () => {
  Object.assign(form, {
    id: null,
    tagName: '',
    color: '#1890ff',
    useCount: 0,
    status: 1
  })
}

const handleAdd = () => {
  dialogTitle.value = '新增标签'
  resetForm()
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑标签'
  Object.assign(form, { ...row })
  dialogVisible.value = true
}

const handleSubmit = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        if (form.id) {
          await updateTag(form)
        } else {
          await addTag(form)
        }
        ElMessage.success('操作成功')
        dialogVisible.value = false
        fetchData()
      } catch (error) {
        ElMessage.error(error.message || '操作失败')
      }
    }
  })
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定要删除这个标签吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await deleteTag(row.id)
      ElMessage.success('删除成功')
      fetchData()
    } catch (error) {
      ElMessage.error(error.message || '删除失败')
    }
  })
}

onMounted(() => {
  fetchData()
})
</script>

<style scoped>
.tag-page {
  animation: fadeIn 0.5s;
}

@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}

.toolbar {
  display: flex;
  justify-content: space-between;
  gap: 12px;
  align-items: center;
}

.toolbar-actions {
  display: flex;
  gap: 10px;
}
</style>
