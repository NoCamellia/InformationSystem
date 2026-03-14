package com.info.consultation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.consultation.entity.Banner;

import java.util.List;

public interface BannerService extends IService<Banner> {
    
    List<Banner> getActiveBanners();
}
