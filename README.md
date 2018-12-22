# CustomDialog
快捷，简单，高度自定义的对话框库

[![](https://jitpack.io/v/qzc0537/CustomDialog.svg)](https://jitpack.io/#qzc0537/CustomDialog)

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
implementation 'com.github.qzc0537:CustomDialog:1.0.3'
```

3.愉快的使用：
```
CustomDialog.with(MainActivity.this)
                        .setLayoutId(R.layout.dialog_custom)
                        .setStyle(R.style.MyCommonDialogStyle)//默认MyCommonDialogStyle
                        .setAnimation(CustomDialog.BOTTOM_IN)
                        .setGravity(Gravity.CENTER)//默认CENTER
                        .setCancelable(true)//默认true
                        .setCancelOnTouchOutside(false)//默认false
                        .setWidth(0.8f)//默认80%
                        .setHeight(CustomDialog.WRAP_CONTENT)
                        .create()
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
                        });
