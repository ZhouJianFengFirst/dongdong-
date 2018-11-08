package com.xiangri.dongdong.perstener;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.ListView;

import com.google.gson.Gson;
import com.xiangri.dongdong.R;
import com.xiangri.dongdong.adapters.LeftRecyAdapter;
import com.xiangri.dongdong.adapters.ListAdapter;
import com.xiangri.dongdong.adapters.RightListAdapter;
import com.xiangri.dongdong.entity.JiuBean;
import com.xiangri.dongdong.entity.RightShopBean;
import com.xiangri.dongdong.entity.ShopBean;
import com.xiangri.dongdong.mvp.view.AppDelegate;
import com.xiangri.dongdong.net.Http;
import com.xiangri.dongdong.view.TopView;

import java.util.List;

public class FragmentListPersenter extends AppDelegate implements LeftRecyAdapter.OnClikListenerOk {

    private static final int CONTENT_REQUEST = 1;
    private static final int RIGHT_REQUEST = 2;
    private Context mContext;
    private RecyclerView leftRecy;
    private ListView reightList;
    private RightListAdapter listAdapter;

    @Override
    public void initData() {
        super.initData();

        //設置事件
        setEvent();

        //设置左面的列表进行网络请求
        getString(Http.JIU_URL, CONTENT_REQUEST,null);

        getString(Http.RIGHT_URL+"1",RIGHT_REQUEST,null);
    }

    private void setEvent() {
        TopView topView = (TopView) getView(R.id.topview);
        topView.showLeft(false);
        leftRecy = (RecyclerView) getView(R.id.left);
        reightList = (ListView) getView(R.id.right);
        listAdapter = new RightListAdapter(mContext);
        reightList.setAdapter(listAdapter);
    }

    @Override
    public void initContext(Context context) {
        super.initContext(context);
        this.mContext = context;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_fragment_list;
    }

    @Override
    public void successString(String data, int type) {
        super.successString(data, type);
        switch (type) {
            case CONTENT_REQUEST:
                setLeftAdapter(data);
                break;
            case RIGHT_REQUEST:
                setRightAdapter(data);
                break;
        }
    }

    @Override
    public void failString(String msg) {
        super.failString(msg);
        toast(msg);
    }

    public void setLeftAdapter(String data) {
        if (data.contains(">")) {
            toast("請求失败");
            return;
        }

        Gson gson = new Gson();
        JiuBean jiuBean = gson.fromJson(data, JiuBean.class);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        leftRecy.setLayoutManager(linearLayoutManager);
        LeftRecyAdapter leftRecyAdapter = new LeftRecyAdapter(mContext, jiuBean.getData());
        leftRecyAdapter.setListenerOk(this);
        leftRecy.setAdapter(leftRecyAdapter);
    }

    @Override
    public void clickOk(int cid) {
        getString(Http.RIGHT_URL+cid,RIGHT_REQUEST,null);
    }

    public void setRightAdapter(String data) {
        Gson gson = new Gson();
        RightShopBean shopBean = gson.fromJson(data, RightShopBean.class);
        listAdapter.setList(shopBean.getData());
    }
}