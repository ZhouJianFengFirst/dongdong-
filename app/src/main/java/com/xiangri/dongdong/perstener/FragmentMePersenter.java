package com.xiangri.dongdong.perstener;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiangri.dongdong.MainActivity;
import com.xiangri.dongdong.R;
import com.xiangri.dongdong.activity.LoginActivity;
import com.xiangri.dongdong.adapters.MeListAdapter;
import com.xiangri.dongdong.entity.MeListBean;
import com.xiangri.dongdong.mvp.view.AppDelegate;
import com.xiangri.dongdong.view.WaterView;

import java.util.ArrayList;
import java.util.List;

public class FragmentMePersenter extends AppDelegate implements View.OnClickListener{

    private Context mContext;
    private WaterView water;
    private RecyclerView list;
    private List<MeListBean> names = new ArrayList<>();
    private ImageView userImage;
    private TextView showUser;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_fragment_me;
    }

    @Override
    public void initContext(Context context) {
        super.initContext(context);
        this.mContext = context;
    }

    @Override
    public void initData() {
        super.initData();

        //設置事件
        setEvent();
        //设置adapter
        setAdapter();
    }

    private void setAdapter() {
        names.add(new MeListBean("位置",R.drawable.address));
        names.add(new MeListBean("收藏",R.drawable.shoucang));
        names.add(new MeListBean("个人信息",R.drawable.me));
        names.add(new MeListBean("钱包",R.drawable.money));
        names.add(new MeListBean("設置",R.drawable.setting));
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(layoutManager);
        MeListAdapter meListAdapter = new MeListAdapter(mContext, names);
        list.setAdapter(meListAdapter);
    }

    private void setEvent() {
       water =  (WaterView)getView(R.id.water);
       list = (RecyclerView)getView(R.id.show_me_list);
         showUser= (TextView) getView(R.id.show_user);
       //水波纹设置监听
       water.setLisener(new WaterView.AnimalLisener() {
                @Override
            public void getHeight(int y) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) list.getLayoutParams();
                layoutParams.setMargins(0,0,0,y);
                list.setLayoutParams(layoutParams);
            }
        });
        userImage = (ImageView) getView(R.id.userimage);
        userImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.userimage:
                ((MainActivity)mContext).startActivity(new Intent(mContext, LoginActivity.class));
                break;
        }
    }

    public void setUserMessage(String username) {
        showUser.setText(username);
    }
}