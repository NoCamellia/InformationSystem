package com.info.consultation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.consultation.entity.User;
import com.info.consultation.mapper.UserMapper;
import com.info.consultation.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    
    @Override
    public User getUserByOpenid(String openid) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getOpenid, openid);
        return this.getOne(wrapper);
    }
    
    @Override
    public User wxLogin(String code) {
        // 这里简化处理，实际项目中需要调用微信API
        // 1. 使用code调用微信API获取openid
        // 2. 查询数据库是否存在该用户
        // 3. 不存在则创建新用户
        // 4. 返回用户信息
        
        // 模拟返回一个测试用户
        String openid = "oTest_" + System.currentTimeMillis();
        User user = getUserByOpenid(openid);
        
        if (user == null) {
            user = new User();
            user.setOpenid(openid);
            user.setNickname("微信用户");
            user.setStatus(1);
            this.save(user);
        }
        
        return user;
    }
}
