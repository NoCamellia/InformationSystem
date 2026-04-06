package com.info.consultation.controller.miniapp;

import com.info.consultation.common.Result;
import com.info.consultation.entity.Article;
import com.info.consultation.entity.UserCollect;
import com.info.consultation.service.ArticleService;
import com.info.consultation.service.UserCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/miniapp/collect")
public class MiniCollectController {
    
    @Autowired
    private UserCollectService userCollectService;
    
    @Autowired
    private ArticleService articleService;
    
    @GetMapping("/list")
    public Result<List<Article>> getCollectList(@RequestParam Long userId) {
        List<UserCollect> collects = userCollectService.getUserCollects(userId);
        // 获取收藏的文章详情
        List<Article> articles = collects.stream()
            .map(collect -> articleService.getById(collect.getArticleId()))
            .collect(Collectors.toList());
        return Result.success(articles);
    }
    
    @GetMapping("/check/{userId}/{articleId}")
    public Result<Boolean> checkCollected(@PathVariable Long userId, @PathVariable Long articleId) {
        boolean collected = userCollectService.isCollected(userId, articleId);
        return Result.success(collected);
    }
    
    @PostMapping("/add")
    public Result<Void> addCollect(@RequestParam Long userId, @RequestParam Long articleId) {
        boolean success = userCollectService.addCollect(userId, articleId);
        return success ? Result.success() : Result.error("收藏失败或已收藏");
    }
    
    @DeleteMapping("/remove")
    public Result<Void> removeCollect(@RequestParam Long userId, @RequestParam Long articleId) {
        boolean success = userCollectService.removeCollect(userId, articleId);
        return success ? Result.success() : Result.error("取消收藏失败");
    }
}
