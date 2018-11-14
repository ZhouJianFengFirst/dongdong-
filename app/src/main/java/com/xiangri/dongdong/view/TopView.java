package com.xiangri.dongdong.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiangri.dongdong.R;

public class TopView extends RelativeLayout {
    private View view;
    private TextView title;
    private TextView right;
    private String titleContent = "title";
    private String rightContent = "编辑";
    private Context mContext;
    private ImageView back;

    public TopView(Context context) {
        super(context);
        init(context);
    }

    public TopView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray type = context.obtainStyledAttributes(attrs, R.styleable.TopView);
        titleContent = type.getString(R.styleable.TopView_title);
        rightContent = type.getString(R.styleable.TopView_rightcontent);
        type.recycle();
        init(context);
    }

    public TopView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        //设置事件
        view = LayoutInflater.from(context).inflate(R.layout.layout_top_view, null);
        setEvent();
        setTitle(titleContent);
        setRightContent(rightContent);
    }

    private void setEvent() {
        title = (TextView) view.findViewById(R.id.txt_title);
        right = (TextView) view.findViewById(R.id.right_content);
        back = (ImageView) view.findViewById(R.id.back);
        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppCompatActivity) mContext).finish();
            }
        });
        right.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
             listener.back();
            }
        });
        addView(view);
    }

    public void showLeft(boolean flag){
        if (flag) {
            back.setVisibility(View.VISIBLE);
        }else{
            back.setVisibility(View.GONE);
        }
    }

    public void showRight(boolean flag){
        if (flag){
            right.setVisibility(View.VISIBLE);
        }else{
            right.setVisibility(View.GONE);
        }
    }
    public void setTitle(String msg) {
        title.setText(msg);
    }

    public void setRightContent(String msg) {
        right.setText(msg);
    }


    private BackListener listener;

    public void setListener(BackListener listener) {
        this.listener = listener;
    }

    public interface BackListener{
        void back();
    }
}
