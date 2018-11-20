package com.xiangri.dongdong.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xiangri.dongdong.R;
import com.xiangri.dongdong.activity.SearchActivity;
import com.xiangri.dongdong.activity.ShopListActivity;
import com.xiangri.dongdong.entity.HistoryEntity;
import com.xiangri.dongdong.sql.SqlUtil;
import com.xiangri.dongdong.utils.SpUtil;

import java.util.ArrayList;
import java.util.List;

public class SelfView extends RelativeLayout implements View.OnClickListener {

    private Context mcontext;
    private LinearLayout layoutall;
    private List<HistoryEntity> datas = new ArrayList<>();
    private LinearLayout linearhor;
    private TextView txt;

    public SelfView(Context context) {
        super(context);
        init(context);
    }

    public SelfView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SelfView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mcontext = context;
        //初始化控件
        View view = View.inflate(mcontext, R.layout.layout_liu_v, null);
        //初始化主布局
        layoutall = view.findViewById(R.id.layout_v);
        view.findViewById(R.id.remove).setOnClickListener(this);
        this.addView(view);
    }

    public void setData(List<HistoryEntity> data) {
        datas = data;
        setList(data);
    }

    private void setList(final List<HistoryEntity> list) {

        layoutall.removeAllViews();
        //创建第二的布局
        linearhor = (LinearLayout) View.inflate(mcontext, R.layout.layout_liu_h, null);

        layoutall.addView(linearhor);
        linearhor.removeAllViews();

        int len = 0;
        for (int i = 0; i < list.size(); i++) {
            String data = list.get(i).getHistory();
            len += data.length();
            if (len > 23) {
                linearhor = (LinearLayout) View.inflate(mcontext, R.layout.layout_liu_h, null);
                layoutall.addView(linearhor);
                len = 0;
            }
            //找到textview的布局
            View viewtxt = View.inflate(mcontext, R.layout.layout_liu_txt, null);
            //找到txtview的控件
            txt = (TextView) viewtxt.findViewById(R.id.txt_shop_name);
            //设置文字
            txt.setText(data);
            //太你就爱到布局
            linearhor.addView(viewtxt);
            //设置布局的宽高
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewtxt.getLayoutParams();
            layoutParams.weight = 1;
            layoutParams.topMargin = 10;
/*          layoutParams.leftMargin = 10;
            layoutParams.rightMargin = 10;*/
            viewtxt.setLayoutParams(layoutParams);
        }

        for ( int i = 0; i <linearhor.getChildCount() ; i++) {
           final View view= linearhor.getChildAt(i);
            final int finalI = i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                Intent intent = new Intent(mcontext, ShopListActivity.class);
                intent.putExtra("keywords", datas.get(finalI).getHistory());
                ((SearchActivity) mcontext).finish();
                ((SearchActivity) mcontext).startActivity(intent);
                }
            });
        }

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linearhor.getLayoutParams();
        params.leftMargin=10;
        params.rightMargin=10;
        linearhor.setLayoutParams(params);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.remove:
                SqlUtil.getInstens().deleteAll();
                layoutall.removeAllViews();
                break;
        }
    }
}