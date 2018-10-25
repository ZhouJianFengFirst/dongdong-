package com.xiangri.dongdong.fragments;

import com.xiangri.dongdong.mvp.persenter.BaseFragment;
import com.xiangri.dongdong.perstener.FragmentHomePersenter;
import com.xiangri.dongdong.perstener.FragmentListPersenter;

public class ListFragment extends BaseFragment<FragmentListPersenter> {
    @Override
    public Class<FragmentListPersenter> getDelegateClass() {
        return FragmentListPersenter.class;
    }
}
