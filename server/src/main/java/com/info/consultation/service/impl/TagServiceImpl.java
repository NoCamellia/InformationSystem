package com.info.consultation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.consultation.common.PageResult;
import com.info.consultation.entity.Tag;
import com.info.consultation.mapper.TagMapper;
import com.info.consultation.service.TagService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public PageResult<Tag> getTagPage(Integer current, Integer size, String keyword) {
        Page<Tag> page = new Page<>(current, size);
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) {
            wrapper.like(Tag::getTagName, keyword);
        }
        wrapper.orderByDesc(Tag::getId);
        IPage<Tag> result = this.page(page, wrapper);
        return new PageResult<>(result.getTotal(), result.getRecords(), result.getCurrent(), result.getSize());
    }
}
