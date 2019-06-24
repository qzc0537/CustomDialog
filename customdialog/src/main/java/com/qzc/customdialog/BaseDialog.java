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
    public static final int MATCH = WindowManager.LayoutParams.MATCH_PARENT;
    public static final int WRAP = WindowManager.LayoutParams.WRAP_CONTENT;
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
    protected BaseBuilder builder;

    public BaseDialog(CustomDialog.Builder builder) {
        super(builder.getContext(), builder.getStyle());
        this.builder = builder;
    }

    private void init() {
        Window window = getWindow();
        if (window == null) return;

        if (builder.getLayoutId() != 0) {
            setContentView(builder.getLayoutId());
        } else {
            setContentView(builder.getContentView());
        }
        window.setGravity(builder.getGravity());
        window.getAttributes().width = builder.getWidth();
        window.getAttributes().height = builder.getHeight();
        if (builder.getAnimation() != 0) {
            window.setWindowAnimations(builder.getAnimation());
        }
        setCancelable(builder.getCancelable());
        setCanceledOnTouchOutside(builder.getCancelOnTouchOutside());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initView();
    }

    protected void initView() {
    }

}
