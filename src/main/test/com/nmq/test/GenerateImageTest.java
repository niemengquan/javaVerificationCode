package com.nmq.test;

import com.nmq.verification.generateimage.Image;
import com.nmq.verification.generateimage.ImageResult;

import java.io.IOException;

/**
 * Created by niemengquan on 2017/2/8.
 */
public class GenerateImageTest {
    public static void main(String[] args) throws IOException {
        ImageResult ir= Image.generateImage();
        System.out.println(ir);
    }
}
