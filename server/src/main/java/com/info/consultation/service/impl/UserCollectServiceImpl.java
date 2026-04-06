package com.info.consultation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.consultation.entity.UserCollect;
import com.info.consultation.mapper.UserCollectMapper;
import com.info.consultation.service.UserCollectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCollectServiceImpl extends ServiceImpl<UserCollectMapper, UserCollect> implements UserCollectService {
    
    @Override
    public List<UserCollect> getUserCollects(Long userId) {
        LambdaQueryWrapper<UserCollect> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserCollect::getUserId, userId)
               .orderByDesc(UserCollect::getCreateTime);
        return this.list(wrapper);
    }
    
    @Override
    public boolean isCollected(Long userId, Long articleId) {
        LambdaQueryWrapper<UserCollect> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserCollect::getUserId, userId)
               .eq(UserCollect::getArticleId, articleId);
        return this.count(wrapper) > 0;
    }
    
    @Override
    public boolean addCollect(Long userId, Long articleId) {
        if (isCollected(userId, articleId)) {
            return false; // 已收藏
        }
        UserCollect collect = new UserCollect();
        collect.setUserId(userId);
        collect.setArticleId(articleId);
        return this.save(collect);
    }
    
    @Override
    public boolean removeCollect(Long userId, Long articleId) {
        LambdaQueryWrapper<UserCollect> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserCollect::getUserId, userId)
               .eq(UserCollect::getArticleId, articleId);
        return this.remove(wrapper);
    }
}
