package com.info.consultation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.info.consultation.entity.Admin;
import com.info.consultation.mapper.AdminMapper;
import com.info.consultation.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Admin login(String username, String password) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            return null;
        }

        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getUsername, username)
               .eq(Admin::getStatus, 1)
               .last("LIMIT 1");
        Admin admin = adminMapper.selectOne(wrapper);
        if (admin == null) {
            return null;
        }

        return passwordEncoder.matches(password, admin.getPassword()) ? admin : null;
    }
}
