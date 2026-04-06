package com.info.consultation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.consultation.entity.UserHistory;

import java.util.List;

public interface UserHistoryService extends IService<UserHistory> {
    
    List<UserHistory> getUserHistory(Long userId, Integer limit);
    
    boolean addHistory(Long userId, Long articleId);
}
