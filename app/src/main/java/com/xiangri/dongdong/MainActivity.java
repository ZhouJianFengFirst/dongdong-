package com.xiangri.dongdong;

import com.xiangri.dongdong.mvp.persenter.BaseActivity;
import com.xiangri.dongdong.perstener.MainActivityPerstener;

public class MainActivity extends BaseActivity<MainActivityPerstener> {

    @Override
    public Class<MainActivityPerstener> getDelegateClass() {
        return MainActivityPerstener.class;
    }
}