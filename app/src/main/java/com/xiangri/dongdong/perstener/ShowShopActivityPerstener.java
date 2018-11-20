package com.xiangri.dongdong.perstener;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.xiangri.dongdong.R;
import com.xiangri.dongdong.activity.ShowShopActivity;
import com.xiangri.dongdong.entity.RegisterBean;
import com.xiangri.dongdong.mvp.view.AppDelegate;
import com.xiangri.dongdong.net.Http;
import com.xiangri.dongdong.utils.SpUtil;

public class ShowShopActivityPerstener extends AppDelegate implements View.OnClickListener {

    private Context mContext;
    private WebView webShop;
    private SimpleDraweeView simpColl;
    private TextView inByCar;
    private TextView nowBay;
    private String pid;
    private static final int ADD_CHAR_CONTNEN = 1;
    private SimpleDraweeView smImage;
    private TextView desc;
    private RelativeLayout layoutShow;
    private String icont;
    private String title;

    @Override
    public void initData() {
        super.initData();
        Intent intent = ((ShowShopActivity) mContext).getIntent();
        String url = intent.getStringExtra("url");
        icont = intent.getStringExtra("icont");
        title = intent.getStringExtra("title");
        pid = intent.getStringExtra("pid");
        //設置事件qaaaq
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
        smImage = (SimpleDraweeView) getView(R.id.sm_image);
        String[] split = icont.split("\\|");
        smImage.setImageURI(split[0]);
        desc = (TextView) getView(R.id.desc);
        desc.setText(title);
        getView(R.id.image_down).setOnClickListener(this);
        getView(R.id.txt_inputcar).setOnClickListener(this);
        layoutShow = (RelativeLayout) getView(R.id.layout_show);
        webShop = (WebView) getView(R.id.web_shop);
        simpColl = (SimpleDraweeView) getView(R.id.image_coll);
        simpColl.setImageResource(R.drawable.coll_no);
        simpColl.setOnClickListener(this);
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
                layoutShow.setVisibility(View.VISIBLE);
                setAnimation(layoutShow, 1280, 0, 1000);
                break;
            case R.id.txt_nowbay:
                break;
            case R.id.txt_inputcar:
                 inPutCarShop();
                setAnimation(layoutShow, 0, 1280, 1000);
                break;
            case R.id.image_down:
                setAnimation(layoutShow, 0, 1280, 1000);
                break;
            case R.id.image_coll:
                toast("收藏成功");
                break;
        }
    }

    private void inPutCarShop() {
        Boolean flag = (Boolean) SpUtil.getInserter(mContext).getSpData("login_flag", false);
        if (flag) {
            String uid = (String) SpUtil.getInserter(mContext).getSpData("uid", "0");
            getString(Http.ADD_SHOP_CAR_URL + "?uid=" + uid + "&pid=" + pid, ADD_CHAR_CONTNEN, null);
        }
    }

    public void setAnimation(View view, int in, int to, int s) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", in, to);
        animator.setDuration(s);
        animator.start();
    }

    @Override
    public void successString(String data, int type) {
        super.successString(data, type);
        switch (type) {
            case ADD_CHAR_CONTNEN:
                Gson gson = new Gson();
                RegisterBean bean = gson.fromJson(data, RegisterBean.class);
                if ("0".equals(bean.getCode())) {
                    toast(bean.getMsg());
                } else {
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