package com.xiangri.dongdong.perstener;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xiangri.dongdong.R;
import com.xiangri.dongdong.activity.ShowShopActivity;
import com.xiangri.dongdong.mvp.view.AppDelegate;

public class ShowShopActivityPerstener  extends AppDelegate {

    private Context mContext;
    private WebView webShop;

    @Override
    public void initData() {
        super.initData();
        Intent intent = ((ShowShopActivity) mContext).getIntent();
        String url = intent.getStringExtra("url");
        intent.getIntExtra("pid",0);

        //設置事件
        setEvent();
        //显示Web信息资源
        showWebView(url);

    }

    private void showWebView(String url) {
        webShop.setWebViewClient(new WebViewClient());
        webShop.getSettings().setJavaScriptEnabled(true);
        webShop.loadUrl(url);
    }

    private void setEvent() {
        webShop = (WebView)getView(R.id.web_shop);
    }

    @Override
    public void initContext(Context context) {
        super.initContext(context);
        this.mContext = context;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_show_shop;
    }
}