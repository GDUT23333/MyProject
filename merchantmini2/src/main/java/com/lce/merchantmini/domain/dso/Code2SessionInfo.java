package com.lce.merchantmini.domain.dso;

import lombok.*;

/**
 * 接收登录凭证校验返回信息
 */
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Code2SessionInfo {

    private String openid;
    private String sessionKey;
    private String unionId;
    private String errCode;
    private String errMsg;
}
