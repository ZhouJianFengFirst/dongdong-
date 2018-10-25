package com.xiangri.dongdong.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.xiangri.dongdong.R;
import com.xiangri.dongdong.entity.JiuBean;

import java.util.ArrayList;
import java.util.List;

public class JiuPageAdapter extends PagerAdapter {


    private Context mContext;
    private List<JiuBean.DataBean> onePage = new ArrayList<>();
    private List<JiuBean.DataBean> towPage = new ArrayList<>();
    private int pagenum = 0;

    public JiuPageAdapter(Context mContext, List<JiuBean.DataBean> onPage, List<JiuBean.DataBean> towPage, int pagenum) {
        this.mContext = mContext;
        this.onePage = onPage;
        this.towPage = towPage;
        this.pagenum = pagenum;
    }

    @Override
    public int getCount() {
        return pagenum;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View inflate = View.inflate(mContext, R.layout.layout_iteam_jiu, null);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.jiu_recy);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 4);
        recyclerView.setLayoutManager(gridLayoutManager);
        if (position == 0) {
            setAdapter(recyclerView, onePage);
        } else if (position == 1) {
            setAdapter(recyclerView, towPage);
        }
        container.addView(inflate);
        return inflate;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public void setAdapter(RecyclerView recyclerView, List<JiuBean.DataBean> list) {
        recyclerView.setAdapter(new RecycleAdapter<JiuBean.DataBean>(mContext, list) {

            @Override
            protected int getLayoutId() {
                return R.layout.layout_jiu_shopmessage;
            }

            @Override
            protected void convert(ViewHolder viewHolder, JiuBean.DataBean dataBean, int postion) {
                viewHolder.setText(R.id.txt, dataBean.getName()).setImageUrl(R.id.gride_image, dataBean.getIcon());
            }

            @Override
            public void convertPrent(ViewHolder viewHolder, List<JiuBean.DataBean> t, int postion) {

            }
        });
    }
}
