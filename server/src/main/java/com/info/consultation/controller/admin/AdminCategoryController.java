package com.info.consultation.controller.admin;

import com.info.consultation.common.Result;
import com.info.consultation.entity.Category;
import com.info.consultation.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/category")
public class AdminCategoryController {
    
    @Autowired
    private CategoryService categoryService;
    
    @GetMapping("/list")
    public Result<List<Category>> getCategoryList() {
        List<Category> categories = categoryService.getCategoryTree();
        return Result.success(categories);
    }
    
    @GetMapping("/detail/{id}")
    public Result<Category> getCategoryDetail(@PathVariable Long id) {
        Category category = categoryService.getById(id);
        return category != null ? Result.success(category) : Result.error("分类不存在");
    }
    
    @PostMapping("/add")
    public Result<Void> addCategory(@RequestBody Category category) {
        boolean success = categoryService.save(category);
        return success ? Result.success() : Result.error("添加失败");
    }
    
    @PutMapping("/update")
    public Result<Void> updateCategory(@RequestBody Category category) {
        boolean success = categoryService.updateById(category);
        return success ? Result.success() : Result.error("更新失败");
    }
    
    @DeleteMapping("/delete/{id}")
    public Result<Void> deleteCategory(@PathVariable Long id) {
        boolean success = categoryService.removeById(id);
        return success ? Result.success() : Result.error("删除失败");
    }
}
