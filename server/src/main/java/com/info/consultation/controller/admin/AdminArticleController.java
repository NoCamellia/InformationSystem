package com.info.consultation.controller.admin;

import com.info.consultation.common.PageResult;
import com.info.consultation.common.Result;
import com.info.consultation.entity.Article;
import com.info.consultation.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/article")
public class AdminArticleController {
    
    @Autowired
    private ArticleService articleService;
    
    @GetMapping("/list")
    public Result<PageResult<Article>> getArticleList(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Integer status) {
        PageResult<Article> result = articleService.getArticleList(current, size, keyword, categoryId, status);
        return Result.success(result);
    }
    
    @GetMapping("/detail/{id}")
    public Result<Article> getArticleDetail(@PathVariable Long id) {
        Article article = articleService.getArticleDetail(id);
        return article != null ? Result.success(article) : Result.error("文章不存在");
    }
    
    @PostMapping("/add")
    public Result<Void> addArticle(@RequestBody Article article) {
        boolean success = articleService.saveArticle(article);
        return success ? Result.success() : Result.error("添加失败");
    }
    
    @PutMapping("/update")
    public Result<Void> updateArticle(@RequestBody Article article) {
        boolean success = articleService.updateArticle(article);
        return success ? Result.success() : Result.error("更新失败");
    }
    
    @DeleteMapping("/delete/{id}")
    public Result<Void> deleteArticle(@PathVariable Long id) {
        boolean success = articleService.removeById(id);
        return success ? Result.success() : Result.error("删除失败");
    }
    
    @PutMapping("/publish/{id}")
    public Result<Void> publishArticle(@PathVariable Long id) {
        boolean success = articleService.publishArticle(id);
        return success ? Result.success() : Result.error("发布失败");
    }
}
