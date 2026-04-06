package com.info.consultation.controller.miniapp;

import com.info.consultation.common.Result;
import com.info.consultation.entity.Article;
import com.info.consultation.entity.UserHistory;
import com.info.consultation.service.ArticleService;
import com.info.consultation.service.UserHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/miniapp/history")
public class MiniHistoryController {
    
    @Autowired
    private UserHistoryService userHistoryService;
    
    @Autowired
    private ArticleService articleService;
    
    @GetMapping("/list")
    public Result<List<Article>> getHistoryList(@RequestParam Long userId, 
                                                 @RequestParam(defaultValue = "20") Integer limit) {
        List<UserHistory> histories = userHistoryService.getUserHistory(userId, limit);
        // 获取浏览历史的文章详情，并去重
        List<Article> articles = histories.stream()
            .map(history -> articleService.getById(history.getArticleId()))
            .distinct()
            .collect(Collectors.toList());
        return Result.success(articles);
    }
    
    @PostMapping("/add")
    public Result<Void> addHistory(@RequestParam Long userId, @RequestParam Long articleId) {
        boolean success = userHistoryService.addHistory(userId, articleId);
        return success ? Result.success() : Result.error("记录失败");
    }
}
