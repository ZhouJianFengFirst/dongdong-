package com.xiangri.dongdong.fragments;

import com.xiangri.dongdong.mvp.persenter.BaseFragment;
import com.xiangri.dongdong.perstener.FragmentHomePersenter;

public class HomeFragment extends BaseFragment<FragmentHomePersenter> {
    @Override
    public Class<FragmentHomePersenter> getDelegateClass() {
        return FragmentHomePersenter.class;
    }
}
