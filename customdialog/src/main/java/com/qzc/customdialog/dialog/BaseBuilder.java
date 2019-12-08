package com.qzc.customdialog.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.IdRes;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.qzc.customdialog.R;
import com.qzc.customdialog.image.CommonImageLoader;
import com.qzc.customdialog.interfaces.OnCustomCheckBoxListener;
import com.qzc.customdialog.interfaces.OnCustomClickListener;
import com.qzc.customdialog.interfaces.OnCustomRadioGroupListener;
import com.qzc.customdialog.interfaces.OnCustomTextChangeListener;
import com.qzc.customdialog.lifecycle.LifeFragment;

/**
 * created by qinzhichang at 2019/12/01 11:02
 * desc:
 */
public class BaseBuilder {

    public Context mContext;
    public View mContentView;
    private ViewHelper mViewHelper;
    private CustomDialog mDialog;
    private int mThemeResId;  //主题
    public boolean mCancelable = true;  //点击返回键是否关闭
    public boolean mCancelableOutside = true;  //点击外部是否关闭
    private int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
    private int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
    private float mWidthRatio = 0f;  //dialog宽度占屏幕宽度的比例
    private float mHeightRatio = 0f;  //dialog高度占屏幕高度的比例
    private float mDimAmount = 0.5f;  //dialog背景亮度
    private int mGravity = Gravity.CENTER;  //dialog位置
    private int mAnimation; //dialog动画
    private int mOffsetX;      //dialog在X轴的偏移量（Gravity需要设置Left）
    private int mOffsetY;      //dialog在Y轴的偏移量（Gravity需要设置Top）
    private int mPriority = -1;

    SparseArray<CharSequence> mTextArray = new SparseArray<>();
    SparseArray<Integer> mTextIntArray = new SparseArray<>();
    SparseArray<Integer> mTextColorArray = new SparseArray();
    SparseArray<ColorStateList> mTextColorStateListArray = new SparseArray<>();
    SparseArray<Integer> mImageResourceArray = new SparseArray<>();
    SparseArray<Drawable> mImageDrawableArray = new SparseArray<>();
    SparseArray<Bitmap> mImageBitmapArray = new SparseArray<>();
    SparseArray<CommonImageLoader> mImageLoaderArray = new SparseArray<>();
    SparseArray<Boolean> mCheckedArray = new SparseArray<>();
    SparseArray<Boolean> mVisibleArray = new SparseArray<>();
    SparseArray<Boolean> mVisibleGoneArray = new SparseArray<>();

    SparseArray<OnCustomClickListener> mClickListenerArray = new SparseArray<>();
    SparseArray<OnCustomCheckBoxListener> mCheckBoxListenerArray = new SparseArray<>();
    SparseArray<OnCustomRadioGroupListener> mRadioGroupListenerArray = new SparseArray<>();
    SparseArray<OnCustomTextChangeListener> mTextChangeListenerArray = new SparseArray<>();

    DialogInterface.OnDismissListener mOnDismissListener;
    DialogInterface.OnCancelListener mOnCancelListener;
    DialogInterface.OnKeyListener mOnKeyListener;


    BaseBuilder(Context context, int themeResId) {
        this.mContext = context;
        this.mThemeResId = themeResId;
    }

    public CustomDialog build() {
        if (mDialog == null) {
            mDialog = new CustomDialog(mContext, mThemeResId);
            mDialog.init(this);
            mViewHelper = mDialog.getViewHelper();
            attachView();
        }
        if (mPriority > -1) {
            DialogManager.getInstance().add(mPriority, mDialog);
        }
        if (mContext instanceof FragmentActivity) {
            FragmentActivity activity = (FragmentActivity) mContext;
            LifeFragment fragment = LifeFragment.getInstance();
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
            transaction.add(fragment, "Lifecycle").commitAllowingStateLoss();
        }
        return mDialog;
    }

