package com.xiangri.dongdong.perstener;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.xiangri.dongdong.R;
import com.xiangri.dongdong.activity.ShowShopActivity;
import com.xiangri.dongdong.entity.RegisterBean;
import com.xiangri.dongdong.mvp.view.AppDelegate;
import com.xiangri.dongdong.net.Http;
import com.xiangri.dongdong.utils.SpUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowShopActivityPerstener extends AppDelegate implements View.OnClickListener {

    private Context mContext;
    @BindView(R.id.web_shop)
    WebView webShop;
    private SimpleDraweeView simpColl;
    private TextView inByCar;
    private TextView nowBay;
    private String pid;
    private static final int ADD_CHAR_CONTNEN = 1;

    @Override
    public void initData() {
        super.initData();
        Intent intent = ((ShowShopActivity) mContext).getIntent();
        String url = intent.getStringExtra("url");
        pid = intent.getStringExtra("pid");
        //設置事件
        setEvent();
        //显示Web信息资源
        showWebView(url);
        ButterKnife.bind(((ShowShopActivity) mContext));
    }

    private void showWebView(String url) {

        webShop.setWebViewClient(new WebViewClient());
        webShop.getSettings().setJavaScriptEnabled(true);
        webShop.loadUrl(url);
    }

    private void setEvent() {
        webShop = (WebView) getView(R.id.web_shop);
        simpColl = (SimpleDraweeView) getView(R.id.image_coll);
        simpColl.setImageResource(R.drawable.coll_no);
        setClick(this, R.id.txt_inmycar, R.id.txt_nowbay);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_inmycar:
                inPutCarShop();
                break;
            case R.id.txt_nowbay:
                break;
        }
    }

    private void inPutCarShop() {
        Boolean flag = (Boolean) SpUtil.getInserter(mContext).getSpData("login_flag", false);
        if (flag) {
            String uid = (String) SpUtil.getInserter(mContext).getSpData("uid", "0");
            getString(Http.ADD_SHOP_CAR_URL+"?uid="+uid+"&pid="+pid, ADD_CHAR_CONTNEN,null);
        }
    }

    @Override
    public void successString(String data, int type) {
        super.successString(data, type);
        switch (type){
            case ADD_CHAR_CONTNEN:
                Gson gson = new Gson();
                RegisterBean bean = gson.fromJson(data, RegisterBean.class);
                if ("0".equals(bean.getCode())){
                    toast(bean.getMsg());
                }else {
                    toast("添加失败");
                }
                break;
        }
    }

    @Override
    public void failString(String msg) {
        super.failString(msg);
    }
}