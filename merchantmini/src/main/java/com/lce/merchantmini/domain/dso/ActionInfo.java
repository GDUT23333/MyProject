package com.lce.merchantmini.domain.dso;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: Ember
 * @Date: 2021/3/18 22:20
 * @Description: 二维码详细信息
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ActionInfo {

    private Scene scene;


    public ActionInfo(String sceneStr) {
        this.scene = new Scene(sceneStr);
    }

    /**
     * 场景值
     */
    private class Scene{
        private String scene_str;

        private Scene(String scene_str) {
            this.scene_str = scene_str;
        }

        public String getScene_id() {
            return scene_str;
        }

        public void setScene_str(String scene_str) {
            this.scene_str = scene_str;
        }
    }
}
