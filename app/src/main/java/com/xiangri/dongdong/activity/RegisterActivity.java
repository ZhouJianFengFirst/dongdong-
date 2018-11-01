package com.xiangri.dongdong.activity;

import com.xiangri.dongdong.mvp.persenter.BaseActivity;
import com.xiangri.dongdong.perstener.LoginActivityPerstener;
import com.xiangri.dongdong.perstener.RegisterActivityPerstion;

public  class RegisterActivity extends BaseActivity<RegisterActivityPerstion> {
    @Override
    public Class<RegisterActivityPerstion> getDelegateClass() {
        return RegisterActivityPerstion.class;
    }
}