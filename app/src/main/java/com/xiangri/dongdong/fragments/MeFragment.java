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
        if (login_flag) {
            String nickname = (String) SpUtil.getInserter(getActivity()).getSpData("nickname", "");
            String address = (String) SpUtil.getInserter(getActivity()).getSpData("address", "");
            String icon = (String) SpUtil.getInserter(getActivity()).getSpData("icon", "");
            delegate.setUserMessage(nickname,address,icon);
        } else {
            delegate.setNotLoginUserMessage();
        }
    }
}
