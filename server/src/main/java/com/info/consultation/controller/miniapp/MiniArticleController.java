package com.info.consultation.controller.miniapp;

import com.info.consultation.common.PageResult;
import com.info.consultation.common.Result;
import com.info.consultation.entity.Article;
import com.info.consultation.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/miniapp/article")
public class MiniArticleController {
    
    @Autowired
    private ArticleService articleService;
    
    @GetMapping("/list")
    public Result<PageResult<Article>> getArticleList(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId) {
        PageResult<Article> result = articleService.getArticleList(current, size, keyword, categoryId, 1);
        return Result.success(result);
    }
    
    @GetMapping("/detail/{id}")
    public Result<Article> getArticleDetail(@PathVariable Long id) {
        Article article = articleService.getArticleDetail(id);
        if (article != null) {
            articleService.incrementViewCount(id);
            return Result.success(article);
        }
        return Result.error("文章不存在");
    }
    
    @GetMapping("/hot")
    public Result<List<Article>> getHotArticles(@RequestParam(defaultValue = "10") Integer limit) {
        List<Article> articles = articleService.getHotArticles(limit);
        return Result.success(articles);
    }
    
    @GetMapping("/recommend")
    public Result<List<Article>> getRecommendArticles(@RequestParam(defaultValue = "10") Integer limit) {
        List<Article> articles = articleService.getRecommendArticles(limit);
        return Result.success(articles);
    }
    
    @PostMapping("/like/{id}")
    public Result<Void> likeArticle(@PathVariable Long id) {
        boolean success = articleService.incrementLikeCount(id);
        return success ? Result.success() : Result.error("操作失败");
    }
    
    @PostMapping("/collect/{id}")
    public Result<Void> collectArticle(@PathVariable Long id) {
        boolean success = articleService.incrementCollectCount(id);
        return success ? Result.success() : Result.error("操作失败");
    }
    
    @PostMapping("/share/{id}")
    public Result<Void> shareArticle(@PathVariable Long id) {
        boolean success = articleService.incrementShareCount(id);
        return success ? Result.success() : Result.error("操作失败");
    }
}