    public CustomDialog show() {
        if (mDialog == null) {
            build();
        }
        initWindow(mDialog);
        mDialog.show();
        return mDialog;
    }

    private void initWindow(Dialog dialog) {
        Window window = dialog.getWindow();
        if (null != window) {
            window.setGravity(mGravity);
            if (mAnimation != 0) {
                window.setWindowAnimations(mAnimation);
            }

            WindowManager.LayoutParams lp = window.getAttributes();
            if (mWidthRatio != 0) {
                lp.width = (int) (mContext.getResources().getDisplayMetrics().widthPixels * mWidthRatio);
            } else {
                lp.width = mWidth;
            }
            if (mHeightRatio != 0) {
                lp.height = (int) (mContext.getResources().getDisplayMetrics().heightPixels * mHeightRatio);
            } else {
                lp.height = mHeight;
            }
            if (mOffsetX > 0) {
                lp.x = mOffsetX;
            }
            if (mOffsetY > 0) {
                lp.y = mOffsetY;
            }
            lp.dimAmount = mDimAmount;
            window.setAttributes(lp);
        }
    }

    private boolean attachView() {
        /**
         * 文字，颜色
         */
        for (int i = 0; i < mTextArray.size(); i++) {
            mViewHelper.setText(mTextArray.keyAt(i), mTextArray.valueAt(i));
        }
        for (int i = 0; i < mTextIntArray.size(); i++) {
            mViewHelper.setText(mTextIntArray.keyAt(i), mTextIntArray.valueAt(i));
        }
        for (int i = 0; i < mTextColorArray.size(); i++) {
            mViewHelper.setTextColor(mTextColorArray.keyAt(i), mTextColorArray.valueAt(i));
        }
        for (int i = 0; i < mTextColorStateListArray.size(); i++) {
            mViewHelper.setTextColor(mTextColorStateListArray.keyAt(i),
                    mTextColorStateListArray.valueAt(i));
        }

        /**
         * 图片
         */
        for (int i = 0; i < mImageResourceArray.size(); i++) {
            mViewHelper.setImageResource(mImageResourceArray.keyAt(i),
                    mImageResourceArray.valueAt(i));
        }
        for (int i = 0; i < mImageDrawableArray.size(); i++) {
            mViewHelper.setImageDrawable(mImageDrawableArray.keyAt(i),
                    mImageDrawableArray.valueAt(i));
        }
        for (int i = 0; i < mImageBitmapArray.size(); i++) {
            mViewHelper.setImageBitmap(mImageBitmapArray.keyAt(i), mImageBitmapArray.valueAt(i));
        }
        for (int i = 0; i < mImageLoaderArray.size(); i++) {
            mViewHelper.setImageUrl(mImageLoaderArray.keyAt(i), mImageLoaderArray.valueAt(i));
        }

        /**
         * 单选框，复选框
         */
        for (int i = 0; i < mCheckedArray.size(); i++) {
            mViewHelper.setChecked(mCheckedArray.keyAt(i), mCheckedArray.valueAt(i));
        }

        /**
         * 可见性
         */
        for (int i = 0; i < mVisibleArray.size(); i++) {
            mViewHelper.setVisible(mVisibleArray.keyAt(i), mVisibleArray.valueAt(i));
        }
        for (int i = 0; i < mVisibleGoneArray.size(); i++) {
            mViewHelper.setVisibleOrGone(mVisibleGoneArray.keyAt(i), mVisibleGoneArray.valueAt(i));
        }

        /**
         * 事件
         */
        for (int i = 0; i < mClickListenerArray.size(); i++) {
            mViewHelper.setOnClickListener(mClickListenerArray.keyAt(i),
                    mClickListenerArray.valueAt(i));
        }
        for (int i = 0; i < mCheckBoxListenerArray.size(); i++) {
            mViewHelper.setOnCheckedChangeListener(mCheckBoxListenerArray.keyAt(i),
                    mCheckBoxListenerArray.valueAt(i));
        }
        for (int i = 0; i < mRadioGroupListenerArray.size(); i++) {
            mViewHelper.setOnCheckedChangeListener(mRadioGroupListenerArray.keyAt(i),
                    mRadioGroupListenerArray.valueAt(i));
        }
        for (int i = 0; i < mRadioGroupListenerArray.size(); i++) {
            mViewHelper.addTextChangedListener(mTextChangeListenerArray.keyAt(i),
                    mTextChangeListenerArray.valueAt(i));
        }
        return true;
    }

