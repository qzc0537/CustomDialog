package com.qzc.sample;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.qzc.customdialog.CustomDialog;
import com.qzc.customdialog.DialogFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv_hello).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                DialogFactory.with(MainActivity.this)
                        .setLayoutId(R.layout.dialog_custom)
                        .setStyle(R.style.MyCommonDialogStyle)//默认MyCommonDialogStyle
                        .setAnimation(R.style.MyBottomInDialogAnim)//无
                        .setGravity(Gravity.CENTER)//默认CENTER
                        .setCancelable(true)//默认true
                        .setCancelOnTouchOutside(false)//默认false
                        .setWidth(0.8f)//默认80%
                        .setHeight(DialogFactory.WRAP_CONTENT)//默认WRAP_CONTENT
                        .createCustomDialog()
                        .setText(R.id.tv_text, "立即领取")
                        .setImageResource(R.id.iv_bg, R.drawable.ic_head)
                        .setCustomClick(R.id.tv_text, new CustomDialog.CustomClickListener() {
                            @Override
                            public void onCustomClick(View view, DialogInterface dialog) {
                                toast("立即领取");
                            }
                        })
                        .setCustomClicks(R.id.iv_bg, R.id.iv_close)
                        .setCustomClicks(new CustomDialog.CustomClicksListener() {
                            @Override
                            public void onCustomClicks(View view, DialogInterface dialog) {
                                if (view.getId() == R.id.iv_bg) {
                                    toast("id: " + view.getId());
                                } else if (view.getId() == R.id.iv_close) {
                                    dialog.dismiss();
                                }
                            }
                        })
                        .show();

            }
        });
    }

    private void toast(CharSequence text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
