package com.xiangri.dongdong.adapters;

import android.content.Context;
import android.view.View;

import com.xiangri.dongdong.R;
import com.xiangri.dongdong.entity.JiuBean;

import java.util.List;

public class LeftRecyAdapter extends RecycleAdapter<JiuBean.DataBean> {

    public LeftRecyAdapter(Context mcontext, List<JiuBean.DataBean> datas) {
        super(mcontext, datas);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.layout_jiu_shopmessage;
    }

    @Override
    protected void convert(ViewHolder viewHolder, final JiuBean.DataBean dataBean, final int postion) {
        viewHolder.setText(R.id.txt,dataBean.getName())
        .setImageUrl(R.id.gride_image,dataBean.getIcon());
        viewHolder.getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                int cid = dataBean.getCid();
                listenerOk.clickOk(cid);
            }
        });
    }

    @Override
    public void convertPrent(ViewHolder viewHolder, List<JiuBean.DataBean> t, int postion) {

    }

    private OnClikListenerOk listenerOk;

    public interface OnClikListenerOk{
       void clickOk(int cid);
    }

    public void setListenerOk(OnClikListenerOk listenerOk) {
        this.listenerOk = listenerOk;
    }

}
