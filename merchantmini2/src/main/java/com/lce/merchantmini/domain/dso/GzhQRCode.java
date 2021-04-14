package com.lce.merchantmini.domain.dso;

import lombok.*;

/**
 * @Author: Ember
 * @Date: 2021/3/18 22:33
 * @Description: 公众号返回的二维码
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GzhQRCode {
    private String ticket;
    private Integer expire_seconds;
    private String url;
}
