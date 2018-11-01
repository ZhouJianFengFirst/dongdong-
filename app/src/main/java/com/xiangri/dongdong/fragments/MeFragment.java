package com.xiangri.dongdong.fragments;

import com.xiangri.dongdong.mvp.persenter.BaseFragment;
import com.xiangri.dongdong.perstener.FragmentHomePersenter;
import com.xiangri.dongdong.perstener.FragmentListPersenter;
import com.xiangri.dongdong.perstener.FragmentMePersenter;
import com.xiangri.dongdong.utils.SpUtil;

public class MeFragment extends BaseFragment<FragmentMePersenter> {
    @Override
    public Class<FragmentMePersenter> getDelegateClass() {
        return FragmentMePersenter.class;
    }

    @Override
    public void onResume() {
        super.onResume();
        Boolean login_flag = (Boolean) SpUtil.getInserter(getActivity()).getSpData("login_flag", false);
        if (login_flag){
            String username = (String) SpUtil.getInserter(getActivity()).getSpData("username", "");
            delegate.setUserMessage(username);
        }
    }
}
