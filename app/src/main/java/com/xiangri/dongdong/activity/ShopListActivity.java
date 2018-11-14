package com.xiangri.dongdong.activity;

import com.xiangri.dongdong.mvp.persenter.BaseActivity;
import com.xiangri.dongdong.perstener.ShopListActivityPerstener;

public class ShopListActivity extends BaseActivity<ShopListActivityPerstener> {
    @Override
    public Class<ShopListActivityPerstener> getDelegateClass() {
        return ShopListActivityPerstener.class;
    }
}