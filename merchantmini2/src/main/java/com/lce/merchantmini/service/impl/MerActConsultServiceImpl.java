package com.lce.merchantmini.service.impl;

import com.lce.merchantmini.domain.dto.Comment;
import com.lce.merchantmini.domain.vo.Consult;
import com.lce.merchantmini.mapper.MerActConsultMapper;
import com.lce.merchantmini.service.MerActConsultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MerActConsultServiceImpl implements MerActConsultService {

    private MerActConsultMapper consultMapper;

    @Autowired
    public void setConsultMapper(MerActConsultMapper consultMapper) {
        this.consultMapper = consultMapper;
    }

    @Override
    public List<Consult> getAllConsult(Integer mId) {
        return this.consultMapper.getAllConsult(mId);
    }

    @Override
    public Integer reply(Integer aId,String reply) {
        Map<String,Object> params = new HashMap<>(2);
        params.put("id",aId);
        params.put("reply",reply);
        return this.consultMapper.reply(params);
    }

    @Override
    public List<Comment> getAllComment(Integer mId) {
        return this.consultMapper.getAllComment(mId);
    }

    @Override
    public Integer replyComment(Integer cId, String reply) {
        Map<String,Object> params = new HashMap<>(2);
        params.put("cId",cId);
        params.put("reply",reply);
        return this.consultMapper.replyComment(params);
    }
}
