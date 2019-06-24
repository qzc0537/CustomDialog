package com.qzc.customdialog.utils;

import android.content.Context;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * @author Administrator
 * created at 2019/1/25 0025 16:12
 */
public class AnimationUtils {

    public static final int DURATION = 300;

    /**
     * 渐变的一种效果
     *
     * @param fromAlpha
     * @param toAlpha
     */
    public static Animation setAlphaAnimation(float fromAlpha, float toAlpha) {
        Animation alphaAnimation = new AlphaAnimation(fromAlpha, toAlpha);
        alphaAnimation.setDuration(DURATION);
        return alphaAnimation;
    }

    /**
     * 定义从屏幕顶部进入的动画效果
     *
     * @param context
     */
    public static Animation inFromTopAnimation(Context context) {
        Animation inFromTop = new TranslateAnimation(0.0f, 0.0f, -DisplayUtils.getScreenHeight(context), 0.0f);
        inFromTop.setFillAfter(true);
        inFromTop.setDuration(DURATION);
        inFromTop.setRepeatMode(Animation.ZORDER_BOTTOM);
        return inFromTop;
    }

    /**
     * 定义从屏幕顶部退出的动画效果
     *
     * @param context
     */
    public static Animation outToTopAnimation(Context context) {
        Animation outToTop = new TranslateAnimation(0.0f, 0.0f, 0.0f, -DisplayUtils.getScreenHeight(context));
        outToTop.setFillAfter(true);
        outToTop.setDuration(DURATION);
        outToTop.setRepeatMode(Animation.ZORDER_BOTTOM);
        return outToTop;
    }

    /**
     * 定义从屏幕底部进入的动画效果
     *
     * @param context
     */
    public static Animation inFromBottomAnimation(Context context) {
        Animation inFromBottom = new TranslateAnimation(0.0f, 0.0f, DisplayUtils.getScreenHeight(context), 0.0f);
        inFromBottom.setFillAfter(true);
        inFromBottom.setDuration(DURATION);
        inFromBottom.setRepeatMode(Animation.ZORDER_TOP);
        return inFromBottom;
    }

    /**
     * 定义从屏幕底部退出的动画效果
     *
     * @param context
     */
    public static Animation outToBottomAnimation(Context context) {
        Animation outToBottom = new TranslateAnimation(0.0f, 0.0f, 0.0f, DisplayUtils.getScreenHeight(context));
        outToBottom.setFillAfter(true);
        outToBottom.setDuration(DURATION);
        outToBottom.setRepeatMode(Animation.ZORDER_NORMAL);
        return outToBottom;
    }

    /**
     * 定义从左侧进入的动画效果
     */
    public static Animation inFromLeftAnimation() {
        Animation inFromLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromLeft.setDuration(DURATION);
        inFromLeft.setInterpolator(new AccelerateInterpolator());
        return inFromLeft;
    }


    /**
     * 定义从左侧退出的动画效果
     */
    public static Animation outToLeftAnimation() {
        Animation outtoLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoLeft.setDuration(DURATION);
        outtoLeft.setInterpolator(new AccelerateInterpolator());
        return outtoLeft;
    }


    /**
     * 定义从右侧进入的动画效果
     */
    public static Animation inFromRightAnimation() {
        Animation inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromRight.setDuration(DURATION);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        return inFromRight;
    }

    /**
     * 定义从右侧退出时的动画效果
     */
    public static Animation outToRightAnimation() {
        Animation outtoRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoRight.setDuration(DURATION);
        outtoRight.setInterpolator(new AccelerateInterpolator());
        return outtoRight;
    }

    public static Animation scaleInFromCenter() {
        Animation animation = new ScaleAnimation(
                0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_PARENT, 0.5f,
                Animation.RELATIVE_TO_PARENT, 0.5f
        );
        animation.setDuration(DURATION);
        animation.setInterpolator(new OvershootInterpolator());
        return animation;
    }
}
