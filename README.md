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
implementation 'com.github.qzc0537:CustomDialog:1.0.9'
```

3.愉快的使用：
```
                int anim = new Random().nextInt(1013 - 1001 + 1) + 1001;
                CustomDialog.with(MainActivity.this)
                        .setLayoutId(R.layout.dialog_custom)
                        .setWidthHeight(0.8f, CustomDialog.WRAP_CONTENT)//默认80%,WRAP_CONTENT
                        .setGravity(Gravity.CENTER)//默认CENTER
                        .setAnimation(anim)
                        .setBackgroundDimEnabled(true)//默认true
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
                        .setCustomClick(R.id.btn_confirm, new CustomDialog.CustomClickListener() {
                            @Override
                            public void onCustomClick(View view, DialogInterface dialog) {
                                dialog.dismiss();
                                toast("确定");
                            }
                        })
                        .setCustomClicks(new CustomDialog.CustomClicksListener() {
                            @Override
                            public void onCustomClicks(View view, DialogInterface dialog) {
                                switch (view.getId()) {
                                    case R.id.iv_beauty:
                                        dialog.dismiss();
                                        toast("新垣结衣~");
                                        break;

                                }
                            }
                        });
