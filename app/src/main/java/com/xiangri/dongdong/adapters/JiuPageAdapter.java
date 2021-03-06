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
import com.xiangri.dongdong.entity.JiuDataBean;

import java.util.ArrayList;
import java.util.List;

public class JiuPageAdapter extends PagerAdapter {

    private Context mContext;
    private List<JiuDataBean> onePage = new ArrayList<>();
    private List<JiuDataBean> towPage = new ArrayList<>();
    private List<JiuDataBean> threePage = new ArrayList<>();
    private int pagenum = 0;

    public JiuPageAdapter(Context mContext, List<JiuDataBean> onePage, List<JiuDataBean> towPage, List<JiuDataBean> threePage, int pagenum) {
        this.mContext = mContext;
        this.onePage = onePage;
        this.towPage = towPage;
        this.pagenum = pagenum;
        this.threePage = threePage;
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

        JiuPageRecyAdapter adapter = new JiuPageRecyAdapter(mContext);
        recyclerView.setAdapter(adapter);
        if (position == 0) {
            adapter.setList(onePage);
        } else if (position == 1) {
            adapter.setList(towPage);
        } else if (position == 2) {
            adapter.setList(threePage);
        }
        container.addView(inflate);
        return inflate;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}