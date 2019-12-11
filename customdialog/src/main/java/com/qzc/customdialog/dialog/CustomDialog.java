package com.qzc.customdialog.dialog;

import android.app.Dialog;
import android.content.Context;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;

import com.qzc.customdialog.R;
import com.qzc.customdialog.image.CommonImageLoader;
import com.qzc.customdialog.interfaces.OnCustomCheckBoxListener;
import com.qzc.customdialog.interfaces.OnCustomClickListener;
import com.qzc.customdialog.interfaces.OnCustomRadioGroupListener;
import com.qzc.customdialog.interfaces.OnCustomTextChangeListener;

/**
 * @author Administrator
 * created at 2018/12/21 0021 10:46
 */
public class CustomDialog extends Dialog {
    public static final int MATCH = WindowManager.LayoutParams.MATCH_PARENT;
    public static final int WRAP = WindowManager.LayoutParams.WRAP_CONTENT;
    public static final int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    public static final int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    private ViewHelper mViewHelper;
    private int mPriority = -1;
    private String mHostName;

    protected CustomDialog(Context context, int theme) {
        super(context, theme);
    }

    protected void init(BaseBuilder builder) {
        if (null != builder.mContentView) {
            mViewHelper = new ViewHelper(builder.mContentView);
        }
        if (null == mViewHelper) {
            throw new IllegalArgumentException("The xml layout of dialog should not be null");
        }
        setContentView(mViewHelper.getContentView());
        mViewHelper.setDialog(this);
        setCancelable(builder.mCancelable);
        setCanceledOnTouchOutside(builder.mCancelableOutside);
        if (null != builder.mOnCancelListener) {
            setOnCancelListener(builder.mOnCancelListener);
        }
        if (null != builder.mOnDismissListener) {
            setOnDismissListener(builder.mOnDismissListener);
        }
        if (null != builder.mOnKeyListener) {
            setOnKeyListener(builder.mOnKeyListener);
        }
    }

    public ViewHelper getViewHelper() {
        return mViewHelper;
    }

    public String getText(@IdRes int id) {
        return mViewHelper.getText(id);
    }

    public <T extends View> T getView(int viewId) {
        return mViewHelper.getView(viewId);
    }

    public void setText(@IdRes int id, CharSequence charSequence) {
        mViewHelper.setText(id, charSequence);
    }

    public void setText(@IdRes int id, int text) {
        mViewHelper.setText(id, text);
    }

    public void setImageResource(@IdRes int viewId, int resource) {
        mViewHelper.setImageResource(viewId, resource);
    }

    public void setImageDrawable(@IdRes int viewId, Drawable drawable) {
        mViewHelper.setImageDrawable(viewId, drawable);
    }

    public void setImageBitmap(@IdRes int viewId, Bitmap bitmap) {
        mViewHelper.setImageBitmap(viewId, bitmap);
    }

    public void setImageUrl(@IdRes int viewId, CommonImageLoader commonImageLoader) {
        mViewHelper.setImageUrl(viewId, commonImageLoader);
    }

    public void setChecked(@IdRes int viewId, boolean isChecked) {
        mViewHelper.setChecked(viewId, isChecked);
    }

    public void setVisible(@IdRes int id, boolean isVisible) {
        mViewHelper.setVisible(id, isVisible);
    }

    public void setVisibleOrGone(@IdRes int id, boolean isVisible) {
        mViewHelper.setVisibleOrGone(id, isVisible);
    }

    public void setOnClickListener(@IdRes int id, OnCustomClickListener listener) {
        mViewHelper.setOnClickListener(id, listener);
    }

    public void setOnCheckedChangeListener(@IdRes int id, OnCustomCheckBoxListener listener) {
        mViewHelper.setOnCheckedChangeListener(id, listener);
    }

    public void setOnCheckedChangeListener(@IdRes int id, OnCustomRadioGroupListener listener) {
        mViewHelper.setOnCheckedChangeListener(id, listener);
    }

    public void addTextChangedListener(@IdRes int id, OnCustomTextChangeListener listener) {
        mViewHelper.addTextChangedListener(id, listener);
    }

    public int getPriority() {
        return mPriority;
    }

    public void setPriority(int priority) {
        this.mPriority = priority;
    }

    public String getHostName() {
        return mHostName;
    }

    public void setHostName(String hostName) {
        this.mHostName = hostName;
    }

