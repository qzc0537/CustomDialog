package com.qzc.sample;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
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
                        .setWidthHeight(0.8f, CustomDialog.WRAP_CONTENT)//默认80%,WRAP_CONTENT
                        .setGravity(Gravity.CENTER)//默认CENTER
                        .setAnimation(CustomDialog.ALPHA_IN)
                        .setBackgroundDimEnabled(true)//默认true
                        .setCancelStrategy(true, true)//默认true,false
                        .create()
                        .setText(R.id.btn_confirm, "领取")
                        .setCustomClick(R.id.iv_beauty, new CustomDialog.CustomClickListener() {
                            @Override
                            public void onCustomClick(View view, DialogInterface dialog) {
                                toast("新垣结衣~");
                            }
                        })
                        .setCustomClicks(new CustomDialog.CustomClicksListener() {
                            @Override
                            public void onCustomClicks(View view, DialogInterface dialog) {
                                dialog.dismiss();
                                toast(((TextView) view).getText().toString());
                            }
                        });

            }
        });
    }

    private void toast(CharSequence text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
