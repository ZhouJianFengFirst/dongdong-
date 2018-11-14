package com.xiangri.dongdong.perstener;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xiangri.dongdong.R;
import com.xiangri.dongdong.activity.SearchActivity;
import com.xiangri.dongdong.activity.ShopListActivity;
import com.xiangri.dongdong.entity.HistoryEntity;
import com.xiangri.dongdong.mvp.view.AppDelegate;
import com.xiangri.dongdong.sql.SqlUtil;
import com.xiangri.dongdong.view.SelfView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivityPersenter extends AppDelegate implements View.OnClickListener {

    private Context mContext;
    private EditText edPut;
    private TextView txtAll;
    private TextView txtShop;
    private SelfView self;
    private TextView txtSerch;
    @Override
    public void initData() {
        super.initData();

        //初始化控件
        initWeght();
        List<HistoryEntity> historyEntities = SqlUtil.getInstens().queryAll();
        self.setData(historyEntities);
    }

    private void initWeght() {
        edPut = (EditText) getView(R.id.et_input);
        txtSerch = (TextView) getView(R.id.txt_serch);
        self = (SelfView) getView(R.id.self);
        setClick(this, R.id.iv_back);
        txtSerch.setOnClickListener(this);
    }

    @Override
    public void initContext(Context context) {
        super.initContext(context);
        this.mContext = context;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_serch:
                String trim = edPut.getText().toString().trim();
                serchShop(trim);
                break;
            case R.id.iv_back:
                ((SearchActivity) mContext).finish();
                break;
        }
    }

    private void serchShop(String trim) {
        if ("".equals(trim.trim()) || trim == null){
            return;
        }
        //判断如果有重复的进行删除
        List<HistoryEntity> quer = SqlUtil.getInstens().queryAll();
        for (int i = 0 ; i <quer.size() ; i ++){
            if (quer.get(i).getHistory().equals(trim)){
                SqlUtil.getInstens().deleteByKey(quer.get(i).getId());
            }
        }
        //添加数据
        SqlUtil.getInstens().insert("search",trim);

        //查询数据
        List<HistoryEntity> historyEntities = SqlUtil.getInstens().queryAll();
        //判断20条之后自动清空
        if (historyEntities.size() == 20) {
            SqlUtil.getInstens().deleteAll();
        }
        self.setData(historyEntities);
        Intent intent = new Intent(mContext, ShopListActivity.class);
        intent.putExtra("keywords", trim);
        ((SearchActivity) mContext).finish();
        ((SearchActivity) mContext).startActivity(intent);
    }
}