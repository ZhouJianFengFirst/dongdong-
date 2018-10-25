package com.xiangri.dongdong.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.xiangri.dongdong.R;
import com.xiangri.dongdong.entity.CarBean;

import java.util.List;

public class CarAdapter extends RecycleAdapter<CarBean.DataBean> {

    private Context mContext;
    private CarShopAdapter.OkClick okClick;

    public void setOkClick(CarShopAdapter.OkClick okClick) {
        this.okClick = okClick;
    }

    public CarAdapter(Context mcontext, List<CarBean.DataBean> datas) {
        super(mcontext, datas);
        this.mContext = mcontext;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_iteam_list;
    }

    @Override
    protected void convert(ViewHolder viewHolder, CarBean.DataBean dataBean, int postion) {
        viewHolder.setText(R.id.txt_content, dataBean.getSellerName());
        View view = viewHolder.getRootView();
        RecyclerView shopMerchant = (RecyclerView) view.findViewById(R.id.shop_merchant);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        shopMerchant.setLayoutManager(staggeredGridLayoutManager);
        CarShopAdapter carShopAdapter = new CarShopAdapter(mContext, dataBean.getList());
        carShopAdapter.setOkClick(okClick);
        shopMerchant.setAdapter(carShopAdapter);
    }

    @Override
    public void convertPrent(ViewHolder viewHolder, List<CarBean.DataBean> t, int postion) {

    }
}