package com.qzc.customdialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

/**
 * @author Administrator
 * created at 2018/12/19 0019 17:56
 */
public abstract class BaseDialog extends Dialog {
    public static final int MATCH = WindowManager.LayoutParams.MATCH_PARENT;
    public static final int WRAP = WindowManager.LayoutParams.WRAP_CONTENT;
    public static final int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    public static final int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    public static final int TOP_IN = 1001;
    public static final int BOTTOM_IN = 1002;
    public static final int LEFT_IN = 1003;
    public static final int RIGHT_IN = 1004;
    public static final int ALPHA_IN = 1005;
    public static final int SCALE_TOP_IN = 1006;
    public static final int SCALE_LEFT_IN = 1007;
    public static final int SCALE_RIGHT_IN = 1008;
    public static final int SCALE_BOTTOM_IN = 1009;
    public static final int SCALE_LEFT_TOP_IN = 1010;
    public static final int SCALE_RIGHT_TOP_IN = 1011;
    public static final int SCALE_LEFT_BOTTOM_IN = 1012;
    public static final int SCALE_RIGHT_BOTTOM_IN = 1013;
    public static final int SCALE_IN = 1014;
    public static final int ROTATE_IN = 1015;

    protected Context mContext;
    protected int layoutId;
    protected View view;
    protected int theme = R.style.BackgroundDimEnabled;
    protected int animation;
    protected int gravity = Gravity.CENTER;
    protected int width = (int) (WindowManager.LayoutParams.MATCH_PARENT * 0.8);
    protected int height = WindowManager.LayoutParams.WRAP_CONTENT;
    protected float dimAmount = 0.5f;
    protected boolean isCancelable = true;
    protected boolean isCancelOnTouchOutside = true;

    public BaseDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (layoutId != 0) {
            setContentView(layoutId);
        } else {
            setContentView(view);
        }
        initWindow();
        initView();
    }

    private void initWindow() {
        Window window = getWindow();
        if (window != null) {
            window.setGravity(gravity);
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = width;
            params.height = height;
            params.dimAmount = dimAmount;
            if (animation != 0) {
                window.setWindowAnimations(animation);
            }
            setCancelable(isCancelable);
            setCanceledOnTouchOutside(isCancelOnTouchOutside);
        }
    }

    protected void initView() {
    }

}
