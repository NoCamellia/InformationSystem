package com.info.consultation.controller.miniapp;

import com.info.consultation.common.Result;
import com.info.consultation.entity.Banner;
import com.info.consultation.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/miniapp/banner")
public class MiniBannerController {
    
    @Autowired
    private BannerService bannerService;
    
    @GetMapping("/list")
    public Result<List<Banner>> getBannerList() {
        List<Banner> banners = bannerService.getActiveBanners();
        return Result.success(banners);
    }
}
