package com.xiangri.dongdong;

import com.xiangri.dongdong.mvp.persenter.BaseActivity;
import com.xiangri.dongdong.perstener.MainActivityPerstener;
import com.xiangri.dongdong.utils.SpUtil;

public class MainActivity extends BaseActivity<MainActivityPerstener> {

    @Override
    public Class<MainActivityPerstener> getDelegateClass() {
        return MainActivityPerstener.class;
    }

}