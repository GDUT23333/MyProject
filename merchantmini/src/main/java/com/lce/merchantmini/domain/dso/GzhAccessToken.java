package com.lce.merchantmini.domain.dso;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: Ember
 * @Date: 2021/3/18 21:54
 * @Description: 公众号的access_token
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GzhAccessToken {
    private String access_token;
    private Integer expires_in;
}
