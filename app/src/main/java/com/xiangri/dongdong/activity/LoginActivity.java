package com.xiangri.dongdong.activity;

import com.xiangri.dongdong.mvp.persenter.BaseActivity;
import com.xiangri.dongdong.perstener.LoginActivityPerstener;

public  class LoginActivity extends BaseActivity<LoginActivityPerstener>{
    @Override
    public Class<LoginActivityPerstener> getDelegateClass() {
        return LoginActivityPerstener.class;
    }
}
