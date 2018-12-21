package com.qzc.customdialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * @author Administrator
 * created at 2018/12/21 0021 10:46
 */
public class CustomDialog extends BaseDialog implements View.OnClickListener {

    private CustomDialog(Builder builder) {
        super(builder);
    }

    public static Builder with(Activity context) {
        return new Builder(context);
    }


    public CustomDialog setText(int resId, CharSequence text) {
        View view = findViewById(resId);
        if (view instanceof TextView) {
            ((TextView) view).setText(text);
        } else if (view instanceof Button) {
            ((Button) view).setText(text);
        } else if (view instanceof CompoundButton) {
            ((CompoundButton) view).setText(text);
        }
        return this;
    }

    public CustomDialog setText(int resId, int text) {
        View view = findViewById(resId);
        if (view instanceof TextView) {
            ((TextView) view).setText(text);
        } else if (view instanceof Button) {
            ((Button) view).setText(text);
        } else if (view instanceof CompoundButton) {
            ((CompoundButton) view).setText(text);
        }
        return this;
    }

    public CustomDialog setImageDrawable(int resId, Drawable drawable) {
        ImageView imageView = findViewById(resId);
        imageView.setImageDrawable(drawable);
        return this;
    }

    public CustomDialog setImageResource(int resId, int resourceId) {
        ImageView imageView = findViewById(resId);
        imageView.setImageResource(resourceId);
        return this;
    }

    public CustomDialog setImageAlpha(int resId, int alpha) {
        ImageView imageView = findViewById(resId);
        imageView.setImageAlpha(alpha);
        return this;
    }

    public CustomDialog setEnable(int resId, boolean enable) {
        View view = findViewById(resId);
        view.setEnabled(enable);
        return this;
    }

    public CustomDialog setChecked(int resId, boolean checked) {
        View view = findViewById(resId);
        if (view instanceof CompoundButton) {
            ((CompoundButton) view).setChecked(checked);
        }
        return this;
    }

    public CustomDialog setCustomClick(int resId, CustomClickListener listener) {
        this.customClickListener = listener;
        View view = findViewById(resId);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (customClickListener != null) {
                    customClickListener.onCustomClick(view, CustomDialog.this);
                }
            }
        });
        return this;
    }

    public CustomDialog setCustomClicks(int... ids) {
        for (int id : ids) {
            findViewById(id).setOnClickListener(this);
        }
        return this;
    }

    public CustomDialog setCustomClicks(CustomClicksListener listener) {
        this.customClicksListener = listener;
        return this;
    }

    public CustomDialog setCustomRadioGroupListener(int resId, CustomRadioGroupListener listener) {
        this.customRadioGroupListener = listener;
        RadioGroup radioGroup = findViewById(resId);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (customRadioGroupListener != null) {
                    customRadioGroupListener.onCustomCheckedChange(group, checkedId, CustomDialog.this);
                }
            }
        });
        return this;
    }

    public CustomDialog setCustomCheckBoxListener(int resId, CustomCheckBoxListener listener) {
        this.customCheckBoxListener = listener;
        CheckBox checkBox = findViewById(resId);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (customRadioGroupListener != null) {
                    customCheckBoxListener.onCustomCheckedChange(buttonView, isChecked, CustomDialog.this);
                }
            }
        });
        return this;
    }

    @Override
    public void onClick(View v) {
        if (customClicksListener != null) {
            customClicksListener.onCustomClicks(v, CustomDialog.this);
        }
    }


    private CustomClickListener customClickListener;
    private CustomClicksListener customClicksListener;
    private CustomRadioGroupListener customRadioGroupListener;

    private CustomCheckBoxListener customCheckBoxListener;

    public interface CustomClickListener {
        void onCustomClick(View view, DialogInterface dialog);
    }

    public interface CustomClicksListener {
        void onCustomClicks(View view, DialogInterface dialog);
    }

    public interface CustomRadioGroupListener {
        void onCustomCheckedChange(RadioGroup group, int checkedId, DialogInterface dialog);
    }

    public interface CustomCheckBoxListener {
        void onCustomCheckedChange(CompoundButton buttonView, boolean isChecked, DialogInterface dialog);
    }


    public static class Builder extends BaseBuilder {

        Builder(Activity context) {
            super(context);
        }

        @Override
        public Context getContext() {
            return context;
        }

        @Override
        public Builder setLayoutId(int layoutId) {
            this.layoutId = layoutId;
            return this;
        }

        @Override
        public int getLayoutId() {
            return layoutId;
        }

        @Override
        public Builder setStyle(int style) {
            this.style = style;
            return this;
        }

        @Override
        public int getStyle() {
            return style;
        }

        @Override
        public Builder setAnimation(int anim) {
            this.animation = anim;
            return this;
        }

        @Override
        public int getAnimation() {
            return animation;
        }

        @Override
        public Builder setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        @Override
        public int getGravity() {
            return gravity;
        }

        @Override
        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        @Override
        public Builder setWidth(float percent) {
            this.width = (int) (getContext().getResources().getDisplayMetrics().widthPixels * percent);
            return this;
        }

        @Override
        public int getWidth() {
            return width;
        }

        @Override
        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        @Override
        public Builder setHeight(float percent) {
            this.height = (int) (getContext().getResources().getDisplayMetrics().heightPixels * percent);
            return this;
        }

        @Override
        public int getHeight() {
            return height;
        }

        @Override
        public Builder setCancelable(boolean cancelable) {
            this.isCancelable = cancelable;
            return this;
        }

        @Override
        public boolean getCancelable() {
            return isCancelable;
        }

        @Override
        public Builder setCancelOnTouchOutside(boolean cancelOnTouchOutside) {
            this.isCancelOnTouchOutside = cancelOnTouchOutside;
            return this;
        }

        @Override
        public boolean getCancelOnTouchOutside() {
            return isCancelOnTouchOutside;
        }

        @Override
        public CustomDialog create() {
            CustomDialog dialog = new CustomDialog(this);
            dialog.show();
            return dialog;
        }
    }


}
