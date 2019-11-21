package com.qzc.sample;

import android.content.DialogInterface;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
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
//                int anim = new Random().nextInt(1013 - 1001 + 1) + 1001;
                CustomDialog.with(MainActivity.this)
                        .setLayoutId(R.layout.dialog_custom)
                        .setWidthHeight(0.9f, CustomDialog.WRAP)//默认80%,WRAP_CONTENT
                        .setGravity(Gravity.CENTER)//默认CENTER
                        .setDimAmount(0.6f)//默认0.5f
                        .setAnimation(CustomDialog.BOTTOM_IN)
                        .setCancelStrategy(true, true)//默认true,true
                        .build()
                        .setText(R.id.btn_confirm, "领取")
                        .setCustomClicks(new CustomDialog.CustomClicksListener() {
                            @Override
                            public void onCustomClicks(View view, View rootView, DialogInterface dialog) {
                                switch (view.getId()) {
                                    case R.id.iv_beauty:
                                        dialog.dismiss();
                                        toast("新垣结衣~");
                                        break;
                                    case R.id.btn_cancel:
                                        dialog.dismiss();
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
