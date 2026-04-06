package com.info.consultation.controller.miniapp;

import com.info.consultation.common.Result;
import com.info.consultation.entity.User;
import com.info.consultation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/miniapp/user")
public class MiniUserController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> params) {
        String code = params.get("code");
        
        // 调用微信登录服务
        User user = userService.wxLogin(code);
        
        if (user != null) {
            Map<String, Object> data = new HashMap<>();
            data.put("userId", user.getId());
            data.put("openid", user.getOpenid());
            data.put("nickname", user.getNickname());
            data.put("avatar", user.getAvatar());
            // 实际项目中应该生成JWT token
            data.put("token", "mock-token-" + user.getId());
            
            return Result.success(data);
        }
        
        return Result.error("登录失败");
    }
    
    @GetMapping("/info")
    public Result<User> getUserInfo(@RequestParam Long userId) {
        User user = userService.getById(userId);
        return user != null ? Result.success(user) : Result.error("用户不存在");
    }
}
