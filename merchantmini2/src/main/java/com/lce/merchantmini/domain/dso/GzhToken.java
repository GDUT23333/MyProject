package com.lce.merchantmini.domain.dso;

import lombok.*;

/**
 * @Author: Ember
 * @Date: 2021/3/17 17:21
 * @Description: 公众号获取token
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class GzhToken {
    private String access_token;
    private Integer expires_in;
}
