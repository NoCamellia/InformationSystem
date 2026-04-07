package com.info.consultation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.consultation.common.PageResult;
import com.info.consultation.entity.Article;
import com.info.consultation.mapper.ArticleMapper;
import com.info.consultation.mapper.TagMapper;
import com.info.consultation.service.ArticleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    
    private final TagMapper tagMapper;
    
    public ArticleServiceImpl(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }
    
    @Override
    public PageResult<Article> getArticleList(Integer current, Integer size, String keyword, Long categoryId, Integer status) {
        List<Long> categoryIds = categoryId == null ? null : List.of(categoryId);
        return getArticleListByCategoryIds(current, size, keyword, categoryIds, status);
    }
    
    @Override
    public PageResult<Article> getArticleListByCategoryIds(Integer current, Integer size, String keyword, List<Long> categoryIds, Integer status) {
        Page<Article> page = new Page<>(current, size);
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        
        if (StringUtils.hasText(keyword)) {
            wrapper.like(Article::getTitle, keyword)
                   .or()
                   .like(Article::getSummary, keyword);
        }
        
        if (categoryIds != null && !categoryIds.isEmpty()) {
            wrapper.in(Article::getCategoryId, categoryIds);
        }
        
        if (status != null) {
            wrapper.eq(Article::getStatus, status);
        }
        
        wrapper.orderByDesc(Article::getIsTop)
               .orderByDesc(Article::getPublishTime);
        
        IPage<Article> result = this.page(page, wrapper);
        result.getRecords().forEach(this::fillArticleTags);
        return new PageResult<>(result.getTotal(), result.getRecords(), result.getCurrent(), result.getSize());
    }
    
    @Override
    public Article getArticleDetail(Long id) {
        Article article = this.getById(id);
        if (article != null) {
            fillArticleTags(article);
        }
        return article;
    }
    
    @Override
    public List<Article> getHotArticles(Integer limit) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getStatus, 1)
               .eq(Article::getIsHot, 1)
               .orderByDesc(Article::getViewCount)
               .last("LIMIT " + limit);
        List<Article> articles = this.list(wrapper);
        articles.forEach(this::fillArticleTags);
        return articles;
    }
    
    @Override
    public List<Article> getRecommendArticles(Integer limit) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getStatus, 1)
               .eq(Article::getIsRecommend, 1)
               .orderByDesc(Article::getPublishTime)
               .last("LIMIT " + limit);
        List<Article> articles = this.list(wrapper);
        articles.forEach(this::fillArticleTags);
        return articles;
    }
    
    @Override
    @Transactional
    public boolean saveArticle(Article article) {
        boolean success = this.save(article);
        if (!success) {
            return false;
        }
        saveArticleTags(article);
        return true;
    }
    
    @Override
    @Transactional
    public boolean updateArticle(Article article) {
        boolean success = this.updateById(article);
        if (!success) {
            return false;
        }
        saveArticleTags(article);
        return true;
    }
    
    @Override
    public boolean publishArticle(Long id) {
        Article article = new Article();
        article.setId(id);
        article.setStatus(1);
        article.setPublishTime(LocalDateTime.now());
        return this.updateById(article);
    }
    
    @Override
    public boolean incrementViewCount(Long id) {
        Article article = this.getById(id);
        if (article != null) {
            article.setViewCount(article.getViewCount() + 1);
            return this.updateById(article);
        }
        return false;
    }
    
    @Override
    public boolean incrementLikeCount(Long id) {
        Article article = this.getById(id);
        if (article != null) {
            article.setLikeCount(article.getLikeCount() + 1);
            return this.updateById(article);
        }
        return false;
    }
    
    @Override
    public boolean incrementCollectCount(Long id) {
        Article article = this.getById(id);
        if (article != null) {
            article.setCollectCount(article.getCollectCount() + 1);
            return this.updateById(article);
        }
        return false;
    }
    
    @Override
    public boolean incrementShareCount(Long id) {
        Article article = this.getById(id);
        if (article != null) {
            article.setShareCount(article.getShareCount() + 1);
            return this.updateById(article);
        }
        return false;
    }
    
    private void fillArticleTags(Article article) {
        article.setTags(tagMapper.selectByArticleId(article.getId()));
    }
    
    private void saveArticleTags(Article article) {
        baseMapper.deleteArticleTags(article.getId());
        if (article.getTagIds() == null || article.getTagIds().isEmpty()) {
            return;
        }
        for (Long tagId : article.getTagIds()) {
            baseMapper.insertArticleTag(article.getId(), tagId);
        }
    }
}
