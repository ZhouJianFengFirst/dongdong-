package com.xiangri.dongdong.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.xiangri.dongdong.R;
import com.xiangri.dongdong.entity.ShopBean;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends BaseAdapter {

    private Context mContext;
    private List<ShopBean.DataBean> shopList = new ArrayList<>();

    public void setShopList(List<ShopBean.DataBean> shopList) {
        this.shopList = shopList;
        notifyDataSetChanged();
    }

    public ListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return shopList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_iteam_list, null);
            viewHolder.txt_content = convertView.findViewById(R.id.txt_content);
            viewHolder.merchant = (RecyclerView) convertView.findViewById(R.id.shop_merchant);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.txt_content.setText(shopList.get(position).getSellerName());
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        viewHolder.merchant.setLayoutManager(staggeredGridLayoutManager);
        ShopAdapter shopAdapter = new ShopAdapter(mContext);
        viewHolder.merchant.setAdapter(shopAdapter);
        shopAdapter.setList(shopList.get(position).getList());
        return convertView;
    }

    class ViewHolder {
        private TextView txt_content;
        private RecyclerView merchant;
    }
}
