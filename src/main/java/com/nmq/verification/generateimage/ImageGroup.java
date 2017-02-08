package com.nmq.verification.generateimage;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 一个图片的分组
 * Created by niemengquan on 2017/2/4.
 */
public class ImageGroup {
    private String name;//图片分组的中文名称
    private int count;//图片分组的数量
    private Set<String> images;//图片分组中的所有的图片的地址

    public ImageGroup(String name,int count,String ... imageNames){
        this.name=name;
        this.count=count;
        this.images=new HashSet<String>();
        this.images.addAll(Arrays.asList(imageNames));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Set<String> getImages() {
        return images;
    }

    public void setImages(Set<String> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "ImageGroup{" +
                "name='" + name + '\'' +
                ", count=" + count +
                ", images=" + images +
                '}';
    }
}
