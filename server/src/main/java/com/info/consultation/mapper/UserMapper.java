package com.info.consultation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.info.consultation.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
