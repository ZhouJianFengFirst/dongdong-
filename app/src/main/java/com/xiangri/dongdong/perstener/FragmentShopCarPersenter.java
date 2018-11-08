package com.xiangri.dongdong.perstener;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xiangri.dongdong.R;
import com.xiangri.dongdong.adapters.CarAdapter;
import com.xiangri.dongdong.adapters.CarShopAdapter;
import com.xiangri.dongdong.entity.CarBean;
import com.xiangri.dongdong.mvp.view.AppDelegate;
import com.xiangri.dongdong.net.Http;
import com.xiangri.dongdong.utils.DialogUtils;
import com.xiangri.dongdong.utils.SpUtil;
import com.xiangri.dongdong.view.TopView;

public class FragmentShopCarPersenter extends AppDelegate implements View.OnClickListener, CarShopAdapter.OkClick {

    private static final int CAR_LIST_REQUEST = 1;
    private Context mContext;
    private ImageView selectAll;
    private TextView total;
    private TextView close;
    private RecyclerView recyList;
    private boolean flag = true;
    private CarBean carBean;
    private CarAdapter carAdapter;
    private double priceAll = 0.0;
    private int numAll = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_fragment_shopcar;
    }

    @Override
    public void initData() {
        super.initData();

        //设置事件
        setEvent();

        Boolean isLogion = (Boolean) SpUtil.getInserter(mContext).getSpData("login_flag",false);
        if (!isLogion){
            DialogUtils dialogUtils = new DialogUtils(mContext);
            dialogUtils.show();
        }
        String uid = (String) SpUtil.getInserter(mContext).getSpData("uid","-1");
        //设置列表的网络数据
        getString(Http.GET_SHOP_CAR_URL + "?uid="+uid+"", CAR_LIST_REQUEST,null);
    }

    private void setEvent() {
        selectAll = (ImageView) getView(R.id.selectAll);
        total = (TextView) getView(R.id.total);
        close = (TextView) getView(R.id.close);
        recyList = (RecyclerView) getView(R.id.recycar);
        TopView topView = (TopView) getView(R.id.topview);
        topView.showLeft(false);
        setClick(this, R.id.selectAll, R.id.close);
    }

    @Override
    public void initContext(Context context) {
        super.initContext(context);
        this.mContext = context;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.selectAll:
                setSelect();
                break;
            case R.id.close:
                break;
        }
    }

    //设置全选全不选
    private void setSelect() {
        if (flag) {
            selectAll.setImageResource(R.drawable.cricle_yes);
            setSelectMouthed(true);
            carAdapter.notifyDataSetChanged();
        } else {
            selectAll.setImageResource(R.drawable.cricle_no);
            setSelectMouthed(false);
            carAdapter.notifyDataSetChanged();
            priceAll = 0.0;
            numAll = 0;
            setContent();
        }
        flag = !flag;
    }

    public void setSelectMouthed(boolean flag) {
        for (int i = 0; i < carBean.getData().size(); i++) {
            for (int j = 0; j < carBean.getData().get(i).getList().size(); j++) {
                carBean.getData().get(i).getList().get(j).setIschecked(flag);
                if (carBean.getData().get(i).getList().get(j).isIschecked()) {
                    priceAll += carBean.getData().get(i).getList().get(j).getPrice();
                    numAll += 1;
                }
            }
        }
        setContent();
    }

    //设置合计和计算
    public void setContent() {
        total.setText("合计：" + priceAll);
        close.setText("计算：(" + numAll + ")");
        priceAll = 0.0;
        numAll = 0;
    }

    @Override
    public void successString(String data, int type) {
        super.successString(data, type);
        switch (type) {
            case CAR_LIST_REQUEST:
                setCarList(data);
                break;
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    public void setCarList(String carList) {
        if (carList.contains(">")) {
            return;
        }
        if ("null".equals(carList)){
            toast("还没有商品");
            return;
        }
        Gson gson = new Gson();
        carBean = gson.fromJson(carList, CarBean.class);
        if ("1".equals(carBean.getCode())){
            toast("还没有商品");
            return;
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyList.setLayoutManager(linearLayoutManager);
        carAdapter = new CarAdapter(mContext, carBean.getData());
        carAdapter.setOkClick(this);
        recyList.setAdapter(carAdapter);
    }

    @Override
    public void Ok() {
        priceAll = 0.0;
        numAll = 0;
        for (int i = 0; i < carBean.getData().size(); i++) {
            for (int j = 0; j < carBean.getData().get(i).getList().size(); j++) {
                if (carBean.getData().get(i).getList().get(j).isIschecked()) {
                    CarBean.DataBean.ListBean listBean = carBean.getData().get(i).getList().get(j);
                    priceAll += listBean.getPrice() * listBean.getSelectNum();
                    numAll += listBean.getSelectNum();
                }
            }
        }
        setContent();
    }

    public void notifyChange() {
        Boolean isLogion = (Boolean) SpUtil.getInserter(mContext).getSpData("login_flag",false);
        if (!isLogion){
            DialogUtils dialogUtils = new DialogUtils(mContext);
            dialogUtils.show();
        }
        String uid = (String) SpUtil.getInserter(mContext).getSpData("uid","-1");
        //设置列表的网络数据
        getString(Http.GET_SHOP_CAR_URL + "?uid="+uid+"", CAR_LIST_REQUEST,null);
    }
}
