package com.xiangri.dongdong.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.xiangri.dongdong.MainActivity;
import com.xiangri.dongdong.R;
import com.xiangri.dongdong.activity.SearchActivity;
import com.xiangri.dongdong.activity.ShopListActivity;
import com.xiangri.dongdong.activity.ShowShopActivity;
import com.xiangri.dongdong.entity.RightShopBean;
import com.xiangri.dongdong.entity.ShopBean;

import java.util.List;

public class RightShopAdapter extends RecycleAdapter<RightShopBean.DataBean.ListBean> {

    private Context mContext;

    public RightShopAdapter(Context mcontext) {
        super(mcontext);
        this.mContext = mcontext;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_jiu_shopmessage;
    }

    @Override
    protected void convert(ViewHolder viewHolder, final RightShopBean.DataBean.ListBean listBean, int postion) {
        viewHolder.setImageUrl(R.id.gride_image, listBean.getIcon()).setText(R.id.txt, listBean.getName());
        viewHolder.getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ShopListActivity.class);
                intent.putExtra("keywords", listBean.getName());
                ((MainActivity) mContext).startActivity(intent);
            }
        });
    }

    @Override
    public void convertPrent(ViewHolder viewHolder, List<RightShopBean.DataBean.ListBean> t, int postion) {

    }
}