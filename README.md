# CustomDialog
快捷，简单，高度自定义的对话框库

[![](https://jitpack.io/v/qzc0537/CustomDialog.svg)](https://jitpack.io/#qzc0537/CustomDialog)

![新垣结衣](https://gss2.bdstatic.com/9fo3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike272%2C5%2C5%2C272%2C90/sign=e31d7a55dba20cf4529df68d17602053/91ef76c6a7efce1b27893518a451f3deb58f6546.jpg)


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
implementation 'com.github.qzc0537:CustomDialog:1.0.6'
```

3.愉快的使用：
```
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
