package com.lce.merchantmini.domain.dto.gzh;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BaseMessage {
    private String openid;
    private String templateId;
    private String url;
    private Map miniProgram;
    private MessageItem title;
    private MessageItem remark;

}
