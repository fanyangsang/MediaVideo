package com.zut.lpb;

import android.app.Application;
import android.view.Gravity;

import com.blankj.utilcode.util.ToastUtils;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //设置toast居中显示
        ToastUtils.setGravity(Gravity.CENTER,0,0);
    }
}
