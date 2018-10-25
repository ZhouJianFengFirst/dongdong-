package com.xiangri.dongdong.adapters;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.xiangri.dongdong.R;
import com.xiangri.dongdong.entity.RightShopBean;
import com.xiangri.dongdong.entity.ShopBean;

import java.util.List;

public class RightShopAdapter extends RecycleAdapter<RightShopBean.DataBean.ListBean> {

    private Context mContext;

    public RightShopAdapter(Context mcontext, List<RightShopBean.DataBean.ListBean> datas) {
        super(mcontext, datas);
        this.mContext = mcontext;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_jiu_shopmessage;
    }

    @Override
    protected void convert(ViewHolder viewHolder, RightShopBean.DataBean.ListBean listBean, int postion) {
        viewHolder.setImageUrl(R.id.gride_image, listBean.getIcon()).setText(R.id.txt, listBean.getName());
    }

    @Override
    public void convertPrent(ViewHolder viewHolder, List<RightShopBean.DataBean.ListBean> t, int postion) {

    }
}