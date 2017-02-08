package com.nmq.verification.generateimage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by niemengquan on 2017/2/8.
 */
public class Cache {
    private static final Map<String,ImageResult> CURRENTVALIDIMAGEMAP=new ConcurrentHashMap<String, ImageResult>();
    public static void put(String key,ImageResult rs){
        CURRENTVALIDIMAGEMAP.put(key,rs);
    }

    public static ImageResult get(String key){
        return CURRENTVALIDIMAGEMAP.get(key);
    }

    public static void remove(String name) {
        CURRENTVALIDIMAGEMAP.remove(name);
    }
}
