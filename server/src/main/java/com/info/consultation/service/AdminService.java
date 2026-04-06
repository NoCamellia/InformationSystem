package com.info.consultation.service;

import com.info.consultation.entity.Admin;

public interface AdminService {
    Admin login(String username, String password);
}
