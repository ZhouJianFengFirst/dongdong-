package com.xiangri.dongdong.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xiangri.dongdong.R;
import com.xiangri.dongdong.mvp.persenter.BaseActivity;
import com.xiangri.dongdong.perstener.SearchActivityPersenter;

public class SearchActivity extends BaseActivity<SearchActivityPersenter> {

    @Override
    public Class<SearchActivityPersenter> getDelegateClass() {
        return SearchActivityPersenter.class;
    }
}