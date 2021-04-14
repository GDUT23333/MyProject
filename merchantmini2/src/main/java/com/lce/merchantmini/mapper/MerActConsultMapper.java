package com.lce.merchantmini.mapper;

import com.lce.merchantmini.domain.dto.Comment;
import com.lce.merchantmini.domain.vo.Consult;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 活动消息
 */
@Repository
@Mapper
public interface MerActConsultMapper {
    /**
     * 获取所有问题
     * @param mId
     * @return
     */
    public List<Consult> getAllConsult(Integer mId);

    /**
     * 回复指定问题
     * @param params
     * @return
     */
    public Integer reply(Map params);

    /**
     * 获取所有评论
     * @param mId
     * @return
     */
    public List<Comment> getAllComment(Integer mId);

    /**
     * 回复评论
     * @param params
     * @return
     */
    public Integer replyComment(Map params);
}
