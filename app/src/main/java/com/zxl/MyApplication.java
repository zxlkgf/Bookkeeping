package com.zxl;

import android.app.Application;

import com.zxl.DateBase.DBManager;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DBManager.initDB(getApplicationContext());
    }
}
