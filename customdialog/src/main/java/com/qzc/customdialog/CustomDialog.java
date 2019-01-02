package com.qzc.customdialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.NO_ID;

/**
 * @author Administrator
 * created at 2018/12/21 0021 10:46
 */
public class CustomDialog extends BaseDialog implements View.OnClickListener {
    private final String TAG = this.getClass().getSimpleName();

    private CustomDialog(Builder builder) {
        super(builder);
    }

    public static Builder with(Activity context) {
        return new Builder(context);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        View view = LayoutInflater.from(builder.getContext().getApplicationContext())
                .inflate(builder.getLayoutId(), null);
        getAllChildViews(view);
    }

    /**
     * 遍历所有子view
     *
     * @param view
     * @return
     */
    private List<View> getAllChildViews(View view) {
        List<View> allChildren = new ArrayList<View>();
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                if (child instanceof View) {
                    child.setClickable(true);
                    if (child.getId() != NO_ID) {
                        findViewById(child.getId()).setOnClickListener(this);
//                        Log.i(TAG, child.getClass().getSimpleName()
//                                + "->is not ViewGroup, setOnClick! id: " + child.getId());
                    }
                }
                allChildren.add(child);
                //再次调用本身（递归）
                allChildren.addAll(getAllChildViews(child));
            }
        }
        return allChildren;
    }

    @Override
    public void onClick(View v) {
        if (customClicksListener != null) {
            customClicksListener.onCustomClicks(v, CustomDialog.this);
        }
    }

    public CustomDialog setText(int resId, CharSequence text) {
        if (TextUtils.isEmpty(text)) return this;
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
        setText(resId, builder.getContext().getString(text));
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

    public CustomDialog setVisibility(int resId, int visibility) {
        View view = findViewById(resId);
        view.setVisibility(visibility);
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

    public CustomDialog setCustomClicks(CustomClicksListener listener) {
        this.customClicksListener = listener;
        return this;
    }

    public CustomDialog setCustomRadioGroupListener(int resId, CustomRadioGroupListener
            listener) {
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
        public BaseBuilder setBackgroundDimEnabled(boolean enabled) {
            this.backgroundDimEnable = enabled;
            return this;
        }

        @Override
        public boolean getBackgroundDimEnabled() {
            return backgroundDimEnable;
        }

        @Override
        public int getStyle() {
            if (style != 0) {
                return style;
            } else {
                if (getBackgroundDimEnabled()) return R.style.BackgroundDimEnabled;
                else return R.style.BackgroundDimDisabled;
            }
        }

        @Override
        public Builder setAnimation(int anim) {
            if (anim == TOP_IN) this.animation = R.style.MyTopInDialogAnim;
            else if (anim == BOTTOM_IN) this.animation = R.style.MyBottomInDialogAnim;
            else if (anim == LEFT_IN) this.animation = R.style.MyLeftInDialogAnim;
            else if (anim == RIGHT_IN) this.animation = R.style.MyRightInDialogAnim;
            else if (anim == ALPHA_IN) this.animation = R.style.MyAlphaInDialogAnim;
            else if (anim == SCALE_TOP_IN) this.animation = R.style.MyScaleTopInDialogAnim;
            else if (anim == SCALE_LEFT_IN) this.animation = R.style.MyScaleLeftInDialogAnim;
            else if (anim == SCALE_RIGHT_IN) this.animation = R.style.MyScaleRightInDialogAnim;
            else if (anim == SCALE_BOTTOM_IN) this.animation = R.style.MyScaleBottomInDialogAnim;
            else if (anim == SCALE_LEFT_TOP_IN)
                this.animation = R.style.MyScaleLeftTopInDialogAnim;
            else if (anim == SCALE_RIGHT_TOP_IN)
                this.animation = R.style.MyScaleRightTopInDialogAnim;
            else if (anim == SCALE_LEFT_BOTTOM_IN)
                this.animation = R.style.MyScaleLeftBottomInDialogAnim;
            else if (anim == SCALE_RIGHT_BOTTOM_IN)
                this.animation = R.style.MyScaleRightBottomInDialogAnim;
            else
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
        public Builder setWidthHeight(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        @Override
        public Builder setWidthHeight(float width, float height) {
            this.width = (int) (getContext().getResources().getDisplayMetrics().widthPixels * width);
            this.height = (int) (getContext().getResources().getDisplayMetrics().heightPixels * height);
            return this;
        }

        @Override
        public BaseBuilder setWidthHeight(int width, float height) {
            this.width = width;
            this.height = (int) (getContext().getResources().getDisplayMetrics().heightPixels * height);
            return this;
        }

        @Override
        public BaseBuilder setWidthHeight(float width, int height) {
            this.width = (int) (getContext().getResources().getDisplayMetrics().widthPixels * width);
            this.height = height;
            return this;
        }

        @Override
        public int getWidth() {
            return width;
        }

        @Override
        public int getHeight() {
            return height;
        }

        @Override
        public BaseBuilder setCancelStrategy(boolean cancelable, boolean cancelOnTouchOutside) {
            this.isCancelable = cancelable;
            this.isCancelOnTouchOutside = cancelOnTouchOutside;
            return this;
        }

        @Override
        public boolean getCancelable() {
            return isCancelable;
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
