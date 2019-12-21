# CustomDialog
快捷，简单，高度自定义的对话框库

[![](https://jitpack.io/v/qzc0537/CustomDialog.svg)](https://jitpack.io/#qzc0537/CustomDialog)

![新垣结衣](https://github.com/qzc0537/CustomDialog/blob/master/Screenshot_2019-01-02-15-43-27.png)


使用
--
1.project build.gradle下添加：
maven { url 'https://jitpack.io' }

如下：

```
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```

2.app build.gradle下添加依赖 ：

```
AndroidX:
implementation 'com.github.qzc0537:CustomDialog:1.2.4'

Before AndroidX:
implementation 'com.github.qzc0537:CustomDialog:1.1.8'
```

3.愉快的使用：
```
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
                CustomDialog.with(MainActivity.this)
                        .setContentView(R.layout.dialog_custom)
                        .setWidthHeight(0.9f, CustomDialog.WRAP)
                        .setGravity(Gravity.CENTER)//默认CENTER
                        .setDimAmount(0.6f)//默认0.5f
                        .setBottomIn()
                        .setPriority(11)
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
                CustomDialog.withMD(MainActivity.this)
                        .setTitle("温馨提示")
                        .setMessage("确定使用这个神奇的库吗？")
                        .setPriority(6)
                        .setNegativeButton("再想想", new OnCustomClickListener() {
                            @Override
                            public void onCustomClick(View view, View contentView, Dialog dialog) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButtonColor(getColor())
                        .setPositiveButton("是的", new OnCustomClickListener() {
                            @Override
                            public void onCustomClick(View view, View contentView, Dialog dialog) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.btn_photo_dialog:
                CustomDialog.withPhoto(MainActivity.this)
                        .setPriority(9)
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
