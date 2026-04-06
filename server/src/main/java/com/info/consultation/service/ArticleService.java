package com.info.consultation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.consultation.entity.Article;
import com.info.consultation.common.PageResult;

import java.util.List;

public interface ArticleService extends IService<Article> {
    
    PageResult<Article> getArticleList(Integer current, Integer size, String keyword, Long categoryId, Integer status);
    
    PageResult<Article> getArticleListByCategoryIds(Integer current, Integer size, String keyword, List<Long> categoryIds, Integer status);
    
    Article getArticleDetail(Long id);
    
    List<Article> getHotArticles(Integer limit);
    
    List<Article> getRecommendArticles(Integer limit);
    
    boolean publishArticle(Long id);
    
    boolean incrementViewCount(Long id);
    
    boolean incrementLikeCount(Long id);
    
    boolean incrementCollectCount(Long id);
    
    boolean incrementShareCount(Long id);
}
