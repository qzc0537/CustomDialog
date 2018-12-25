package com.qzc.sample;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.qzc.customdialog.CustomDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv_hello).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                CustomDialog.with(MainActivity.this)
                        .setLayoutId(R.layout.dialog_custom)
                        .setWidthHeight(0.8f, CustomDialog.WRAP_CONTENT)//默认80%
                        .setGravity(Gravity.CENTER)//默认CENTER
                        .setAnimation(CustomDialog.SCALE_LEFT_IN)
                        .setBackgroundDimEnabled(true)//默认true
                        .setCancelStrategy(true, true)//默认true,false
                        .create()
                        .setText(R.id.tv_text, "立即领取")
                        .setImageResource(R.id.iv_bg, R.drawable.ic_head)
                        .setCustomClick(R.id.tv_text, new CustomDialog.CustomClickListener() {
                            @Override
                            public void onCustomClick(View view, DialogInterface dialog) {
                                toast("立即领取");
                            }
                        })
                        .setCustomClicks(new CustomDialog.CustomClicksListener() {
                            @Override
                            public void onCustomClicks(View view, DialogInterface dialog) {
                                if (view.getId() == R.id.iv_bg) {
                                    toast("id: " + view.getId());
                                } else if (view.getId() == R.id.iv_close) {
                                    dialog.dismiss();
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
