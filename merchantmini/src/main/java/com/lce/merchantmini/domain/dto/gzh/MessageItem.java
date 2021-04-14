package com.lce.merchantmini.domain.dto.gzh;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 公众号消息
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MessageItem {
    private String value;
    private String color;
}