    public BaseBuilder setCancelStrategy(boolean isCancelable, boolean isCancelableOutside) {
        this.mCancelable = isCancelable;
        this.mCancelableOutside = isCancelableOutside;
        return this;
    }

    public BaseBuilder setCancelable(boolean isCancelable) {
        this.mCancelable = isCancelable;
        return this;
    }

    public BaseBuilder setCancelableOutside(boolean isCancelableOutside) {
        this.mCancelableOutside = isCancelableOutside;
        return this;
    }

    public BaseBuilder setWidthHeight(int width, int height) {
        this.mWidth = width;
        this.mHeight = height;
        return this;
    }

    public BaseBuilder setWidthHeight(float widthRatio, float heightRatio) {
        this.mWidthRatio = widthRatio;
        this.mHeightRatio = heightRatio;
        return this;
    }

    public BaseBuilder setWidthHeight(int width, float heightRatio) {
        this.mWidth = width;
        this.mHeightRatio = heightRatio;
        return this;
    }

    public BaseBuilder setWidthHeight(float widthRatio, int height) {
        this.mWidthRatio = widthRatio;
        this.mHeight = height;
        return this;
    }

    public BaseBuilder setWidth(int width) {
        this.mWidth = width;
        return this;
    }

    public BaseBuilder setHeight(int height) {
        this.mHeight = height;
        return this;
    }

    public BaseBuilder setWidth(float widthRatio) {
        this.mWidthRatio = widthRatio;
        return this;
    }

    public BaseBuilder setHeight(float heightRatio) {
        this.mHeightRatio = heightRatio;
        return this;
    }

    public BaseBuilder setDimAmount(float dimAmount) {
        this.mDimAmount = dimAmount;
        return this;
    }

    public BaseBuilder setGravity(int gravity) {
        this.mGravity = gravity;
        return this;
    }

    public BaseBuilder setAnimation(int animation) {
        this.mAnimation = animation;
        return this;
    }

    public BaseBuilder setOffsetX(int offsetX) {
        this.mOffsetX = offsetX;
        return this;
    }

    public BaseBuilder setOffsetY(int offsetY) {
        this.mOffsetY = offsetY;
        return this;
    }

    public BaseBuilder setPriority(int mPriority) {
        this.mPriority = mPriority;
        return this;
    }

    public BaseBuilder setTopIn() {
        this.mAnimation = R.style.MyTopInDialogAnim;
        return this;
    }

    public BaseBuilder setBottomIn() {
        this.mAnimation = R.style.MyBottomInDialogAnim;
        return this;
    }

    public BaseBuilder setLeftIn() {
        this.mAnimation = R.style.MyLeftInDialogAnim;
        return this;
    }

    public BaseBuilder setRightIn() {
        this.mAnimation = R.style.MyRightInDialogAnim;
        return this;
    }

    public BaseBuilder setAlphaIn() {
        this.mAnimation = R.style.MyAlphaInDialogAnim;
        return this;
    }

    public BaseBuilder setRotateIn() {
        this.mAnimation = R.style.MyRotateInDialogAnim;
        return this;
    }

    public BaseBuilder setScaleIn() {
        this.mAnimation = R.style.MyScaleInDialogAnim;
        return this;
    }

    public BaseBuilder setScaleTopIn() {
        this.mAnimation = R.style.MyScaleTopInDialogAnim;
        return this;
    }

