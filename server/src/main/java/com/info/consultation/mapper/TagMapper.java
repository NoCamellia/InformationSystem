package com.info.consultation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.info.consultation.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TagMapper extends BaseMapper<Tag> {

    @Select("""
        SELECT t.id, t.tag_name, t.color, t.use_count, t.status, t.create_time, t.update_time
        FROM tag t
        INNER JOIN article_tag at ON t.id = at.tag_id
        WHERE at.article_id = #{articleId} AND t.status = 1
        ORDER BY t.id ASC
        """)
    List<Tag> selectByArticleId(@Param("articleId") Long articleId);
}
