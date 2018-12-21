package com.qzc.customdialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;

/**
 * @author Administrator
 * created at 2018/12/19 0019 17:56
 */
public abstract class BaseDialog extends Dialog {

    protected DialogFactory.Builder builder;

    public BaseDialog(DialogFactory.Builder builder) {
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
        if (builder.getAnimation() != 0){
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
