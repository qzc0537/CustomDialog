package com.qzc.customdialog.dialog;

import android.content.DialogInterface;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * created by qinzhichang at 2019/12/08 22:49
 * desc:    对话框管理类
 */
public class DialogManager {

    private static DialogManager mInstance;
    private static SparseArray<CustomDialog> mDialogArray;

    private DialogManager() {
    }

    public static DialogManager getInstance() {
        if (mInstance == null) {
            synchronized (DialogManager.class) {
                if (mInstance == null) {
                    mInstance = new DialogManager();
                    mDialogArray = new SparseArray<>();
                }
            }
        }
        return mInstance;
    }

    /**
     * 添加对话框
     *
     * @param dialog
     */
    public void add(CustomDialog dialog) {
        if (mDialogArray.indexOfKey(dialog.getPriority()) <= -1) {
            mDialogArray.put(dialog.getPriority(), dialog);
        }
    }

    /**
     * 找到优先级最高值
     *
     * @return
     */
    public int findMax() {
        int max = 0;
        for (int i = 0; i < mDialogArray.size(); i++) {
            if (mDialogArray.keyAt(i) > max) {
                max = mDialogArray.keyAt(i);
            }
        }
        return max;
    }

    /**
     * 顺序显示对话框
     */
    public void showPriorityDialog() {
        if (mDialogArray.size() == 0) return;
        int max = findMax();
        CustomDialog dialog = null;
        for (int i = 0; i < mDialogArray.size(); i++) {
            dialog = mDialogArray.valueAt(i);
            if (dialog != null && dialog.getPriority() == max) {
                mDialogArray.remove(max);
                break;
            }
        }
        if (dialog != null) {
            dialog.show();
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    showPriorityDialog();
                }
            });
        }
    }

    /**
     * 关闭和移除所有设置优先级的对话框
     */
    public void dismissPriorityDialog(String hostName) {
        if (mDialogArray.size() == 0) return;
        CustomDialog dialog = null;
        for (int i = 0; i < mDialogArray.size(); i++) {
            dialog = mDialogArray.valueAt(i);
            if (dialog != null && dialog.getHostName().equals(hostName)) {
                mDialogArray.remove(dialog.getPriority());
                if (dialog.isShowing()) dialog.dismiss();
                dialog = null;
            }
        }
    }

}
