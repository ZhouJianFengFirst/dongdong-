package com.xiangri.dongdong.perstener;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.xlistviewlib.XListView;
import com.google.gson.Gson;
import com.xiangri.dongdong.MainActivity;
import com.xiangri.dongdong.R;
import com.xiangri.dongdong.activity.SearchActivity;
import com.xiangri.dongdong.adapters.JiuPageAdapter;
import com.xiangri.dongdong.adapters.ListAdapter;
import com.xiangri.dongdong.entity.BannerBean;
import com.xiangri.dongdong.entity.HistoryEntity;
import com.xiangri.dongdong.entity.JiuBean;
import com.xiangri.dongdong.entity.JiuDataBean;
import com.xiangri.dongdong.entity.ShopBean;
import com.xiangri.dongdong.mvp.view.AppDelegate;
import com.xiangri.dongdong.net.Http;
import com.xiangri.dongdong.sql.SqlUtil;
import com.xiangri.dongdong.utils.NetworkUtils;
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
    private List<JiuDataBean> onePage = new ArrayList<>();
    private List<JiuDataBean> towPage = new ArrayList<>();
    private List<JiuDataBean> threePage = new ArrayList<>();
    private ViewPager viewpage;
    private LinearLayout childLinear;
    private XListView xlistview;
    private ListAdapter listAdapter;
    private RelativeLayout layoutTop;
    private RelativeLayout layoutTopSeach;
    private HorseView horseview;
    private LinearLayout layoutPostion;
    private JiuBean jiuBean;

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
        //初始化数据
        setEvent();

        //网络请求
        if (!NetworkUtils.isConnected(mContext)) {
            notNetHtpp();
            return;
        }
        doHttp();
    }

    private void notNetHtpp() {
        //查詢
        HistoryEntity bannerHistory = SqlUtil.getInstens().queryByType(BANNER_REQUEST + "");
        setBannerAdapter(bannerHistory.getHistory());

        HistoryEntity jiuHistory = SqlUtil.getInstens().queryByType(JIU_REQUEST + "");
        setViewPageAdapter(jiuHistory.getHistory());

        HistoryEntity shopHistory = SqlUtil.getInstens().queryByType(SHOP_REQUEST + "");
        setShopAdapter(shopHistory.getHistory());
    }

    public void setAnimation(View view, int s) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0.7f, 1f);
        animator.setDuration(s);
        animator.start();
    }

    private void setEvent() {

        //头部的搜索框
        layoutTop = (RelativeLayout) getView(R.id.layout_top);
        //初始化准备添加头部的view
        View view = View.inflate(mContext, R.layout.layout_home_jiu, null);
        view.findViewById(R.id.scan).setOnClickListener(this);
        view.findViewById(R.id.et_sou).setOnClickListener(this);
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
                //网络请求
                if (!NetworkUtils.isConnected(mContext)) {
                    notNetHtpp();
                    return;
                }
                doHttp();
                xlistview.stopRefresh();
            }

            @Override
            public void onLoadMore() {
                /* doHttp();*/
                xlistview.stopLoadMore();
            }
        });
        //初始化适配器
        listAdapter = new ListAdapter(mContext);
        //设置适配器
        xlistview.setAdapter(listAdapter);

        //添加头部
        xlistview.addHeaderView(view);
        scan.setOnClickListener(this);
        getView(R.id.et_sou).setOnClickListener(this);
        message.setOnClickListener(this);
        xlistview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                layoutTop.setBackgroundColor(Color.parseColor("#d43c3c"));
                if (firstVisibleItem >= 1) {
                    setAnimation(layoutTop, 3000);
                    layoutTop.setVisibility(View.VISIBLE);
                    setAnimation(layoutTop, 3000);
                    layoutTopSeach.setVisibility(View.GONE);

                } else {
                    setAnimation(layoutTop, 3000);
                    layoutTop.setVisibility(View.GONE);
                    setAnimation(layoutTop, 3000);
                    layoutTopSeach.setVisibility(View.VISIBLE);
                }
            }
        });
        setPoiont(0);
        viewpage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                setPoiont(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }


    public void setPoiont(int postion) {
        childLinear.removeAllViews();
        for (int i = 0; i < 3; i++) {
            ImageView imageView = new ImageView(mContext);
            if (postion == i) {
                imageView.setImageResource(R.drawable.solid_red);
            } else {
                imageView.setImageResource(R.drawable.solid);
            }
            childLinear.addView(imageView);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
            layoutParams.height = 10;
            layoutParams.width = 10;
            layoutParams.setMargins(5, 0, 5, 0);
            imageView.setLayoutParams(layoutParams);
        }
    }

    @Override
    public void failString(String msg) {
        super.failString(msg);
        toast("");
    }

    @Override
    public void successString(String data, int type) {
        super.successString(data, type);
        switch (type) {
            case BANNER_REQUEST:
                setBannerAdapter(data);
                SqlUtil.getInstens().insert(BANNER_REQUEST + "", data);
                break;
            case JIU_REQUEST:
                setViewPageAdapter(data);
                SqlUtil.getInstens().insert(JIU_REQUEST + "", data);
                break;
            case SHOP_REQUEST:

                setShopAdapter(data);
                SqlUtil.getInstens().insert(SHOP_REQUEST + "", data);
                break;
        }
    }

    private void setShopAdapter(String data) {
        if (data.contains(">")) {
            getString(Http.SHOP_URL, SHOP_REQUEST, null);
            return;
        }
        if (data != null) {
            Gson gson = new Gson();
            ShopBean shopBean = gson.fromJson(data, ShopBean.class);
            List<ShopBean.DataBean> data1 = shopBean.getData();
            data1.remove(0);
            listAdapter.setShopList(data1);
        }
    }


    /**
     * 设置page九宮格
     *
     * @param data
     */
    private void setViewPageAdapter(String data) {
        if (data.contains(">")) {
            return;
        }

        towPage.clear();
        onePage.clear();
        //实例化数据
        Gson gson = new Gson();
        jiuBean = gson.fromJson(data, JiuBean.class);
        for (int i = 0; i < jiuBean.getData().size(); i++) {
            if (i < 8) {
                towPage.add(jiuBean.getData().get(i));
            } else if (i >= 8 && i < 16) {
                onePage.add(jiuBean.getData().get(i));
            } else if (i >= 16 && i < 23) {
                threePage.add(jiuBean.getData().get(i));
            }
        }
        if (jiuBean != null) {
            //设置跑马灯的数据
            List<String> titles = new ArrayList<>();
            List<String> images = new ArrayList<>();
            for (int i = 0; i < jiuBean.getData().size(); i++) {
                titles.add(jiuBean.getData().get(i).getName());
                images.add(jiuBean.getData().get(i).getIcon());
            }
            horseview.setData(titles, images);
        }
        JiuPageAdapter jiuPageAdapter = new JiuPageAdapter(mContext, onePage, towPage,threePage, 3);
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
            case R.id.et_sou:
                ((MainActivity) mContext).startActivityForResult(new Intent(mContext, SearchActivity.class), START_ACTIVITY);
                break;
        }
    }

    public void doHttp() {
        //设定banner图进行网络请求
        getString(Http.BANNER_URL, BANNER_REQUEST, null);

        //设定9宫格的网络请求
        getString(Http.JIU_URL, JIU_REQUEST, null);

        //设定列表进行网络请求
        getString(Http.SHOP_URL, SHOP_REQUEST, null);
    }
}