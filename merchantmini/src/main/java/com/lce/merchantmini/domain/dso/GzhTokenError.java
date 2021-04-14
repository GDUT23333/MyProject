package com.lce.merchantmini.domain.dso;

import com.google.gson.annotations.SerializedName;

/**
 * @author: Ember
 * @create: 2021-02-28 17:31
 **/


public class GzhTokenError {

    @SerializedName("errcode")
    private String errCode;
    @SerializedName("errmsg")
    private String errMsg;

}
