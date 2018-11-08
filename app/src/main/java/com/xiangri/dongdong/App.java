package com.xiangri.dongdong;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.xiangri.dongdong.net.Http;
import com.xiangri.dongdong.net.RetrofitHelper;

public    class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        Fresco.initialize(this);
        RetrofitHelper.getInstens().init(Http.BASE_URL+"/");
    }
}