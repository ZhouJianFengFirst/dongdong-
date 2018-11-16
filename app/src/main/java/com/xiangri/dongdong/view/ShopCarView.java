package com.xiangri.dongdong.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xiangri.dongdong.R;

public class ShopCarView extends RelativeLayout implements View.OnClickListener {

    private ImageView ivSelect;
    private SimpleDraweeView shopImage;
    private TextView txtTitle, txtPrice;
    private EditText etInput;
    private int num = 1;
    private boolean ischecked;
    private Context mCotext;

    public ShopCarView(Context context) {
        super(context);
        init(context);
    }

    public ShopCarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ShopCarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mCotext = context;
        //初始化控件
        View view = LayoutInflater.from(context).inflate(R.layout.layout_shopcar_view, null);
        initWeigth(view);
        addView(view);
    }

    private void initWeigth(View view) {
        ivSelect = (ImageView) view.findViewById(R.id.iv_select);
        shopImage = (SimpleDraweeView) view.findViewById(R.id.shop_image);
        view.findViewById(R.id.add).setOnClickListener(this);
        view.findViewById(R.id.remove).setOnClickListener(this);
        ivSelect.setOnClickListener(this);
        txtTitle = (TextView) view.findViewById(R.id.shop_title);
        txtPrice = (TextView) view.findViewById(R.id.shop_price);
        etInput = (EditText) view.findViewById(R.id.et_content);
    }

    //设置选择状态
    public void setIsChecked(boolean isChecked) {
        this.ischecked = isChecked;
        if (isChecked) {
            ivSelect.setImageResource(R.drawable.cricle_yes);
        } else {
            ivSelect.setImageResource(R.drawable.cricle_no);
        }
    }

    //设置商品图片
    public void setShopImage(String url) {
        shopImage.setImageURI(url);
    }

    //设置商品标题
    public void setTitle(String title) {
        txtTitle.setText(title);
    }

    //设置商品价格

    public void setPrice(String price) {
        txtPrice.setText(price);
    }

    //设置商品的数量
    public void setShopNum(int num) {
        this.num = num;
        etInput.setText(num + "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                num++;
                setInput(num);
                break;
            case R.id.remove:
                num--;
                setInput(num);
                break;
            case R.id.iv_select:
                if (ischecked) {
                    ischecked = false;
                    listener.touchSelect(ischecked);
                } else {
                    ischecked = true;
                    listener.touchSelect(ischecked);
                }
                break;
        }
    }

    public void setInput(int num) {
        if (num == 0) {
            Toast.makeText(mCotext,"商品不能小于0",Toast.LENGTH_SHORT).show();
            num = 1;
            return;
        }
        listener.backNum(num);
    }

    public interface DataBackListener {
        void touchSelect(boolean ischecked);

        void backNum(int num);
    }

    private DataBackListener listener;

    public void setListener(DataBackListener listener) {
        this.listener = listener;
    }
}