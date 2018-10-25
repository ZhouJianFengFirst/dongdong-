package com.xiangri.dongdong.adapters;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.xiangri.dongdong.R;
import com.xiangri.dongdong.entity.ShopBean;

import java.util.List;

public class ShopAdapter extends RecycleAdapter<ShopBean.DataBean.ListBean> {

    private Context mContext;
    private int i = 0;

    public ShopAdapter(Context mcontext, List<ShopBean.DataBean.ListBean> datas) {
        super(mcontext, datas);
        this.mContext = mcontext;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_jiu_shopmessage;
    }

    @Override
    protected void convert(com.xiangri.dongdong.adapters.ViewHolder viewHolder, final ShopBean.DataBean.ListBean listBean, int postion) {
        i = postion;
        String[] split = listBean.getImages().split("\\|");
        viewHolder.setImageUrl(R.id.gride_image, split[0]).setText(R.id.txt, listBean.getTitle());
        viewHolder.getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, listBean.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void convertPrent(ViewHolder viewHolder, List<ShopBean.DataBean.ListBean> t, int postion) {

    }
}