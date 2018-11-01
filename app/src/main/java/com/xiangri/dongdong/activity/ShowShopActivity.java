package com.xiangri.dongdong.activity;

import com.xiangri.dongdong.perstener.ShowShopActivityPerstener;
import com.xiangri.dongdong.mvp.persenter.BaseActivity;

public  class ShowShopActivity extends BaseActivity<ShowShopActivityPerstener>{
    @Override
    public Class<ShowShopActivityPerstener> getDelegateClass() {
        return ShowShopActivityPerstener.class;
    }
}
