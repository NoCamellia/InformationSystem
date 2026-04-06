package com.info.consultation.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("user_collect")
public class UserCollect implements Serializable {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long userId;
    
    private Long articleId;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
