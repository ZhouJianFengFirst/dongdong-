package com.xiangri.dongdong.perstener;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;
import com.xiangri.dongdong.R;
import com.xiangri.dongdong.adapters.LeftRecyAdapter;
import com.xiangri.dongdong.adapters.ListAdapter;
import com.xiangri.dongdong.adapters.RightListAdapter;
import com.xiangri.dongdong.entity.HistoryEntity;
import com.xiangri.dongdong.entity.JiuBean;
import com.xiangri.dongdong.entity.RightShopBean;
import com.xiangri.dongdong.entity.ShopBean;
import com.xiangri.dongdong.mvp.view.AppDelegate;
import com.xiangri.dongdong.net.Http;
import com.xiangri.dongdong.sql.SqlUtil;
import com.xiangri.dongdong.utils.NetworkUtils;
import com.xiangri.dongdong.view.TopView;

import java.util.List;

public class FragmentListPersenter extends AppDelegate implements LeftRecyAdapter.OnClikListenerOk {

    private static final int CONTENT_REQUEST = 5;
    private static final int RIGHT_REQUEST = 6;
    private Context mContext;
    private RecyclerView leftRecy;
    private ListView reightList;
    private RightListAdapter listAdapter;
    private LeftRecyAdapter leftRecyAdapter;

    @Override
    public void initData() {
        super.initData();

        //設置事件
        setEvent();

        doHttp();
        //设置左面的列表进行网络请求
    }

    private void doHttp() {

        if (!NetworkUtils.isConnected(mContext)) {
            HistoryEntity leftHistory = SqlUtil.getInstens().queryByType( CONTENT_REQUEST+ "");
            Log.d("Taggger1",leftHistory.getHistory()+"");
            setLeftAdapter(leftHistory.getHistory());
            HistoryEntity rightHistory = SqlUtil.getInstens().queryByType(RIGHT_REQUEST + "");
            Log.d("Taggger2",rightHistory.getHistory()+"");
            setRightAdapter(rightHistory.getHistory());
            return;
        }
        getString(Http.JIU_URL, CONTENT_REQUEST, null);

        getString(Http.RIGHT_URL + "1", RIGHT_REQUEST, null);
    }

    private void setEvent() {
        TopView topView = (TopView) getView(R.id.topview);
        topView.showLeft(false);
        leftRecy = (RecyclerView) getView(R.id.left);
        reightList = (ListView) getView(R.id.right);

        listAdapter = new RightListAdapter(mContext);
        reightList.setAdapter(listAdapter);

        leftRecyAdapter = new LeftRecyAdapter(mContext);
        leftRecyAdapter.setListenerOk(this);
        leftRecy.setAdapter(leftRecyAdapter);

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
                SqlUtil.getInstens().insert(CONTENT_REQUEST+"",data);
                break;
            case RIGHT_REQUEST:
                setRightAdapter(data);
                SqlUtil.getInstens().insert(RIGHT_REQUEST+"",data);
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
        leftRecyAdapter.setList(jiuBean.getData());
    }

    @Override
    public void clickOk(int cid) {
        if (!NetworkUtils.isConnected(mContext)){
            toast("请检查当前网络");
        }
        getString(Http.RIGHT_URL + cid, RIGHT_REQUEST, null);
    }

    public void setRightAdapter(String data) {
        Gson gson = new Gson();
        RightShopBean shopBean = gson.fromJson(data, RightShopBean.class);
        listAdapter.setList(shopBean.getData());
    }
}