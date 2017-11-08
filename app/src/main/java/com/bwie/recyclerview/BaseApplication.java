package com.bwie.recyclerview;

import android.app.Application;
//全局初始化Application类
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //配置imageLoader
        ImageLoaderUtil.init(this);
    }
}