    public BaseBuilder setScaleBottomIn() {
        this.mAnimation = R.style.MyScaleBottomInDialogAnim;
        return this;
    }

    public BaseBuilder setScaleLeftIn() {
        this.mAnimation = R.style.MyScaleLeftInDialogAnim;
        return this;
    }

    public BaseBuilder setScaleRightIn() {
        this.mAnimation = R.style.MyScaleRightInDialogAnim;
        return this;
    }

    public BaseBuilder setScaleLeftTopIn() {
        this.mAnimation = R.style.MyScaleLeftTopInDialogAnim;
        return this;
    }

    public BaseBuilder setScaleLeftBottomIn() {
        this.mAnimation = R.style.MyScaleLeftBottomInDialogAnim;
        return this;
    }

    public BaseBuilder setScaleRightTopIn() {
        this.mAnimation = R.style.MyScaleRightTopInDialogAnim;
        return this;
    }

    public BaseBuilder setScaleRightBottomIn() {
        this.mAnimation = R.style.MyScaleRightBottomInDialogAnim;
        return this;
    }

    public BaseBuilder setText(@IdRes int viewId, CharSequence charSequence) {
        mTextArray.put(viewId, charSequence);
        return this;
    }

    public BaseBuilder setText(@IdRes int viewId, int text) {
        mTextIntArray.put(viewId, text);
        return this;
    }

    public BaseBuilder setTextColor(@IdRes int viewId, int text) {
        mTextColorArray.put(viewId, text);
        return this;
    }

    public BaseBuilder setTextColorStateList(@IdRes int viewId, ColorStateList stateList) {
        mTextColorStateListArray.put(viewId, stateList);
        return this;
    }

    public BaseBuilder setImageResource(@IdRes int viewId, int resource) {
        mImageResourceArray.put(viewId, resource);
        return this;
    }

    public BaseBuilder setImageDrawable(@IdRes int viewId, Drawable drawable) {
        mImageDrawableArray.put(viewId, drawable);
        return this;
    }

    public BaseBuilder setImageBitmap(@IdRes int viewId, Bitmap bitmap) {
        mImageBitmapArray.put(viewId, bitmap);
        return this;
    }

    public BaseBuilder setImageUrl(@IdRes int viewId, CommonImageLoader commonImageLoader) {
        mImageLoaderArray.put(viewId, commonImageLoader);
        return this;
    }

    public BaseBuilder setChecked(@IdRes int viewId, boolean isChecked) {
        mCheckedArray.put(viewId, isChecked);
        return this;
    }

    public BaseBuilder setVisible(@IdRes int viewId, boolean isVisible) {
        mVisibleArray.put(viewId, isVisible);
        return this;
    }

    public BaseBuilder setVisibleOrGone(@IdRes int viewId, boolean isVisible) {
        mVisibleGoneArray.put(viewId, isVisible);
        return this;
    }

    public BaseBuilder setOnClickListener(@IdRes int viewId, OnCustomClickListener listener) {
        mClickListenerArray.put(viewId, listener);
        return this;
    }

    public BaseBuilder setOnCheckedChangeListener(@IdRes int viewId, OnCustomCheckBoxListener listener) {
        mCheckBoxListenerArray.put(viewId, listener);
        return this;
    }

    public BaseBuilder setOnCheckedChangeListener(@IdRes int viewId, OnCustomRadioGroupListener listener) {
        mRadioGroupListenerArray.put(viewId, listener);
        return this;
    }

    public BaseBuilder addTextChangedListener(@IdRes int viewId, OnCustomTextChangeListener listener) {
        mTextChangeListenerArray.put(viewId, listener);
        return this;
    }

    public BaseBuilder setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
        return this;
    }

    public BaseBuilder setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        this.mOnCancelListener = onCancelListener;
        return this;
    }

    public BaseBuilder setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
        this.mOnKeyListener = onKeyListener;
        return this;
    }

}
