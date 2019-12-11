package com.qzc.sample;

import android.app.Dialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.qzc.customdialog.dialog.CustomDialog;
import com.qzc.customdialog.dialog.DialogManager;
import com.qzc.customdialog.interfaces.OnCustomClickListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_custom_dialog).setOnClickListener(this);
        findViewById(R.id.btn_material_dialog).setOnClickListener(this);
        findViewById(R.id.btn_photo_dialog).setOnClickListener(this);
        findViewById(R.id.btn_show_priority).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_custom_dialog:
                CustomDialog.newBuilder(MainActivity.this)
                        .setContentView(R.layout.dialog_custom)
                        .setWidthHeight(0.9f, CustomDialog.WRAP)
                        .setGravity(Gravity.CENTER)//默认CENTER
                        .setDimAmount(0.6f)//默认0.5f
                        .setBottomIn()
                        .setPriority(0)
                        .setCancelStrategy(true, true)//默认true,true
                        .setText(R.id.btn_confirm, "领取")
                        .setTextColor(R.id.btn_confirm, getColor())
                        .setOnClickListener(R.id.btn_cancel, new OnCustomClickListener() {
                            @Override
                            public void onCustomClick(View view, View contentView, Dialog dialog) {
                                dialog.dismiss();
                            }
                        })
                        .setOnClickListener(R.id.btn_confirm, new OnCustomClickListener() {
                            @Override
                            public void onCustomClick(View view, View contentView, Dialog dialog) {
                                dialog.dismiss();
                                EditText editText = contentView.findViewById(R.id.edt_input);
                                toast(editText.getText().toString());
                            }
                        })
                        .show();
                break;
            case R.id.btn_material_dialog:
                CustomDialog.newMDBuilder(MainActivity.this)
                        .setTitle("温馨提示")
                        .setMessage("确定使用这个神奇的库吗？")
                        .setPriority(1)
                        .setPositiveButtonColor(getColor())
                        .setNegativeButton("再想想", new OnCustomClickListener() {
                            @Override
                            public void onCustomClick(View view, View contentView, Dialog dialog) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("是的", new OnCustomClickListener() {
                            @Override
                            public void onCustomClick(View view, View contentView, Dialog dialog) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.btn_photo_dialog:
                CustomDialog.newPhotoBuilder(MainActivity.this)
                        .setPriority(2)
                        .setCameraButtonListener(new OnCustomClickListener() {
                            @Override
                            public void onCustomClick(View view, View contentView, Dialog dialog) {
                                dialog.dismiss();
                                toast("拍照");
                            }
                        })
                        .setPhotoButtonListener(new OnCustomClickListener() {
                            @Override
                            public void onCustomClick(View view, View contentView, Dialog dialog) {
                                dialog.dismiss();
                                toast("相册");
                            }
                        })
                        .show();
                break;
            case R.id.btn_show_priority:
                DialogManager.getInstance().showPriorityDialog();
                break;
        }
    }

    private void toast(CharSequence text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private int getColor() {
        return ContextCompat.getColor(this, R.color.colorAccent);
    }
}
