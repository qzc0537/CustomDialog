package com.qzc.customdialog;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.View;
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

    private CustomDialog(DialogFactory.Builder builder) {
        super(builder);
    }

    public static CustomDialog getInstance(DialogFactory.Builder builder) {
        return new CustomDialog(builder);
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


}
