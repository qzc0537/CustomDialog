package com.qzc.customdialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author Administrator
 * created at 2018/12/19 0019 17:56
 */
public abstract class BaseDialog extends Dialog {
    public static final int MATCH_PARENT = WindowManager.LayoutParams.MATCH_PARENT;
    public static final int WRAP_CONTENT = WindowManager.LayoutParams.WRAP_CONTENT;
    public static final int TOP_IN = 1000;
    public static final int BOTTOM_IN = 1001;
    public static final int LEFT_IN = 1002;
    public static final int RIGHT_IN = 1003;
    public static final int ALPHA_IN = 1004;
    public static final int SCALE_TOP_IN = 1006;
    public static final int SCALE_LEFT_IN = 1007;
    public static final int SCALE_RIGHT_IN = 1008;
    public static final int SCALE_BOTTOM_IN = 1009;
    public static final int SCALE_LEFT_TOP_IN = 10010;
    public static final int SCALE_RIGHT_TOP_IN = 10011;
    public static final int SCALE_LEFT_BOTTOM_IN = 10012;
    public static final int SCALE_RIGHT_BOTTOM_IN = 10013;
    protected BaseBuilder builder;

    public BaseDialog(CustomDialog.Builder builder) {
        super(builder.getContext(), builder.getStyle());
        this.builder = builder;
        init();
    }

    private void init() {
        if (getWindow() == null) return;
        if (builder.getLayoutId() != 0) setContentView(builder.getLayoutId());
        initWindow(getWindow());
        initView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initEvent();
    }

    private void initWindow(Window window) {
        window.setGravity(builder.getGravity());
        window.getAttributes().width = builder.getWidth();
        window.getAttributes().height = builder.getHeight();
        if (builder.getAnimation() != 0) {
            window.setWindowAnimations(builder.getAnimation());
        }
        setCancelable(builder.getCancelable());
        setCanceledOnTouchOutside(builder.getCancelOnTouchOutside());
    }

    protected void initView() {
    }

    protected void initData() {
    }

    protected void initEvent() {
    }
}
