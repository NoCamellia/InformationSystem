package com.info.consultation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.consultation.entity.UserCollect;

import java.util.List;

public interface UserCollectService extends IService<UserCollect> {
    
    List<UserCollect> getUserCollects(Long userId);
    
    boolean isCollected(Long userId, Long articleId);
    
    boolean addCollect(Long userId, Long articleId);
    
    boolean removeCollect(Long userId, Long articleId);
}
