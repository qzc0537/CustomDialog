package com.qzc.customdialog;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;

public abstract class BaseBuilder {
    protected Activity context;
    protected int layoutId;
    protected int style = R.style.BackgroundDimEnabled;
    protected int animation;
    protected int gravity = Gravity.CENTER;
    protected int width = (int) (WindowManager.LayoutParams.MATCH_PARENT * 0.8);
    protected int height = WindowManager.LayoutParams.WRAP_CONTENT;
    protected boolean backgroundDimEnable = true;
    protected boolean isCancelable = true;
    protected boolean isCancelOnTouchOutside = false;

    BaseBuilder(Activity context) {
        this.context = context;
    }

    public abstract Context getContext();

    public abstract BaseBuilder setLayoutId(int layoutId);

    public abstract int getLayoutId();

    public abstract BaseBuilder setStyle(int style);

    public abstract BaseBuilder setBackgroundDimEnabled(boolean enabled);

    public abstract boolean getBackgroundDimEnabled();

    public abstract int getStyle();

    public abstract BaseBuilder setAnimation(int anim);

    public abstract int getAnimation();

    public abstract BaseBuilder setGravity(int gravity);

    public abstract int getGravity();

    public abstract BaseBuilder setWidthHeight(int width, int height);

    public abstract BaseBuilder setWidthHeight(float width, float height);

    public abstract BaseBuilder setWidthHeight(int width, float height);

    public abstract BaseBuilder setWidthHeight(float width, int height);

    public abstract int getWidth();

    public abstract int getHeight();

    public abstract BaseBuilder setCancelStrategy(boolean cancelable, boolean cancelOnTouchOutside);

    public abstract boolean getCancelable();

    public abstract boolean getCancelOnTouchOutside();

    public abstract CustomDialog create();
}
