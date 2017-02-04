package com.nmq.verification.generateimage;

import java.awt.image.BufferedImage;

/**
 * Created by niemengquan on 2017/2/4.
 */
public class BufferedImageWrap {
    private boolean key;
    private BufferedImage bufferedImage;

    public BufferedImageWrap(boolean key, BufferedImage bufferedImage) {
        this.key = key;
        this.bufferedImage = bufferedImage;
    }

    public boolean isKey() {
        return key;
    }

    public void setKey(boolean key) {
        this.key = key;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }
}
