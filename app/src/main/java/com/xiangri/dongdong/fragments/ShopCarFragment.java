package com.xiangri.dongdong.fragments;

import com.xiangri.dongdong.mvp.persenter.BaseFragment;
import com.xiangri.dongdong.perstener.FragmentHomePersenter;
import com.xiangri.dongdong.perstener.FragmentMePersenter;
import com.xiangri.dongdong.perstener.FragmentShopCarPersenter;

public class ShopCarFragment extends BaseFragment<FragmentShopCarPersenter> {
    @Override
    public Class<FragmentShopCarPersenter> getDelegateClass() {
        return FragmentShopCarPersenter.class;
    }
}
