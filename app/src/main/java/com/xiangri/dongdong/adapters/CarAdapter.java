package com.xiangri.dongdong.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;

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
    protected void convert(ViewHolder viewHolder, final CarBean.DataBean dataBean, int postion) {
        viewHolder.setText(R.id.txt_content, dataBean.getSellerName());
        RecyclerView shopMerchant = (RecyclerView) viewHolder.getView(R.id.shop_merchant);
        final ImageView imageSelect = (ImageView) viewHolder.getView(R.id.shaohome_select);

        imageSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataBean.isShopHomeSelect()) {
                    dataBean.setShopHomeSelect(false);
                    for (int i = 0; i < dataBean.getList().size(); i++) {
                        dataBean.getList().get(i).setIschecked(false);
                        listener.back(dataBean.getList().get(i).getNum());
                    }
                    notifyDataSetChanged();
                    imageSelect.setImageResource(R.drawable.cricle_no);

                } else {
                    dataBean.setShopHomeSelect(true);
                    for (int i = 0; i < dataBean.getList().size(); i++) {
                        dataBean.getList().get(i).setIschecked(true);
                        listener.back(dataBean.getList().get(i).getNum());
                    }
                    notifyDataSetChanged();
                    imageSelect.setImageResource(R.drawable.cricle_yes);
                }
            }
        });

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
