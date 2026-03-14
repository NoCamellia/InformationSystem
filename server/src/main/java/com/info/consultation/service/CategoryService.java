package com.info.consultation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.consultation.entity.Category;

import java.util.List;

public interface CategoryService extends IService<Category> {
    
    List<Category> getCategoryTree();
    
    List<Category> getCategoryByParentId(Long parentId);
}
