package com.xiangri.dongdong.activity;

import com.xiangri.dongdong.mvp.persenter.BaseActivity;
import com.xiangri.dongdong.perstener.MyMessageActivityPerstener;

public class MyMessageActivity extends BaseActivity<MyMessageActivityPerstener> {

    @Override
    public Class<MyMessageActivityPerstener> getDelegateClass() {
        return MyMessageActivityPerstener.class;
    }
}