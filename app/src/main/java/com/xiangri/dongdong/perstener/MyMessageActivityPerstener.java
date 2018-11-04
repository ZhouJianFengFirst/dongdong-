package com.xiangri.dongdong.perstener;

import android.content.Context;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xiangri.dongdong.R;
import com.xiangri.dongdong.mvp.view.AppDelegate;

public class MyMessageActivityPerstener extends AppDelegate implements View.OnClickListener{

    private Context mContext;
    private SimpleDraweeView userSimpView;

    @Override
    public void initContext(Context context) {
        super.initContext(context);
        this.mContext = context;
    }

    @Override
    public void initData() {
        super.initData();

        //设置事件
        setEvent();
        //设置点击事件
        setClick(this,R.id.user_up_tioimage,R.id.user_vip,R.id.user_sex,R.id.user_address);
    }

    private void setEvent() {
        userSimpView =  (SimpleDraweeView) getView(R.id.litter_user_image);
        userSimpView.setImageResource(R.drawable.login);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_message;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
        }
    }
}
