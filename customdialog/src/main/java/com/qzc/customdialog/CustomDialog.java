package com.qzc.customdialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.annotation.IdRes;
import androidx.annotation.RequiresApi;

import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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
    private SparseArray<View> views;
    private LinkedHashMap<Integer, CustomClickListener> clickMap;
    private View rootView;

    private CustomDialog(Activity context, int layoutId, View view, int theme, int animation,
                         int gravity, int width, int height, float dimAmount, boolean isCancelable,
                         boolean isCancelOnTouchOutside) {
        super(context, theme == 0 ? R.style.BackgroundDimEnabled : theme);
        this.mContext = context;
        this.layoutId = layoutId;
        this.view = view;
        this.width = width;
        this.height = height;
        this.gravity = gravity;
        this.dimAmount = dimAmount;
        setAnimation(animation);
        this.isCancelable = isCancelable;
        this.isCancelOnTouchOutside = isCancelOnTouchOutside;
        this.views = new SparseArray<>();
        this.clickMap = new LinkedHashMap<>();
    }

    public static Builder with(Activity context) {
        return new Builder(context);
    }

    @Override
    protected void initView() {
        rootView = LayoutInflater.from(mContext.getApplicationContext())
                .inflate(layoutId, null);
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
        setText(viewId, mContext.getString(text));
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


    private CustomDialog setAnimation(int anim) {
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


    public static class Builder {
        private Activity context;
        private int layoutId;
        private View view;
        private int theme = R.style.BackgroundDimEnabled;
        private int animation;
        private int gravity = Gravity.CENTER;
        private int width = (int) (WindowManager.LayoutParams.MATCH_PARENT * 0.8);
        private int height = WindowManager.LayoutParams.WRAP_CONTENT;
        private float dimAmount = 0.5f;
        private boolean isCancelable = true;
        private boolean isCancelOnTouchOutside = true;

        public Builder(Activity context) {
            this.context = context;
        }

        public Context getContext() {
            return context;
        }

        public Builder setContext(Activity context) {
            this.context = context;
            return this;
        }

        public int getLayoutId() {
            return layoutId;
        }

        public Builder setLayoutId(int layoutId) {
            this.layoutId = layoutId;
            return this;
        }

        public View getView() {
            return view;
        }

        public Builder setView(View view) {
            this.view = view;
            return this;
        }

        public Builder setWidthHeight(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public Builder setWidthHeight(float width, float height) {
            this.width = (int) (screenWidth * width);
            this.height = (int) (screenHeight * height);
            return this;
        }

        public Builder setWidthHeight(int width, float height) {
            this.width = width;
            this.height = (int) (screenHeight * height);
            return this;
        }

        public Builder setWidthHeight(float width, int height) {
            this.width = (int) (screenWidth * width);
            this.height = height;
            return this;
        }

        public int getTheme() {
            return theme;
        }

        public Builder setTheme(int theme) {
            this.theme = theme;
            return this;
        }

        public int getAnimation() {
            return animation;
        }

        public Builder setAnimation(int animation) {
            this.animation = animation;
            return this;
        }

        public int getGravity() {
            return gravity;
        }

        public Builder setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public float getDimAmount() {
            return dimAmount;
        }

        public Builder setDimAmount(float dimAmount) {
            this.dimAmount = dimAmount;
            return this;
        }

        public Builder setCancelStrategy(boolean cancelable, boolean cancelOnTouchOutside) {
            isCancelable = cancelable;
            isCancelOnTouchOutside = cancelOnTouchOutside;
            return this;
        }

        public boolean isCancelable() {
            return isCancelable;
        }

        public Builder setCancelable(boolean cancelable) {
            isCancelable = cancelable;
            return this;
        }

        public boolean isCancelOnTouchOutside() {
            return isCancelOnTouchOutside;
        }

        public Builder setCancelOnTouchOutside(boolean cancelOnTouchOutside) {
            isCancelOnTouchOutside = cancelOnTouchOutside;
            return this;
        }

        public CustomDialog build() {
            CustomDialog dialog = new CustomDialog(context, layoutId, view, theme, animation, gravity, width,
                    height, dimAmount, isCancelable, isCancelOnTouchOutside);
            dialog.show();
            return dialog;
        }
    }
}
