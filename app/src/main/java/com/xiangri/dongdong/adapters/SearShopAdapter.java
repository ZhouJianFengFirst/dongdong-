package com.xiangri.dongdong.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.xiangri.dongdong.R;
import com.xiangri.dongdong.activity.ShopListActivity;
import com.xiangri.dongdong.activity.ShowShopActivity;
import com.xiangri.dongdong.entity.SearShopBean;
import java.util.List;

public  class SearShopAdapter   extends RecycleAdapter<SearShopBean.DataBean>{

    private Context mContext;

    public SearShopAdapter(Context mcontext) {
        super(mcontext);
        this.mContext = mcontext;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_iteam_image;
    }

    @Override
    protected void convert(ViewHolder viewHolder, final SearShopBean.DataBean dataBean, int postion) {
        String[] split = dataBean.getImages().split("\\|");
        viewHolder.setImageUrl(R.id.gride_image,split[0])
                .setText(R.id.txt,dataBean.getTitle())
                .setText(R.id.txt_price,"价格￥:"+dataBean.getPrice()+"");
        viewHolder.getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ShowShopActivity.class);
                intent.putExtra("pid",dataBean.getPid()+"");
                intent.putExtra("url",dataBean.getDetailUrl());
                intent.putExtra("title",dataBean.getTitle());
                intent.putExtra("icont",dataBean.getImages());
                ((ShopListActivity)mContext).startActivity(intent);
            }
        });
    }

    @Override
    public void convertPrent(ViewHolder viewHolder, List<SearShopBean.DataBean> t, int postion) {

    }
}