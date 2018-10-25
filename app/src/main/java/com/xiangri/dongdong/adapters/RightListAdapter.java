package com.xiangri.dongdong.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiangri.dongdong.R;
import com.xiangri.dongdong.entity.RightShopBean;

import java.util.ArrayList;
import java.util.List;

public class RightListAdapter extends BaseAdapter {

    private List<RightShopBean.DataBean> shopList = new ArrayList<>();
    private Context mContext;

    public RightListAdapter(Context mContext) {
        this.mContext = mContext;

    }

    public void setList(List<RightShopBean.DataBean> list) {
        this.shopList = list;
        notifyDataSetChanged();
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

        ViewHolder viewHolder ;

        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.layout_iteam_list,null);
            viewHolder.txt_content = convertView.findViewById(R.id.txt_content);
            viewHolder.merchant = (RecyclerView) convertView.findViewById(R.id.shop_merchant); convertView.setTag(viewHolder);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txt_content.setText(shopList.get(position).getName());
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        viewHolder.merchant.setLayoutManager(staggeredGridLayoutManager);
        RightShopAdapter rightShopAdapter = new RightShopAdapter(mContext, shopList.get(position).getList());
        viewHolder.merchant.setAdapter(rightShopAdapter);
        return convertView;
    }

     class ViewHolder{
        TextView txt_content;
       private RecyclerView merchant;
    }
}
