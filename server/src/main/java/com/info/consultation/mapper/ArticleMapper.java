package com.info.consultation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArticleMapper extends BaseMapper<com.info.consultation.entity.Article> {

    @Delete("DELETE FROM article_tag WHERE article_id = #{articleId}")
    void deleteArticleTags(@Param("articleId") Long articleId);

    @Insert("INSERT INTO article_tag(article_id, tag_id) VALUES(#{articleId}, #{tagId})")
    void insertArticleTag(@Param("articleId") Long articleId, @Param("tagId") Long tagId);
}
