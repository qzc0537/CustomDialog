package com.qzc.customdialog.image;

import android.widget.ImageView;

/**
 * created by qinzhichang at 2019/12/01 11:25
 * desc:
 */
public abstract class CommonImageLoader {

    protected String mUrl;

    public CommonImageLoader(String url) {
        this.mUrl = url;
    }

    public String getUrl() {
        return mUrl;
    }

    public abstract void loadImage(String url, ImageView imageView);
}
