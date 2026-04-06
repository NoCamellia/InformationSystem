<template>
  <div class="article-edit">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>{{ isEdit ? '编辑文章' : '新增文章' }}</span>
          <el-button @click="goBack">返回</el-button>
        </div>
      </template>

      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="文章标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入文章标题" />
        </el-form-item>

        <el-form-item label="文章摘要" prop="summary">
          <el-input 
            v-model="form.summary" 
            type="textarea" 
            :rows="3"
            placeholder="请输入文章摘要"
          />
        </el-form-item>

        <el-form-item label="封面图片" prop="coverImage">
          <el-input v-model="form.coverImage" placeholder="请输入封面图片URL" />
        </el-form-item>

        <el-form-item label="文章分类" prop="categoryId">
          <el-select v-model="form.categoryId" placeholder="请选择分类">
            <el-option
              v-for="item in categoryOptions"
              :key="item.id"
              :label="item.categoryName"
              :value="item.id"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="作者" prop="authorName">
          <el-input v-model="form.authorName" placeholder="请输入作者名称" />
        </el-form-item>

        <el-form-item label="来源" prop="source">
          <el-input v-model="form.source" placeholder="请输入文章来源" />
        </el-form-item>

        <el-form-item label="文章内容" prop="content">
          <el-input 
            v-model="form.content" 
            type="textarea" 
            :rows="15"
            placeholder="请输入文章内容（支持HTML）"
          />
        </el-form-item>

        <el-form-item label="置顶">
          <el-switch v-model="form.isTop" :active-value="1" :inactive-value="0" />
        </el-form-item>

        <el-form-item label="热门">
          <el-switch v-model="form.isHot" :active-value="1" :inactive-value="0" />
        </el-form-item>

        <el-form-item label="推荐">
          <el-switch v-model="form.isRecommend" :active-value="1" :inactive-value="0" />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="handleSave(0)">保存草稿</el-button>
          <el-button type="success" @click="handleSave(1)">发布文章</el-button>
          <el-button @click="goBack">取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getArticleDetail, addArticle, updateArticle } from '@/api/article'
import { getCategoryList } from '@/api/category'

const router = useRouter()
const route = useRoute()
const formRef = ref(null)
const isEdit = ref(false)
const categoryOptions = ref([])

const form = reactive({
  title: '',
  summary: '',
  coverImage: '',
  content: '',
  categoryId: null,
  authorId: 1,
  authorName: '系统管理员',
  source: '',
  isTop: 0,
  isHot: 0,
  isRecommend: 0,
  status: 0
})

const rules = {
  title: [{ required: true, message: '请输入文章标题', trigger: 'blur' }],
  summary: [{ required: true, message: '请输入文章摘要', trigger: 'blur' }],
  content: [{ required: true, message: '请输入文章内容', trigger: 'blur' }],
  categoryId: [{ required: true, message: '请选择分类', trigger: 'change' }]
}

const fetchCategories = async () => {
  try {
    const res = await getCategoryList()
    categoryOptions.value = res
  } catch (error) {
    ElMessage.error('获取分类失败')
  }
}

const fetchDetail = async () => {
  try {
    const res = await getArticleDetail(route.params.id)
    Object.assign(form, res)
  } catch (error) {
    ElMessage.error('获取文章详情失败')
  }
}

const handleSave = (status) => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        form.status = status
        if (isEdit.value) {
          await updateArticle(form)
        } else {
          await addArticle(form)
        }
        ElMessage.success(status === 1 ? '发布成功' : '保存成功')
        goBack()
      } catch (error) {
        ElMessage.error('操作失败')
      }
    }
  })
}

const goBack = () => {
  router.back()
}

onMounted(async () => {
  await fetchCategories()
  if (route.params.id) {
    isEdit.value = true
    fetchDetail()
  }
})
</script>

<style scoped>
.article-edit {
  animation: fadeIn 0.5s;
}

@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
