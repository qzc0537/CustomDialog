package com.qzc.customdialog.lifecycle;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.qzc.customdialog.dialog.DialogManager;

/**
 * created by qinzhichang at 2019/12/08 23:01
 * desc:    感知生命周期的Fragment
 */
public class LifeFragment extends Fragment {

    private String mHastName = "";

    public LifeFragment() {
    }

    public static LifeFragment getInstance() {
        return new LifeFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mHastName = getActivity() == null ? "" : getActivity().getLocalClassName();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        DialogManager.getInstance().dismissPriorityDialog(mHastName);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
