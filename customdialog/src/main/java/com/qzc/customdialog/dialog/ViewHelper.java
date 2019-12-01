package com.qzc.customdialog.dialog;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.IdRes;

import com.qzc.customdialog.image.CommonImageLoader;
import com.qzc.customdialog.interfaces.OnCustomCheckBoxListener;
import com.qzc.customdialog.interfaces.OnCustomClickListener;
import com.qzc.customdialog.interfaces.OnCustomRadioGroupListener;
import com.qzc.customdialog.interfaces.OnCustomTextChangeListener;
import com.qzc.customdialog.utils.StringUtils;

import java.lang.ref.WeakReference;

/**
 * created by qinzhichang at 2019/12/01 11:08
 * desc:    View辅助类
 */
public class ViewHelper {

    private CustomDialog mDialog;
    private View mContentView;
    private SparseArray<WeakReference<View>> mViews;

    public ViewHelper(View view) {
        this.mContentView = view;
        mViews = new SparseArray<>();
    }

    public View getContentView() {
        return mContentView;
    }

    public void setDialog(CustomDialog dialog) {
        this.mDialog = dialog;
    }

    public <T extends View> T getView(@IdRes int idRes) {
        WeakReference<View> viewWeakReference = mViews.get(idRes);
        View view = null;
        if (null != viewWeakReference) {
            view = viewWeakReference.get();
        }
        if (null == view) {
            view = mContentView.findViewById(idRes);
            if (null != view) {
                mViews.put(idRes, new WeakReference<>(view));
            }
        }
        return (T) view;
    }

    public String getText(@IdRes int viewId) {
        View view = getView(viewId);
        if (view instanceof TextView) {
            return ((TextView) view).getText().toString();
        } else {
            return "";
        }
    }

    public void setText(@IdRes int viewId, CharSequence charSequence) {
        View view = getView(viewId);
        if (view instanceof TextView) {
            ((TextView) view).setText(StringUtils.valueOf(charSequence));
        }
    }

    public void setText(@IdRes int viewId, int text) {
        View view = getView(viewId);
        if (view instanceof TextView) {
            ((TextView) view).setText(text);
        }
    }

    public void setTextColor(@IdRes int viewId, int color) {
        View view = getView(viewId);
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(color);
        }
    }

    public void setTextColor(@IdRes int viewId, ColorStateList color) {
        View view = getView(viewId);
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(color);
        }
    }

    public void setImageBitmap(@IdRes int viewId, Bitmap bitmap) {
        View view = getView(viewId);
        if (null != view && view instanceof ImageView) {
            ((ImageView) (view)).setImageBitmap(bitmap);
        }
    }

    public void setImageResource(@IdRes int viewId, int resource) {
        View view = getView(viewId);
        if (null != view && view instanceof ImageView) {
            ((ImageView) (view)).setImageResource(resource);
        }
    }

    public void setImageDrawable(@IdRes int viewId, Drawable drawable) {
        View view = getView(viewId);
        if (null != view && view instanceof ImageView) {
            ((ImageView) (view)).setImageDrawable(drawable);
        }
    }

    public void setImageUrl(@IdRes int viewId, CommonImageLoader commonImageLoader) {
        View view = getView(viewId);
        if (null != view && view instanceof ImageView) {
            commonImageLoader.loadImage(commonImageLoader.getUrl(), ((ImageView) (view)));
        }
    }

    public void setChecked(@IdRes int viewId, boolean isChecked) {
        View view = getView(viewId);
        if (null != view && view instanceof CompoundButton) {
            ((CompoundButton) (view)).setChecked(isChecked);
        }
    }

    public void setVisible(@IdRes int id, boolean isVisible) {
        View view = getView(id);
        if (null != view) {
            view.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
        }
    }

    public void setVisibleOrGone(@IdRes int id, boolean isVisible) {
        View view = getView(id);
        if (null != view) {
            view.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        }
    }

    public void setOnClickListener(@IdRes int id, final OnCustomClickListener listener) {
        View view = getView(id);
        if (null != view && null != listener) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onCustomClick(v, mContentView, mDialog);
                }
            });
        }
    }

    public void setOnCheckedChangeListener(@IdRes int id, final OnCustomCheckBoxListener listener) {
        View view = getView(id);
        if (null != view && view instanceof RadioGroup && null != listener) {
            ((CheckBox) view).setOnCheckedChangeListener(listener);
        }
    }

    public void setOnCheckedChangeListener(@IdRes int id, final OnCustomRadioGroupListener listener) {
        View view = getView(id);
        if (null != view && view instanceof RadioGroup && null != listener) {
            ((RadioGroup) view).setOnCheckedChangeListener(listener);
        }
    }

    public void addTextChangedListener(@IdRes int id, OnCustomTextChangeListener listener) {
        View view = getView(id);
        if (null != view && view instanceof EditText && null != listener) {
            ((EditText) view).addTextChangedListener(listener);
        }
    }
}
