package com.info.consultation.controller.miniapp;

import com.info.consultation.common.Result;
import com.info.consultation.entity.Category;
import com.info.consultation.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/miniapp/category")
public class MiniCategoryController {
    
    @Autowired
    private CategoryService categoryService;
    
    @GetMapping("/list")
    public Result<List<Category>> getCategoryList(@RequestParam(required = false, defaultValue = "0") Long parentId) {
        List<Category> categories = categoryService.getCategoryByParentId(parentId);
        return Result.success(categories);
    }
    
    @GetMapping("/tree")
    public Result<List<Category>> getCategoryTree() {
        List<Category> categories = categoryService.getCategoryTree();
        return Result.success(categories);
    }
}
