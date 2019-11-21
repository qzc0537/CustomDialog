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
implementation 'com.github.qzc0537:CustomDialog:1.2.0'

Before AndroidX:
implementation 'com.github.qzc0537:CustomDialog:1.1.8'
```

3.愉快的使用：
```
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
