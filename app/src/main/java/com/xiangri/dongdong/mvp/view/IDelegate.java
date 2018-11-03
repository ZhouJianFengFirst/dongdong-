package com.xiangri.dongdong.mvp.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import okhttp3.RequestBody;

public interface IDelegate {

    void initData();

    void creat(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle);

    View getRootView();

    void initContext(Context context);

    void getString(String url, int type);

    void postString(String url, int type, RequestBody body);

}
