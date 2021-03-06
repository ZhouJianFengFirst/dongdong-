package com.xiangri.dongdong.perstener;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiangri.dongdong.MainActivity;
import com.xiangri.dongdong.R;
import com.xiangri.dongdong.fragments.HomeFragment;
import com.xiangri.dongdong.fragments.ListFragment;
import com.xiangri.dongdong.fragments.MeFragment;
import com.xiangri.dongdong.fragments.ShopCarFragment;
import com.xiangri.dongdong.mvp.view.AppDelegate;
import com.xiangri.dongdong.tabview.TabView;
import com.xiangri.dongdong.tabview.TabViewChild;
import com.xiangri.dongdong.utils.UltimateBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivityPerstener extends AppDelegate {

    private Context mContext;
    private List<TabViewChild> tabchilds = new ArrayList<>();
    private TabViewChild tabMe;
    private FragmentManager manger;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initContext(Context context) {
        super.initContext(context);
        this.mContext = context;
    }

    @Override
    public void initData() {
        super.initData();
        final TabView tabView = (TabView) getView(R.id.tabview);

        TabViewChild tabHome = new TabViewChild(R.drawable.index_yes, R.drawable.index_no, "首页", new HomeFragment());
        TabViewChild tabList = new TabViewChild(R.drawable.list_yes, R.drawable.list_no, "列表", new ListFragment());
        TabViewChild tabShopCar = new TabViewChild(R.drawable.car_yes, R.drawable.car_no, "购物车", new ShopCarFragment());
        tabMe = new TabViewChild(R.drawable.me_yes, R.drawable.me_no, "我的", new MeFragment());
        tabchilds.add(tabHome);
        tabchilds.add(tabList);
        tabchilds.add(tabShopCar);
        tabchilds.add(tabMe);
        manger = ((MainActivity) mContext).getSupportFragmentManager();
        tabView.setTabViewChild(tabchilds,manger);
        tabView.setOnTabChildClickListener(new TabView.OnTabChildClickListener() {
            @Override
            public void onTabChildClick(int position, ImageView imageView, TextView textView) {
                if (position == 1) {
                    setBar();
                }
            }
        });
    }

    private void setBar() {
        UltimateBar.newColorBuilder()
                .statusColor(Color.parseColor("#d43c3c"))
                .build((MainActivity) mContext)
                .apply();
    }
}