package com.lce.merchantmini.domain.dto.gzh;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AnswerProgressMsg extends BaseMessage{
    private MessageItem content;
    private MessageItem type;
    private MessageItem createTime;

}
