package com.xiangri.dongdong.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.xiangri.dongdong.R;
import com.xiangri.dongdong.entity.JiuBean;
import com.xiangri.dongdong.entity.JiuDataBean;

import java.util.List;

public class LeftRecyAdapter extends RecycleAdapter<JiuDataBean> {

    public LeftRecyAdapter(Context mcontext) {
        super(mcontext);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.layout_iteam_image;
    }

    @Override
    protected void convert(final ViewHolder viewHolder, final JiuDataBean dataBean, final int postion) {
        viewHolder.setText(R.id.txt,dataBean.getName()).setImageUrl(R.id.gride_image,dataBean.getIcon());
    }

    @Override
    public void convertPrent(ViewHolder viewHolder, final List<JiuDataBean> t, final int postion) {
        final View view = viewHolder.getView(R.id.line);
        viewHolder.getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cid = t.get(postion).getCid();
                listenerOk.clickOk(cid);
                for (int i = 0; i <t.size(); i ++){
                    if (i == postion){
                        view.setVisibility(View.VISIBLE);
                    }else{
                        view.setVisibility(View.GONE);
                    }
                }
            }
        });

    }

    private OnClikListenerOk listenerOk;

    public interface OnClikListenerOk{
       void clickOk(int cid);
    }

    public void setListenerOk(OnClikListenerOk listenerOk) {
        this.listenerOk = listenerOk;
    }

}
