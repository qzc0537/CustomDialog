package com.qzc.customdialog;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.qzc.customdialog.utils.DisplayUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static android.view.View.NO_ID;

/**
 * @author Administrator
 * created at 2018/12/21 0021 10:46
 */
public class CustomDialog extends BaseDialog implements View.OnClickListener {
    private final String TAG = this.getClass().getSimpleName();
    private final SparseArray<View> views;
    private final LinkedHashMap<Integer, CustomClickListener> clickMap;
    private View rootView;

    private CustomDialog(Builder builder) {
        super(builder);
        this.views = new SparseArray<>();
        this.clickMap = new LinkedHashMap<>();
    }

    public static BaseBuilder with(Activity context) {
        return new Builder(context);
    }

    @Override
    protected void initView() {
        rootView = LayoutInflater.from(builder.getContext().getApplicationContext())
                .inflate(builder.getLayoutId(), null);
        getAllChildViews(rootView);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    public void onClick(View v) {
        if (customClicksListener != null) {
            customClicksListener.onCustomClicks(v, v.getRootView(), CustomDialog.this);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(@IdRes int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
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
                        getView(child.getId()).setOnClickListener(this);
                        Log.i(TAG, child.getClass().getSimpleName()
                                + "->is not ViewGroup, setOnClick! id: " + child.getId());
                    }
                }
                allChildren.add(child);
                //再次调用本身（递归）
                allChildren.addAll(getAllChildViews(child));
            }
        }
        return allChildren;
    }

    public CustomDialog setText(int viewId, CharSequence text) {
        if (TextUtils.isEmpty(text)) return this;
        View view = getView(viewId);
        if (view instanceof TextView) {
            ((TextView) view).setText(text);
        } else if (view instanceof Button) {
            ((Button) view).setText(text);
        } else if (view instanceof CompoundButton) {
            ((CompoundButton) view).setText(text);
        }
        return this;
    }

    public CustomDialog setText(int viewId, int text) {
        setText(viewId, builder.getContext().getString(text));
        return this;
    }

    public CustomDialog setImageDrawable(int viewId, Drawable drawable) {
        ImageView imageView = getView(viewId);
        imageView.setImageDrawable(drawable);
        return this;
    }

    public CustomDialog setImageResource(int viewId, int resourceId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resourceId);
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public CustomDialog setImageAlpha(int viewId, int alpha) {
        ImageView imageView = getView(viewId);
        imageView.setImageAlpha(alpha);
        return this;
    }

    public CustomDialog setEnable(int viewId, boolean enable) {
        View view = getView(viewId);
        view.setEnabled(enable);
        return this;
    }

    public CustomDialog setChecked(int viewId, boolean checked) {
        View view = getView(viewId);
        if (view instanceof CompoundButton) {
            ((CompoundButton) view).setChecked(checked);
        }
        return this;
    }

    public CustomDialog setVisibility(int viewId, int visibility) {
        View view = getView(viewId);
        view.setVisibility(visibility);
        return this;
    }

    public CustomDialog setCompoundDrawables(int viewId, int left, int top, int right, int bottom) {
        View view = getView(viewId);
        if (view instanceof TextView) {
            ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        } else if (view instanceof Button) {
            ((Button) view).setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        } else if (view instanceof RadioButton) {
            ((RadioButton) view).setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        } else if (view instanceof CheckBox) {
            ((CheckBox) view).setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        }
        return this;
    }

    public CustomDialog setCustomClick(int viewId, CustomClickListener listener) {
        clickMap.put(viewId, listener);
        View view = getView(viewId);
        if (view != null) {
            if (!view.isClickable()) {
                view.setClickable(true);
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickMap.get(view.getId()) != null) {
                        clickMap.get(view.getId()).onCustomClick(view, CustomDialog.this);
                    }
                }
            });
        }
        return this;
    }

    public CustomDialog setCustomClicks(CustomClicksListener listener) {
        this.customClicksListener = listener;
        return this;
    }

    public CustomDialog setCustomRadioGroupListener(int viewId, CustomRadioGroupListener
            listener) {
        this.customRadioGroupListener = listener;
        RadioGroup radioGroup = getView(viewId);
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

    public CustomDialog setCustomCheckBoxListener(int viewId, CustomCheckBoxListener listener) {
        this.customCheckBoxListener = listener;
        CheckBox checkBox = getView(viewId);
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


    private CustomClicksListener customClicksListener;
    private CustomRadioGroupListener customRadioGroupListener;
    private CustomCheckBoxListener customCheckBoxListener;

    public interface CustomClickListener {
        void onCustomClick(View view, DialogInterface dialog);
    }

    public interface CustomClicksListener {
        void onCustomClicks(View view, View rootView, DialogInterface dialog);
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
        public Builder setContentView(int layoutId) {
            this.layoutId = layoutId;
            return this;
        }

        @Override
        public BaseBuilder setContentView(View contentView) {
            this.contentView = contentView;
            return this;
        }

        @Override
        public int getLayoutId() {
            return layoutId;
        }

        @Override
        public View getContentView() {
            return contentView;
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
            else if (anim == SCALE_IN)
                this.animation = R.style.MyScaleInDialogAnim;
            else if (anim == ROTATE_IN)
                this.animation = R.style.MyRotateInDialogAnim;
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
        public BaseBuilder setDimAmount(float dimAmount) {
            this.dimAmount = dimAmount;
            return this;
        }

        @Override
        public float getDimAmount() {
            return dimAmount;
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
