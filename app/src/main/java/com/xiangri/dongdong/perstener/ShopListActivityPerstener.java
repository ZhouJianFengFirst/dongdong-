package com.xiangri.dongdong.perstener;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.xiangri.dongdong.R;
import com.xiangri.dongdong.activity.SearchActivity;
import com.xiangri.dongdong.activity.ShopListActivity;
import com.xiangri.dongdong.adapters.SearShopAdapter;
import com.xiangri.dongdong.entity.SearShopBean;
import com.xiangri.dongdong.mvp.view.AppDelegate;
import com.xiangri.dongdong.net.Http;

import java.util.HashMap;
import java.util.Map;

public class ShopListActivityPerstener extends AppDelegate implements View.OnClickListener {

    private Context mContext;
    private int page = 1;
    private static final int SHOP_CONTENT = 0x123;
    private TextView txtInput,txtShop,txtShopHome;
    private ImageView cameraImage ;
    private XRecyclerView shopList;
    private String keywords;
    private SearShopBean searShopBean;

    @Override
    public void initData() {
        super.initData();
        Intent intent = ((ShopListActivity) mContext).getIntent();
        keywords = intent.getStringExtra("keywords");

        Map<String, String> map = new HashMap<>();
        map.put("keywords", keywords);
        map.put("page", page+"");
        map.put("source", "android");
        //初始化控件
        initWeght();
        //网络请求数据
        doHttp(map);
    }

    public void doHttp(Map<String,String> map) {
        getString(Http.SCAN_SHOP_URL, SHOP_CONTENT, map);
    }

    private void initWeght() {
        txtInput = (TextView)getView(R.id.txtput);
        txtInput.setText(keywords);
        txtShop = (TextView)getView(R.id.txt_shop);
        txtShopHome = (TextView)getView(R.id.txt_shophome);
        cameraImage = (ImageView)getView(R.id.image_serch);
        shopList = (XRecyclerView)getView(R.id.shoplist);
        txtInput.setOnClickListener(this);
        shopList.setPullRefreshEnabled(true);
        getView(R.id.iv_back).setOnClickListener(this);
        shopList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                Map<String, String> map = new HashMap<>();
                map.put("keywords", keywords);
                map.put("page", page+"");
                map.put("source", "android");
                doHttp(map);
            }

            @Override
            public void onLoadMore() {
                page++;
                if (searShopBean.getData().size() == 0){
                    page =0;
                    toast("沒有更多数据");
                }
                Map<String, String> map = new HashMap<>();
                map.put("keywords", keywords);
                map.put("page", page+"");
                map.put("source", "android");
                doHttp(map);
            }
        });
    }

    @Override
    public void initContext(Context context) {
        super.initContext(context);
        this.mContext = context;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_iteam_listshop;
    }

    @Override
    public void successString(String data, int type) {
        super.successString(data, type);
        switch (type) {
            case SHOP_CONTENT:
                setShopAdapter(data);
                break;
        }
    }

    @Override
    public void failString(String msg) {
        super.failString(msg);
    }

    public void setShopAdapter(String data) {
         searShopBean = new Gson().fromJson(data, SearShopBean.class);
        if (searShopBean.getData().size() == 0) {
            page = 0;
        }
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        SearShopAdapter searShopAdapter = new SearShopAdapter(mContext,searShopBean.getData());
        shopList.setLayoutManager(manager);
        shopList.setAdapter(searShopAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txtput:
                mContext.startActivity(new Intent(mContext, SearchActivity.class));
                break;
            case R.id.iv_back:
                ((ShopListActivity)mContext).finish();
                break;
        }
    }
}