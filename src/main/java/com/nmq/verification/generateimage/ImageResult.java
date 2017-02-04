package com.nmq.verification.generateimage;

import java.util.Set;

/**
 * Created by niemengquan on 2017/2/4.
 */
public class ImageResult {
    private String name;
    private Set<Integer> keySet;
    private String uniqueKey;//cookie中存放的标识
    private String tip;//目标图片的中文名称

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Integer> getKeySet() {
        return keySet;
    }

    public void setKeySet(Set<Integer> keySet) {
        this.keySet = keySet;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
