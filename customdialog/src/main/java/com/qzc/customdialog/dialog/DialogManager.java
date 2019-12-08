package com.qzc.customdialog.dialog;

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
    private static ArrayList<Integer> mPriorityList;

    private DialogManager() {
    }

    public static DialogManager getInstance() {
        if (mInstance == null) {
            synchronized (DialogManager.class) {
                if (mInstance == null) {
                    mInstance = new DialogManager();
                    mDialogArray = new SparseArray<>();
                    mPriorityList = new ArrayList<>();
                }
            }
        }
        return mInstance;
    }

    /**
     * 添加对话框
     *
     * @param priority
     * @param dialog
     */
    public void add(int priority, CustomDialog dialog) {
        mDialogArray.put(priority, dialog);
        sortPriority();
    }

    /**
     * 对优先级值排序
     */
    public void sortPriority() {
        //从SparseArray中取出所有优先级
        for (int i = 0; i < mDialogArray.size(); i++) {
            mPriorityList.add(mDialogArray.keyAt(i));
        }
        //对优先级排序
        Arrays.sort(mPriorityList.toArray(new Integer[mDialogArray.size()]));
    }

    /**
     * 顺序显示对话框
     */
    public void showPriorityDialog() {
        for (int i = 0; i < mPriorityList.size(); i++) {
            CustomDialog dialog = mDialogArray.valueAt(mPriorityList.get(i));
            dialog.show();
        }
    }

    /**
     * 顺序关闭所有设置优先级的对话框
     */
    public void dismissPriorityDialog() {
        //顺序关闭
        for (int i = 0; i < mPriorityList.size(); i++) {
            CustomDialog dialog = mDialogArray.valueAt(mPriorityList.get(i));
            dialog.dismiss();
            dialog = null;
        }
        //清空引用
        mPriorityList.clear();
        mDialogArray.clear();
    }

}
