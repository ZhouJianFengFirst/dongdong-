package com.xiangri.dongdong.mvp.persenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.xiangri.dongdong.mvp.view.AppDelegate;

public abstract class BaseActivity<T extends AppDelegate> extends AppCompatActivity {

    public T delegate;

    public abstract Class<T> getDelegateClass();

    public BaseActivity() {
        try {
            delegate = getDelegateClass().newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        delegate.initContext(this);
        delegate.creat(getLayoutInflater(), null, savedInstanceState);
        setContentView(delegate.getRootView());
        delegate.initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        delegate.destory();
        delegate = null;
    }
}
