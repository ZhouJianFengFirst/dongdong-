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
    private CarShopAdapter.ShopCarBackNumListener listener;

    public void setListener(CarShopAdapter.ShopCarBackNumListener listener) {
        this.listener = listener;
    }

    public CarAdapter(Context mcontext) {
        super(mcontext);
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
        CarShopAdapter carShopAdapter = new CarShopAdapter(mContext);
        carShopAdapter.setListener(listener);
        shopMerchant.setAdapter(carShopAdapter);
        carShopAdapter.setList(dataBean.getList());
    }

    @Override
    public void convertPrent(ViewHolder viewHolder, List<CarBean.DataBean> t, int postion) {

    }
}
