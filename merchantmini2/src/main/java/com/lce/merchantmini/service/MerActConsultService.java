package com.lce.merchantmini.service;

import com.lce.merchantmini.domain.dto.Comment;
import com.lce.merchantmini.domain.vo.Consult;

import java.util.List;
import java.util.Map;

public interface MerActConsultService {
    /**
     * 获取所有用户问题
     * @param mId
     * @return
     */
    public List<Consult> getAllConsult(Integer mId);

    /**
     * 回复指定问题
     * @param aId
     * @param reply
     * @return
     */
    public Integer reply(Integer aId,String reply);

    /**
     * 获取所有评论
     * @param mId
     * @return
     */
    public List<Comment> getAllComment(Integer mId);

    /**
     * 回复指定评论
     * @param cId
     * @param reply
     * @return
     */
    public Integer replyComment(Integer cId,String reply);
}