    public static Builder newBuilder(Context context) {
        return new Builder(context);
    }

    public static Builder newBuilder(Context context, int themeResId) {
        return new Builder(context, themeResId);
    }

    public static class Builder extends BaseBuilder {

        public Builder(Context context) {
            this(context, R.style.MyDialog);
        }

        public Builder(Context context, int themeResId) {
            super(context, themeResId);
        }

        public Builder setContentView(View view) {
            mContentView = view;
            return this;
        }

        public Builder setContentView(@LayoutRes int layoutResId) {
            if (layoutResId != 0) {
                setContentView(LayoutInflater.from(mContext).inflate(layoutResId, null));
            }
            return this;
        }
    }

    public static MDBuilder newMDBuilder(Context context) {
        return new MDBuilder(context);
    }

    public static MDBuilder newMDBuilder(Context context, int themeResId) {
        return new MDBuilder(context, themeResId);
    }

    public static class MDBuilder extends BaseBuilder {

        public MDBuilder(Context context) {
            this(context, R.style.MyDialog);
        }

        public MDBuilder(Context context, int themeResId) {
            super(context, themeResId);
            mContentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_material, null);
        }

        public MDBuilder setPriority(int priority) {
            mPriority = priority;
            return this;
        }

        public MDBuilder setTitle(CharSequence charSequence) {
            mTextArray.put(R.id.tv_title, charSequence);
            return this;
        }

        public MDBuilder setMessage(CharSequence charSequence) {
            mTextArray.put(R.id.tv_message, charSequence);
            return this;
        }

        public MDBuilder setNegativeButton(CharSequence charSequence, OnCustomClickListener listener) {
            if (TextUtils.isEmpty(charSequence)) setVisibleOrGone(R.id.tv_cancel, false);
            mTextArray.put(R.id.tv_cancel, charSequence);
            mClickListenerArray.put(R.id.tv_cancel, listener);
            return this;
        }

        public MDBuilder setPositiveButton(CharSequence charSequence, OnCustomClickListener listener) {
            if (TextUtils.isEmpty(charSequence)) setVisibleOrGone(R.id.tv_confirm, false);
            mTextArray.put(R.id.tv_confirm, charSequence);
            mClickListenerArray.put(R.id.tv_confirm, listener);
            return this;
        }

        public MDBuilder setNegativeButtonColor(int color) {
            mTextColorArray.put(R.id.tv_cancel, color);
            return this;
        }

        public MDBuilder setPositiveButtonColor(int color) {
            mTextColorArray.put(R.id.tv_confirm, color);
            return this;
        }
    }

    public static PhotoBuilder newPhotoBuilder(Context context) {
        return new PhotoBuilder(context);
    }

    public static PhotoBuilder newPhotoBuilder(Context context, int themeResId) {
        return new PhotoBuilder(context, themeResId);
    }

    public static class PhotoBuilder extends BaseBuilder {

        public PhotoBuilder(Context context) {
            this(context, R.style.MyDialog);
        }

        public PhotoBuilder(Context context, int themeResId) {
            super(context, themeResId);
            mContentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_camera_photo, null);
            setWidth(1f);
            setGravity(Gravity.BOTTOM);
            setBottomIn();
            setOnClickListener(R.id.tv_cancel, new OnCustomClickListener() {
                @Override
                public void onCustomClick(View view, View contentView, Dialog dialog) {
                    dialog.dismiss();
                }
            });
        }

        public PhotoBuilder setPriority(int priority) {
            mPriority = priority;
            return this;
        }

        public PhotoBuilder setCameraButtonColor(int color) {
            mTextColorArray.put(R.id.tv_camera, color);
            return this;
        }

        public PhotoBuilder setPhotoButtonColor(int color) {
            mTextColorArray.put(R.id.tv_photo, color);
            return this;
        }

        public PhotoBuilder setCancelButtonColor(int color) {
            mTextColorArray.put(R.id.tv_cancel, color);
            return this;
        }

        public PhotoBuilder setCameraButtonListener(OnCustomClickListener listener) {
            mClickListenerArray.put(R.id.tv_camera, listener);
            return this;
        }

        public PhotoBuilder setPhotoButtonListener(OnCustomClickListener listener) {
            mClickListenerArray.put(R.id.tv_photo, listener);
            return this;
        }

    }

}
