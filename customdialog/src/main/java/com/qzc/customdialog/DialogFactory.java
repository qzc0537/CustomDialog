package com.qzc.customdialog;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;

/**
 * @author Administrator
 * created at 2018/12/14 0014 15:43
 */
public class DialogFactory {

    public static final int MATCH_PARENT = WindowManager.LayoutParams.MATCH_PARENT;
    public static final int WRAP_CONTENT = WindowManager.LayoutParams.WRAP_CONTENT;

    public static Builder with(Activity context) {
        return new Builder(context);
    }


    public static class Builder {

        /**
         * 生成用户自定义对话框
         *
         * @return
         */
        public CustomDialog createCustomDialog() {
            return CustomDialog.getInstance(this);
        }

        private Activity context;
        private int layoutId;
        private int style = R.style.MyCommonDialogStyle;
        private int animation;
        private int gravity = Gravity.CENTER;
        private int width = (int) (WindowManager.LayoutParams.MATCH_PARENT * 0.8);
        private int height = WindowManager.LayoutParams.WRAP_CONTENT;
        private boolean isCancelable = true;
        private boolean isCancelOnTouchOutside = false;

        Builder(Activity context) {
            this.context = context;
        }

        public Context getContext() {
            return context;
        }

        public Builder setLayoutId(int layoutId) {
            this.layoutId = layoutId;
            return this;
        }

        public int getLayoutId() {
            return layoutId;
        }

        public Builder setStyle(int style) {
            this.style = style;
            return this;
        }

        public int getStyle() {
            return style;
        }

        public Builder setAnimation(int anim) {
            this.animation = anim;
            return this;
        }

        public int getAnimation() {
            return animation;
        }

        public Builder setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public int getGravity() {
            return gravity;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setWidth(float percent) {
            this.width = (int) (getContext().getResources().getDisplayMetrics().widthPixels * percent);
            return this;
        }

        public int getWidth() {
            return width;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setHeight(float percent) {
            this.height = (int) (getContext().getResources().getDisplayMetrics().heightPixels * percent);
            return this;
        }

        public int getHeight() {
            return height;
        }

        public Builder setCancelable(boolean cancelable) {
            this.isCancelable = cancelable;
            return this;
        }

        public boolean getCancelable() {
            return isCancelable;
        }

        public Builder setCancelOnTouchOutside(boolean cancelOnTouchOutside) {
            this.isCancelOnTouchOutside = cancelOnTouchOutside;
            return this;
        }

        public boolean getCancelOnTouchOutside() {
            return isCancelOnTouchOutside;
        }
    }
}
