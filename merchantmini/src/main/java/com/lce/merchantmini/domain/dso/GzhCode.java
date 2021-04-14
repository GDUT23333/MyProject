package com.lce.merchantmini.domain.dso;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: Ember
 * @Date: 2021/3/17 17:21
 * @Description: 公众号code
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GzhCode {
    private String access_token;
    private Integer expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
}
