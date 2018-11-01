package com.xiangri.dongdong.adapters;

import android.content.Context;

import com.xiangri.dongdong.R;
import com.xiangri.dongdong.entity.JiuBean;

import java.util.List;

public class JiuPageRecyAdapter extends RecycleAdapter<JiuBean.DataBean>   {

    public JiuPageRecyAdapter(Context mcontext, List<JiuBean.DataBean> datas) {
        super(mcontext, datas);
    }

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
}
