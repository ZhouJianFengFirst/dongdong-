package com.xiangri.dongdong.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.xiangri.dongdong.MainActivity;
import com.xiangri.dongdong.R;
import com.xiangri.dongdong.activity.ShowShopActivity;
import com.xiangri.dongdong.entity.ShopBean;
import com.xiangri.dongdong.entity.UserBean;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class ShopAdapter extends RecycleAdapter<ShopBean.DataBean.ListBean> {

    private Context mContext;
    private int i = 0;

    public ShopAdapter(Context mcontext) {
        super(mcontext);
        this.mContext = mcontext;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_jiu_shopmessage;
    }

    @Override
    protected void convert(com.xiangri.dongdong.adapters.ViewHolder viewHolder, final ShopBean.DataBean.ListBean listBean, int postion) {

        String[] split = listBean.getImages().split("\\|");
        viewHolder.setImageUrl(R.id.gride_image, split[0]).setText(R.id.txt, listBean.getTitle()).setText(R.id.txt_price, "￥" + listBean.getPrice() + "");
        viewHolder.getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, ShowShopActivity.class);
                intent.putExtra("pid", listBean.getPid() + "");
                intent.putExtra("url", listBean.getDetailUrl() + "");
                intent.putExtra("title", listBean.getTitle());
                intent.putExtra("icont", listBean.getImages());
                ((MainActivity) mContext).startActivity(intent);
            }
        });
    }

    @Override
    public void convertPrent(ViewHolder viewHolder, List<ShopBean.DataBean.ListBean> t, int postion) {

    }
}