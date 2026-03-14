<template>
  <div class="tag-page">
    <el-card>
      <div class="toolbar">
        <el-button type="primary" @click="handleAdd">新增标签</el-button>
      </div>

      <el-table :data="tableData" style="width: 100%; margin-top: 20px;" v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="tagName" label="标签名称" />
        <el-table-column prop="color" label="颜色" width="150">
          <template #default="{ row }">
            <el-tag :color="row.color" style="border: none;">{{ row.tagName }}</el-tag>
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

const loading = ref(false)
const tableData = ref([
  { id: 1, tagName: '热点', color: '#ff4d4f', useCount: 15, status: 1 },
  { id: 2, tagName: '推荐', color: '#1890ff', useCount: 20, status: 1 },
  { id: 3, tagName: '原创', color: '#52c41a', useCount: 10, status: 1 },
  { id: 4, tagName: '深度', color: '#722ed1', useCount: 8, status: 1 },
  { id: 5, tagName: '独家', color: '#fa8c16', useCount: 5, status: 1 }
])
const dialogVisible = ref(false)
const dialogTitle = ref('新增标签')
const formRef = ref(null)

const form = reactive({
  id: null,
  tagName: '',
  color: '#1890ff',
  status: 1
})

const rules = {
  tagName: [{ required: true, message: '请输入标签名称', trigger: 'blur' }]
}

const fetchData = async () => {
  loading.value = true
  setTimeout(() => {
    loading.value = false
  }, 500)
}

const handleAdd = () => {
  dialogTitle.value = '新增标签'
  Object.assign(form, {
    id: null,
    tagName: '',
    color: '#1890ff',
    status: 1
  })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑标签'
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleSubmit = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      ElMessage.success('操作成功')
      dialogVisible.value = false
      fetchData()
    }
  })
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定要删除这个标签吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    ElMessage.success('删除成功')
    fetchData()
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
  justify-content: flex-end;
}
</style>
