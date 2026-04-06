package com.info.consultation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.consultation.common.PageResult;
import com.info.consultation.entity.Tag;

public interface TagService extends IService<Tag> {
    PageResult<Tag> getTagPage(Integer current, Integer size, String keyword);
}
