package com.xiangri.dongdong.perstener;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.xlistviewlib.XListView;
import com.google.gson.Gson;
import com.xiangri.dongdong.MainActivity;
import com.xiangri.dongdong.R;
import com.xiangri.dongdong.adapters.JiuPageAdapter;
import com.xiangri.dongdong.adapters.ListAdapter;
import com.xiangri.dongdong.entity.BannerBean;
import com.xiangri.dongdong.entity.JiuBean;
import com.xiangri.dongdong.entity.ShopBean;
import com.xiangri.dongdong.mvp.view.AppDelegate;
import com.xiangri.dongdong.net.Http;
import com.xiangri.dongdong.view.HorseView;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.android.PermissionUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentHomePersenter extends AppDelegate implements View.OnClickListener {

    private static final int BANNER_REQUEST = 1;
    private static final int JIU_REQUEST = 2;
    private static final int SHOP_REQUEST = 3;
    public static final int START_ACTIVITY = 4;
    private Context mContext;
    private BGABanner banner;
    private CircleImageView scan;
    private CircleImageView message;
    private List<String> images = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private ViewPager viewPager;
    private List<JiuBean.DataBean> onePage = new ArrayList<>();
    private List<JiuBean.DataBean> towPage = new ArrayList<>();
    private ViewPager viewpage;
    private LinearLayout childLinear;
    private XListView xlistview;
    private ListAdapter listAdapter;
    private RelativeLayout layoutTop;
    private RelativeLayout layoutTopSeach;
    private HorseView horseview;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_fragment_home;
    }

    @Override
    public void initContext(Context context) {
        super.initContext(context);
        this.mContext = context;
    }

    @Override
    public void initData() {
        super.initData();

        //设定事件
        setEvent();

        //设定banner图进行网络请求
        getString(Http.BANNER_URL, BANNER_REQUEST,null);

        //设定9宫格的网络请求
        getString(Http.JIU_URL, JIU_REQUEST,null);

        //设定列表进行网络请求
        getString(Http.SHOP_URL, SHOP_REQUEST,null);
    }

    private void setEvent() {

        //头部的搜索框
        layoutTop = (RelativeLayout) getView(R.id.layout_top);
        //初始化准备添加头部的view
        View view = View.inflate(mContext, R.layout.layout_home_jiu, null);
        view.findViewById(R.id.scan).setOnClickListener(this);
        //跑马灯
        horseview = (HorseView) view.findViewById(R.id.horseview);
        //头部信息里面的搜索框
        layoutTopSeach = (RelativeLayout) view.findViewById(R.id.layout_top);
        //头部信息里的viewpage
        viewpage = (ViewPager) view.findViewById(R.id.viewpage);
        //准备添加的小圆点控件
        childLinear = (LinearLayout) view.findViewById(R.id.layout_postion);
        //banner图的控件
        banner = (BGABanner) view.findViewById(R.id.banner);
        //扫一扫
        scan = (CircleImageView) getView(R.id.scan);
        //信息
        message = (CircleImageView) getView(R.id.message);
        //listview
        xlistview = (XListView) getView(R.id.xListView);
        //打开上拉刷新下拉加载的开关
        xlistview.setPullLoadEnable(true);
        xlistview.setPullRefreshEnable(true);
        xlistview.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                //设定banner图进行网络请求
                getString(Http.BANNER_URL, BANNER_REQUEST,null);

                //设定9宫格的网络请求
                getString(Http.JIU_URL, JIU_REQUEST,null);

                //设定列表进行网络请求
                getString(Http.SHOP_URL, SHOP_REQUEST,null);
            }

            @Override
            public void onLoadMore() {

            }
        });
        //初始化适配器
        listAdapter = new ListAdapter(mContext);
        //设置适配器
        xlistview.setAdapter(listAdapter);

        //添加头部
        xlistview.addHeaderView(view);
        scan.setOnClickListener(this);
        message.setOnClickListener(this);
        xlistview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                layoutTop.setBackgroundColor(Color.parseColor("#d43c3c"));
                if (firstVisibleItem >= 1) {
                    layoutTop.setVisibility(View.VISIBLE);
                    layoutTopSeach.setVisibility(View.GONE);
                } else {
                    layoutTop.setVisibility(View.GONE);
                    layoutTopSeach.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void successString(String data, int type) {
        super.successString(data, type);
        switch (type) {
            case BANNER_REQUEST:
                setBannerAdapter(data);
                break;
            case JIU_REQUEST:
                setViewPageAdapter(data);
                break;
            case SHOP_REQUEST:
                Gson gson = new Gson();
                ShopBean shopBean = gson.fromJson(data, ShopBean.class);
                listAdapter.setShopList(shopBean.getData());
                break;
        }
    }

    private void setViewPageAdapter(String data) {
        if (data.contains(">")) {
            return;
        }
        //实例化数据
        Gson gson = new Gson();
        JiuBean jiuBean = gson.fromJson(data, JiuBean.class);
        for (int i = 0; i < jiuBean.getData().size(); i++) {
            if (i >= 8 && i < 16) {
                towPage.add(jiuBean.getData().get(i));
            } else if (i < 8) {
                onePage.add(jiuBean.getData().get(i));
            }
        }
        //设置跑马灯的数据
        List<String> titles = new ArrayList<>();
        List<String> images = new ArrayList<>();
        for (int i = 0; i < jiuBean.getData().size(); i++) {
            titles.add(jiuBean.getData().get(i).getName());
            images.add(jiuBean.getData().get(i).getIcon());
        }
        horseview.setData(titles, images);
        JiuPageAdapter jiuPageAdapter = new JiuPageAdapter(mContext, onePage, towPage, 2);
        viewpage.setAdapter(jiuPageAdapter);
    }

    /**
     * 设定轮播图
     *
     * @param data
     */
    private void setBannerAdapter(String data) {
        Gson gson = new Gson();
        BannerBean bannerBean = gson.fromJson(data, BannerBean.class);
        for (int i = 0; i < bannerBean.getData().size(); i++) {
            images.add(bannerBean.getData().get(i).getIcon().replace("https", "http"));
            titles.add(bannerBean.getData().get(i).getTitle());
        }

        banner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, @Nullable String model, int position) {
                Glide.with(mContext).load(model).into(itemView);
            }
        });
        banner.setData(images, titles);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scan:
                PermissionUtils.permission(mContext, new PermissionUtils.PermissionListener() {
                    @Override
                    public void success() {
                        ((MainActivity) mContext).startActivityForResult(new Intent(mContext, CaptureActivity.class), START_ACTIVITY);
                    }
                });
                break;
            case R.id.message:
                break;
        }
    }
}