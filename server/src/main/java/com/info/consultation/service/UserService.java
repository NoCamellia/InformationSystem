package com.info.consultation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.consultation.entity.User;

public interface UserService extends IService<User> {
    
    User getUserByOpenid(String openid);
    
    User wxLogin(String code);
}
