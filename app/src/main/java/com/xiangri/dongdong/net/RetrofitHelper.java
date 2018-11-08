package com.xiangri.dongdong.net;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    private static RetrofitHelper retrofitHelper;
    private Retrofit retrofit;

    private RetrofitHelper() {

    }

    public static RetrofitHelper getInstens() {
        if (retrofitHelper == null) {
            synchronized (RetrofitHelper.class) {
                retrofitHelper = new RetrofitHelper();
            }
        }
        return retrofitHelper;
    }

    public void init(String baseUrl) {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public void doGet(String url, Map<String, String> map, Observer ob) {
        if (map == null) {
            map = new HashMap<>();
        }
        BaseService baseService = retrofit.create(BaseService.class);
        baseService.get(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ob);
    }
    public void doPost(String url, Map<String, String> map, Observer ob) {
        if (map == null) {
            map = new HashMap<>();
        }
        BaseService baseService = retrofit.create(BaseService.class);
        baseService.post(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ob);
    }
}
