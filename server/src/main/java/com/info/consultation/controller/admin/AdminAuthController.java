package com.info.consultation.controller.admin;

import com.info.consultation.common.Result;
import com.info.consultation.entity.Admin;
import com.info.consultation.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/auth")
public class AdminAuthController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> params) {
        Admin admin = adminService.login(params.get("username"), params.get("password"));
        if (admin == null) {
            return Result.error("用户名或密码错误");
        }

        Map<String, Object> data = new HashMap<>();
        data.put("token", "admin-token-" + admin.getId() + "-" + System.currentTimeMillis());
        data.put("adminId", admin.getId());
        data.put("username", admin.getUsername());
        data.put("realName", admin.getRealName());
        data.put("roleId", admin.getRoleId());
        data.put("loginTime", LocalDateTime.now());
        return Result.success(data);
    }
}
