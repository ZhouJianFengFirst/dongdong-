package com.xiangri.dongdong.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.xiangri.dongdong.R;
import com.xiangri.dongdong.entity.CarBean;
import com.xiangri.dongdong.view.ShopCarView;

import java.util.List;

public class CarShopAdapter extends RecycleAdapter<CarBean.DataBean.ListBean> {
    private Context mContext;

    public CarShopAdapter(Context mcontext) {
        super(mcontext);
        this.mContext = mcontext;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_iteam_car;
    }

    @Override
    protected void convert(final ViewHolder viewHolder, final CarBean.DataBean.ListBean listBean, final int postion) {
        final ShopCarView shopCarView = (ShopCarView) viewHolder.getView(R.id.shopcarview);
        shopCarView.setListener(new ShopCarView.DataBackListener() {
            @Override
            public void touchSelect(boolean ischecked) {
                listBean.setIschecked(ischecked);
                notifyItemChanged(postion);
                shopCarView.setIsChecked(ischecked);
                listener.back(listBean.getNum());
            }

            @Override
            public void backNum(int num) {
                listBean.setNum(num);
                notifyItemChanged(postion);
                shopCarView.setShopNum(num);
                listener.back(num);
            }
        });
        if (listBean.isIschecked()) {
            shopCarView.setIsChecked(true);
        } else {
            shopCarView.setIsChecked(false);
        }
        String[] split = listBean.getImages().split("\\|");
        shopCarView.setShopImage(split[0]);
        shopCarView.setTitle(listBean.getTitle());
        shopCarView.setPrice("￥价格：" + listBean.getPrice() + "");
        shopCarView.setShopNum(listBean.getNum());
    }

    @Override
    public void convertPrent(final ViewHolder viewHolder, final List<CarBean.DataBean.ListBean> t, final int postion) {

    }

    public void toase(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    public interface ShopCarBackNumListener {
        void back(int num);
    }

    public ShopCarBackNumListener listener;

    public void setListener(ShopCarBackNumListener listener) {
        this.listener = listener;
    }
}