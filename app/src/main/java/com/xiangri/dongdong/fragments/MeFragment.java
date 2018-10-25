package com.xiangri.dongdong.fragments;

import com.xiangri.dongdong.mvp.persenter.BaseFragment;
import com.xiangri.dongdong.perstener.FragmentHomePersenter;
import com.xiangri.dongdong.perstener.FragmentListPersenter;
import com.xiangri.dongdong.perstener.FragmentMePersenter;

public class MeFragment extends BaseFragment<FragmentMePersenter> {
    @Override
    public Class<FragmentMePersenter> getDelegateClass() {
        return FragmentMePersenter.class;
    }
}
