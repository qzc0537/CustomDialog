package com.qzc.sample;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.qzc.customdialog.CustomDialog;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv_hello).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                int anim = new Random().nextInt(1013 - 1001 + 1) + 1001;
                CustomDialog.with(MainActivity.this)
                        .setContentView(R.layout.dialog_custom)
                        .setWidthHeight(0.8f, CustomDialog.WRAP)//默认80%,WRAP_CONTENT
                        .setGravity(Gravity.CENTER)//默认CENTER
                        .setAnimation(CustomDialog.SCALE_IN)
                        .setCancelStrategy(true, true)//默认true,false
                        .create()
                        .setText(R.id.btn_confirm, "领取")
                        .setCustomClick(R.id.btn_cancel, new CustomDialog.CustomClickListener() {
                            @Override
                            public void onCustomClick(View view, DialogInterface dialog) {
                                dialog.dismiss();
                                toast("取消");
                            }
                        })
                        .setCustomClicks(new CustomDialog.CustomClicksListener() {
                            @Override
                            public void onCustomClicks(View view, View rootView, DialogInterface dialog) {
                                switch (view.getId()) {
                                    case R.id.iv_beauty:
                                        dialog.dismiss();
                                        toast("新垣结衣~");
                                        break;
                                    case R.id.btn_confirm:
                                        dialog.dismiss();
                                        EditText editText = rootView.findViewById(R.id.edt_input);
                                        toast(editText.getText().toString());
                                        break;
                                }
                            }
                        });
            }
        });
    }

    private void toast(CharSequence text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
