package com.info.consultation.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName("article")
public class Article implements Serializable {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String title;
    
    private String summary;
    
    private String coverImage;
    
    private String content;
    
    private Long categoryId;
    
    private Long authorId;
    
    private String authorName;
    
    private String source;
    
    private Integer viewCount;
    
    private Integer likeCount;
    
    private Integer collectCount;
    
    private Integer shareCount;
    
    private Integer commentCount;
    
    private Integer isTop;
    
    private Integer isHot;
    
    private Integer isRecommend;
    
    private Integer status;
    
    @TableField(exist = false)
    private List<Tag> tags;
    
    @TableField(exist = false)
    private List<Long> tagIds;
    
    private LocalDateTime publishTime;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
