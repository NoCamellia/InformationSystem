package com.info.consultation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.consultation.entity.UserHistory;
import com.info.consultation.mapper.UserHistoryMapper;
import com.info.consultation.service.UserHistoryService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserHistoryServiceImpl extends ServiceImpl<UserHistoryMapper, UserHistory> implements UserHistoryService {
    
    @Override
    public List<UserHistory> getUserHistory(Long userId, Integer limit) {
        LambdaQueryWrapper<UserHistory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserHistory::getUserId, userId)
               .orderByDesc(UserHistory::getViewTime)
               .last("LIMIT " + limit);
        return this.list(wrapper);
    }
    
    @Override
    public boolean addHistory(Long userId, Long articleId) {
        // 检查是否已经存在该文章的浏览记录
        LambdaQueryWrapper<UserHistory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserHistory::getUserId, userId)
               .eq(UserHistory::getArticleId, articleId);
        UserHistory existingHistory = this.getOne(wrapper);
        
        if (existingHistory != null) {
            // 如果已存在，更新浏览时间
            existingHistory.setViewTime(LocalDateTime.now());
            return this.updateById(existingHistory);
        } else {
            // 如果不存在，创建新记录
            UserHistory history = new UserHistory();
            history.setUserId(userId);
            history.setArticleId(articleId);
            history.setViewTime(LocalDateTime.now());
            return this.save(history);
        }
    }
}
