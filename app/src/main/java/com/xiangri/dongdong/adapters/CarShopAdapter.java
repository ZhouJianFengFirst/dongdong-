package com.xiangri.dongdong.adapters;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.xiangri.dongdong.R;
import com.xiangri.dongdong.entity.CarBean;

import java.util.List;

public class CarShopAdapter extends RecycleAdapter<CarBean.DataBean.ListBean> {
    private Context mContext;

    public CarShopAdapter(Context mcontext, List<CarBean.DataBean.ListBean> datas) {
        super(mcontext, datas);
        this.mContext = mcontext;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_iteam_car;
    }

    @Override
    protected void convert(final ViewHolder viewHolder, final CarBean.DataBean.ListBean listBean, final int postion) {

    }

    @Override
    public void convertPrent(final ViewHolder viewHolder, final List<CarBean.DataBean.ListBean> t, final int postion) {
        final CarBean.DataBean.ListBean listBean = t.get(postion);
        String[] split = listBean.getImages().split("\\|");
        viewHolder.setText(R.id.shop_title, listBean.getTitle())
                .setImageUrl(R.id.shop_image, split[0])
                .setText(R.id.shop_price, "价格：" + listBean.getPrice() + "");
        if (listBean.isIschecked()) {
            viewHolder.setImageResource(R.id.iv_select, R.drawable.cricle_yes);
        } else {
            viewHolder.setImageResource(R.id.iv_select, R.drawable.cricle_no);
        }

        //设定选择框
        viewHolder.getView(R.id.iv_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listBean.isIschecked()) {
                    listBean.setIschecked(false);
                    viewHolder.setImageResource(R.id.iv_select, R.drawable.cricle_no);
                    okClick.Ok();
                    notifyItemChanged(postion);
                } else {
                    listBean.setIschecked(true);
                    viewHolder.setImageResource(R.id.iv_select, R.drawable.cricle_yes);
                    okClick.Ok();
                    notifyItemChanged(postion);
                }
            }
        });

        EditText etcontent = (EditText) viewHolder.getView(R.id.et_content);

        etcontent.setText(t.get(postion).getSelectNum()+"");
        viewHolder.setClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.add:
                        int num = t.get(postion).getSelectNum();
                        num++;
                        setContent(num, t, postion);
                        break;
                    case R.id.remove:
                        int num1 = t.get(postion).getSelectNum();
                        num1 --;
                        setContent(num1, t, postion);
                        break;
                }
            }
        }, R.id.add, R.id.remove);
    }


    public void setContent(int num,  List<CarBean.DataBean.ListBean> t, int postion) {
        if (num < 1) {
            toase("不能小于1呦");
            return;
        }
        t.get(postion).setSelectNum(num);
        notifyItemChanged(postion);
        okClick.Ok();
    }

    public interface OkClick {
        void Ok();
    }

    private OkClick okClick;

    public void setOkClick(OkClick okClick) {
        this.okClick = okClick;
    }

    public void toase(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}