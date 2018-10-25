package com.xiangri.dongdong.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.xiangri.dongdong.MainActivity;
import com.xiangri.dongdong.R;
import com.xiangri.dongdong.activity.MapActivity;
import com.xiangri.dongdong.entity.MeListBean;

import java.util.List;

public class MeListAdapter extends RecycleAdapter<MeListBean>   {
    private Context mContext;
    public MeListAdapter(Context mcontext, List<MeListBean> datas) {
        super(mcontext, datas);
        this.mContext = mcontext;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_iteam_me;
    }

    @Override
    protected void convert(ViewHolder viewHolder, MeListBean meListBean, final int postion) {
        viewHolder.setImageResource(R.id.me_image,meListBean.getImages())
                .setText(R.id.txt_title,meListBean.getNames());
        viewHolder.getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (postion){
                    case 0:
                        ((MainActivity)mContext).startActivity(new Intent(mContext, MapActivity.class));
                        break;
                }
            }
        });
    }

    @Override
    public void convertPrent(ViewHolder viewHolder, List<MeListBean> t, int postion) {

    }
}
